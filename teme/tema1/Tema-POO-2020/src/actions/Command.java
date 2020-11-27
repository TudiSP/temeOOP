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

public class Command implements Action{
    private final int id;
    private final String type;
    private final String username;
    private final String videoname;
    private double rating;
    private int numberSeason;

    public Command(int id, String type, String username, String videoname, double rating, int numberSeason){
        this.id = id;
        this.type = type;
        this.username = username;
        this.videoname = videoname;
        this.rating = rating;
        this.numberSeason = numberSeason;
    }

    public JSONObject favorite(Writer fileWriter, Video video, User user) throws IOException {
        if(user.hasSeen(video.getTitle())) {
            if(!user.getFavourite().contains(video.getTitle())) {
                user.getFavourite().add(video.getTitle());
                video.setNrFavourites(video.getnrFavourites() + 1);
                return fileWriter.writeFile(getId(), null, "success -> " + video.getTitle() + " was added as favourite");
            } else {
                return fileWriter.writeFile(getId(), null, "error -> " + video.getTitle() + " is already in favourite list");
            }
        } else {
            return fileWriter.writeFile(getId(), null, "error -> " + video.getTitle() + " is not seen");

        }
    }

    public JSONObject view(Writer fileWriter, Video video, User user) throws IOException{
        if(!user.hasSeen(video.getTitle())) {
            video.setNrFavourites(video.getnrFavourites() + 1); // increment nr of favourites in that video
        }
        user.getHistory().merge(video.getTitle(), 1, Integer::sum);
        return fileWriter.writeFile(getId(), null, "success -> " + video.getTitle() + " was viewed with total views of "
                + user.getHistory().get(video.getTitle()));
    }

    public JSONObject rating(Writer fileWriter, Video video, User user) throws IOException{
        switch (video.getType()){
            case "movie":
                if(!((Movie) video).ratedBy(user.getUsername())) {
                    ((Movie) video).addUserRatingList(user.getUsername()); // keep track on which user rated the movie
                    ((Movie) video).getRatings().add(getRating()); // add ratings
                    ((Movie) video).calculateAverageRating(); // recalculate average rating
                    if(((Movie) video).getActors() != null) { //check for// check for actors
                            for (Actor actor : ((Movie) video).getActors()) { // recalculate and update cast's averageRating for movies
                                Actor.updateAverageRating(actor);
                            }
                    }
                    return fileWriter.writeFile(getId(), null, "success -> " + video.getTitle() + " was rated with "
                            + getRating() + " by " + user.getUsername()); // return message as JSONObject
                }
                return fileWriter.writeFile(getId(), null, "error -> " + video.getTitle() + " has been already rated");
                // return message as JSONObject
            case "serial":
                Season season  = Utils.numberToSeasonSearch(((Show) video).getSeasons(), getNumberSeason());
                if(season != null && !season.ratedBy(user.getUsername())) {
                    season.addUserRatingList(user.getUsername());
                    season.getRatings().add(getRating()); // add ratings
                    ((Show) video).calculateAverageRating();
                    if(((Show) video).getActors() != null) {
                        for (Actor actor : ((Show) video).getActors()) { // recalculate and update cast's averageRating for movies
                            Actor.updateAverageRating(actor);
                        }
                    }
                    return fileWriter.writeFile(getId(), null, "success -> " + video.getTitle() + " was rated with "
                            + getRating() + " by " + user.getUsername()); // return message as JSONObject
                }
                return fileWriter.writeFile(getId(), null, "error -> " + video.getTitle() + " has been already rated");
            default:
                return fileWriter.writeFile(getId(), null, "error -> WRONG TYPE");
        }

    }

    public void execute(Writer fileWriter, JSONArray arrayResult) throws IOException{
        Video video = Utils.searchVideoByName(MainContainer.getVideos(), videoname);
        User user = Utils.searchUserByName(MainContainer.getUsers(), username);
        switch (type) { // add action result to JSONArray object
            case "favorite":
                arrayResult.add(favorite(fileWriter, video, user));
                break;
            case "view":
                arrayResult.add(view(fileWriter, video, user));
                break;
            case "rating":
                arrayResult.add(rating(fileWriter, video, user));
                break;
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

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumberSeason() {
        return numberSeason;
    }

    public void setNumberSeason(int numberSeason) {
        this.numberSeason = numberSeason;
    }

}
