package entertainment;

import actor.Actor;

import java.util.ArrayList;
import java.util.List;

public class Show extends Video{
    private  List<Actor> actors;
    private  List<String> actorNames;
    private int no_seasons;
    private List<Season> seasons;

    public Show(int launchYear, String title, ArrayList<String> actorNames, ArrayList<String> genres,
                int no_seasons, List<Season> seasons, String type) {
        super(launchYear, title, genres, type);
        this.actors = actors;
        this.actorNames = actorNames;
        this.no_seasons = no_seasons;
        this.seasons = seasons;
        calculateTotalDuration();
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public List<String> getActorNames() {
        return actorNames;
    }

    public void calculateTotalDuration(){
        Double dur = 0.0;
        for(Season season : seasons) {
            dur += season.getDuration();
        }
        duration = dur;
    }

    public void calculateAverageRating() {
        Double sum = 0.0;
        for(Season season : seasons) {
            sum += season.calculateAverageRating();
        }
        averageRating = sum / no_seasons;
    }
}
