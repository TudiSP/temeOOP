package entertainment;

import actor.Actor;

import java.util.ArrayList;
import java.util.List;

public final class Show extends Video {
    private final List<String> actorNames;
    private final int nrSeasons;
    private final List<Season> seasons;
    private List<Actor> actors;

    /**
     *
     * @param launchYear
     * @param title
     * @param actorNames
     * @param genres
     * @param nrSeasons
     * @param seasons
     * @param type
     */
    public Show(final int launchYear, final String title,
                final ArrayList<String> actorNames, final ArrayList<String> genres,
                final int nrSeasons, final List<Season> seasons, final String type) {
        super(launchYear, title, genres, type);
        this.actors = actors;
        this.actorNames = actorNames;
        this.nrSeasons = nrSeasons;
        this.seasons = seasons;
        calculateTotalDuration();
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(final List<Actor> actors) {
        this.actors = actors;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public List<String> getActorNames() {
        return actorNames;
    }

    /**
     * calculate the totalDuration, adding up season durations
     */
    public void calculateTotalDuration() {
        Double dur = 0.0;
        for (Season season : seasons) {
            dur += season.getDuration();
        }
        duration = dur;
    }

    /**
     * calculate total AverageRating, doing an average of all season's scores
     */
    public void calculateAverageRating() {
        Double sum = 0.0;
        for (Season season : seasons) {
            sum += season.calculateAverageRating();
        }
        averageRating = sum / nrSeasons;
    }
}
