package entertainment;

import actor.Actor;

import java.util.ArrayList;
import java.util.List;

public final class Movie extends Video {
    private final List<String> actorNames;
    private final List<String> ratedBy;
    private List<Double> ratings;
    private List<Actor> actors;

    /**
     *
     * @param launchYear
     * @param title
     * @param genres
     * @param duration
     * @param actorNames
     * @param type
     */
    public Movie(final int launchYear, final String title,
                 final ArrayList<String> genres,
                 final int duration, final ArrayList<String> actorNames,
                 final String type) {
        super(launchYear, title, genres, type);
        super.duration = (double) duration;
        this.actorNames = actorNames;
        this.ratings = new ArrayList<>();
        this.ratedBy = new ArrayList<>();
    }


    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }

    /**
     * checks if this movie has been already rated by a user based on it's username
     * @param username
     * @return
     */
    public boolean ratedBy(final String username) {
        return ratedBy.contains(username);
    }

    /**
     * add's user to ratedBy list
     * @param username
     */
    public void addUserRatingList(final String username) {
        ratedBy.add(username);
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(final List<Actor> actors) {
        this.actors = actors;
    }

    public List<String> getActorNames() {
        return actorNames;
    }

    /**
     * calculates a movie's average rating based on it's ratings
     */
    public void calculateAverageRating() {
        Double sum = 0.0;
        for (Double d : getRatings()) {
            sum += d;
        }
        averageRating = sum / (double) getRatings().size();
    }
}



