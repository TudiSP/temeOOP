package entertainment;

import actor.Actor;

import java.util.ArrayList;
import java.util.List;

public final class Movie extends Video {
    private List<Double> ratings;
    private List<Actor> actors;
    private List<String> actorNames;
    private List<String> ratedBy;

    public Movie(int launchYear, String title, ArrayList<String> genres, int duration, ArrayList<String> actorNames, String type) {
        super(launchYear, title, genres, type);
        super.duration = Double.valueOf(duration);
        this.actorNames = actorNames;
        this.ratings = new ArrayList<>();
        this.ratedBy = new ArrayList<>();
    }


    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(List<Double> ratings) {
        this.ratings = ratings;
    }


    public boolean ratedBy(String username) {
        return ratedBy.contains(username);
    }

    public void addUserRatingList(String username) {
        ratedBy.add(username);
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<String> getActorNames() {
        return actorNames;
    }

    public void calculateAverageRating() {
        Double sum = 0.0;
        for(Double d : getRatings()) {
            sum += d;
        }
        averageRating = sum / (double) getRatings().size();
    }

    @Override
    public String toString() {
        return "Movie{" +
                "launchYear=" + launchYear +
                ", title='" + title + '\'' +
                ", genres=" + genres +
                '}';
    }
}



