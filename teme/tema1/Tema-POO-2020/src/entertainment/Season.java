package entertainment;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a season of a tv show
 * <p>
 * DO NOT MODIFY
 */
public final class Season {
    /**
     * Number of current season
     */
    private final int currentSeason;
    private final List<String> ratedBy;
    /**
     * Duration in minutes of a season
     */
    private final int duration;
    /**
     * List of ratings for each season
     */
    private List<Double> ratings;

    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new ArrayList<>();
        this.ratedBy = new ArrayList<>();
    }

    public int getDuration() {
        return duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }


    public int getCurrentSeason() {
        return currentSeason;
    }

    public boolean ratedBy(final String username) {
        return ratedBy.contains(username);
    }

    public void addUserRatingList(final String username) {
        ratedBy.add(username);
    }

    public Double calculateAverageRating() {
        Double sum = 0.0;
        for (Double d : getRatings()) {
            sum += d;
        }
        if (getRatings().size() != 0) {
            return sum / (double) getRatings().size();
        }
        return 0.0;
    }

    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + '}';
    }
}

