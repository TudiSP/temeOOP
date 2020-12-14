package actions;

import actor.Actor;
import common.MainContainer;
import entertainment.Movie;
import entertainment.Season;
import entertainment.Show;
import entertainment.Video;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import users.User;
import utils.Utils;

import java.io.IOException;

public final class Command implements Action {
    private final int id;
    private final String type;
    private final String username;
    private final String videoname;
    private double rating;
    private int numberSeason;

    /**
     *
     * @param id
     * @param type
     * @param username
     * @param videoname
     * @param rating
     * @param numberSeason
     */
    public Command(final int id, final String type,
                   final String username, final String videoname,
                   final double rating, final int numberSeason) {
        this.id = id;
        this.type = type;
        this.username = username;
        this.videoname = videoname;
        this.rating = rating;
        this.numberSeason = numberSeason;
    }

    /**
     * generates JSONObject based on the task's requirements
     * @param fileWriter
     * @param video
     * @param user
     * @return
     * @throws IOException
     */
    public JSONObject favorite(final Writer fileWriter, final Video video,
                               final User user) throws IOException {
        if (user.hasSeen(video.getTitle())) {
            if (!user.getFavourite().contains(video.getTitle())) {
                user.getFavourite().add(video.getTitle());
                video.setNrFavourites(video.getnrFavourites() + 1);
                return fileWriter.writeFile(getId(), null, "success -> "
                        + video.getTitle() + " was added as favourite");
            } else {
                return fileWriter.writeFile(getId(), null, "error -> "
                        + video.getTitle() + " is already in favourite list");
            }
        } else {
            return fileWriter.writeFile(getId(), null, "error -> "
                    + video.getTitle() + " is not seen");

        }
    }

    public JSONObject view(final Writer fileWriter, final Video video,
                           final User user) throws IOException {
        video.setViews(video.getViews() + 1);
        user.getHistory().merge(video.getTitle(), 1, Integer::sum);
        return fileWriter.writeFile(getId(), null, "success -> "
                + video.getTitle() + " was viewed with total views of "
                + user.getHistory().get(video.getTitle()));
    }

    /**
     * generates JSONObject based on the task's requirements
     * @param fileWriter
     * @param video
     * @param user
     * @return
     * @throws IOException
     */
    public JSONObject rating(final Writer fileWriter, final Video video,
                             final User user) throws IOException {
        switch (video.getType()) {
            case "movie":
                if (!user.hasSeen(video.getTitle())) {
                    return fileWriter.writeFile(getId(), null, "error -> "
                            + video.getTitle() + " is not seen");
                }
                if (!((Movie) video).ratedBy(user.getUsername())
                        && user.hasSeen(video.getTitle())) {
                    // increment user activity
                    user.setNoRatings(user.getNoRatings() + 1);
                    // keep track on which user rated the movie
                    ((Movie) video).addUserRatingList(user.getUsername());
                    // add ratings
                    ((Movie) video).getRatings().add(getRating());
                    // recalculate average rating
                    ((Movie) video).calculateAverageRating();
                    //check for// check for actors
                    if (((Movie) video).getActors() != null) {
                        // recalculate and update cast's averageRating for movies
                        for (Actor actor : ((Movie) video).getActors()) {
                            Actor.updateAverageRating(actor);
                        }
                    }
                    // return message as JSONObject
                    return fileWriter.writeFile(getId(), null, "success -> "
                            + video.getTitle() + " was rated with "
                            + getRating() + " by " + user.getUsername());
                }
                return fileWriter.writeFile(getId(), null, "error -> "
                        + video.getTitle() + " has been already rated");
            // return message as JSONObject
            case "serial":
                Season season = Utils.numberToSeasonSearch(((Show) video).getSeasons(),
                        getNumberSeason());
                if (!user.hasSeen(video.getTitle())) {
                    return fileWriter.writeFile(getId(), null, "error -> "
                            + video.getTitle() + " is not seen");
                }
                if (season != null && !season.ratedBy(user.getUsername())) {
                    // increment user activity
                    user.setNoRatings(user.getNoRatings() + 1);
                    season.addUserRatingList(user.getUsername());
                    season.getRatings().add(getRating()); // add ratings
                    ((Show) video).calculateAverageRating();
                    if (((Show) video).getActors() != null) {
                        // recalculate and update cast's averageRating for movies
                        for (Actor actor : ((Show) video).getActors()) {
                            Actor.updateAverageRating(actor);
                        }
                    }
                    // return message as JSONObject
                    return fileWriter.writeFile(getId(), null, "success -> "
                            + video.getTitle() + " was rated with "
                            + getRating() + " by " + user.getUsername());
                }
                return fileWriter.writeFile(getId(), null, "error -> "
                        + video.getTitle() + " has been already rated");
            default:
                return fileWriter.writeFile(getId(), null, "error -> WRONG TYPE");
        }

    }

    /**
     * implements Action interface's method execute() and adds a JSONObject to the JSONArray
     * by calling the other methods described above.
     * @param fileWriter
     * @param arrayResult
     * @throws IOException
     */
    public void execute(final Writer fileWriter,
                        final JSONArray arrayResult) throws IOException {
        Video video = Utils.searchVideoByName(MainContainer.getVideos(), videoname);
        User user = Utils.searchUserByName(MainContainer.getUsers(), username);
        // add action result to JSONArray object
        switch (type) {
            case "favorite" -> arrayResult.add(favorite(fileWriter, video, user));
            case "view" -> arrayResult.add(view(fileWriter, video, user));
            case "rating" -> arrayResult.add(rating(fileWriter, video, user));
            default -> arrayResult.add(null);
        }
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }

    public int getNumberSeason() {
        return numberSeason;
    }

    public void setNumberSeason(final int numberSeason) {
        this.numberSeason = numberSeason;
    }
}
