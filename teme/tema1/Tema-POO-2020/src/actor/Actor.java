package actor;

import actions.Action;
import entertainment.Movie;
import entertainment.Show;
import entertainment.Video;

import java.util.*;

public class Actor {
    private final String name;
    private final String career_description;
    private List<Video> filmography;
    private Map<ActorsAwards, Integer> awards;

    private Double averageRating;
    private Double queryAwardNumber;

    public Actor(String name, String career_description, List<Video> filmography, Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.career_description = career_description;
        this.filmography = filmography;
        this.awards = awards;
        this.averageRating = 0.0;
    }

    public static List<Actor> sortActorList(List<Actor> actors, List<ActorsAwards> queryAwards, int numberOfActors, String criteria, String sortingOrder) {
        List<Actor> sortedActorList = actors;
        if(sortingOrder.equals("asc")) { //sortare ascendenta
            sortedActorList.sort(new Comparator<Actor>() {
                @Override
                public int compare(Actor o1, Actor o2) {
                    switch (criteria) {
                        case "average":
                            if(o1.averageRating.compareTo(o2.averageRating) != 0) { //if not equal compare based on averageRating
                                return o1.averageRating.compareTo(o2.averageRating); // else continue to default
                            }
                            return o1.name.compareTo(o2.name);
                        case "awards":
                                o1.queryAwardNumber = o1.calculateNumberAwards(queryAwards);
                                o2.queryAwardNumber = o2.calculateNumberAwards(queryAwards);
                                if(o1.queryAwardNumber.compareTo(o2.queryAwardNumber) != 0) {
                                    return o1.queryAwardNumber.compareTo(o2.queryAwardNumber);
                                }
                                return o1.name.compareTo(o2.name);
                        default:
                            return o1.name.compareTo(o2.name);
                    }
                }
            });
        } else { //sortare descendenta
            sortedActorList.sort(new Comparator<Actor>() {
                @Override
                public int compare(Actor o1, Actor o2) {
                    switch (criteria) {
                        case "average":
                            if(o1.averageRating.compareTo(o2.averageRating) != 0) { //if not equal compare based on averageRating
                                return o1.averageRating.compareTo(o2.averageRating); // else continue to default
                            }
                        default:
                            return o1.name.compareTo(o2.name);
                    }
                }
            }.reversed());
        }

        if(numberOfActors != 0 && numberOfActors < sortedActorList.size()) {
            Iterator<Actor> i = sortedActorList.listIterator(numberOfActors);
            while (i.hasNext()) {
                i.next();
                i.remove();
            }
        }
        return sortedActorList;
    }

    public static Double calculateAverageRating(List<Video> filmography) {
        Double sum = 0.0;
        Double nrRatedVideos = 0.0;
        for(Video video : filmography) {
            if(video.getAverageRating() != 0) {
                sum += video.getAverageRating();
                nrRatedVideos++;
            }
        }
        if(nrRatedVideos != 0) {
            return sum / nrRatedVideos;
        }
        return 0.0;
    }

    public static String toStringActorList(List<Actor> actors){ // create a string of actor's names based on a list of actors
        String output = new String();

        output += "[";
        for(Actor actor : actors) {
            output += actor.name;
            output += ", ";
        }
        output = output.substring(0, output.length() - 2); // remove last 2 characters -> ", "
        output += "]";

        return output;
    }

    public boolean hasAwards(List<ActorsAwards> queryAwards) {
        for(ActorsAwards award : queryAwards) {
            if(awards.containsKey(award) == false) {
                return false;
            }
        }
        return true;
    }

    public Double calculateNumberAwards(List<ActorsAwards> queryAwards) {
        Double no_awards = 0.0;
        for(ActorsAwards award : queryAwards) {
           no_awards = Double.valueOf(awards.get(award)); // we presume that actor has all the awards from the list
        }
        return no_awards;
    }

    public boolean hasKeywords(List<String> keywords){
        for(String keyword : keywords) {
            if(!career_description.contains(keyword)) {
                return  false;
            }
        }
        return true;
    }

    public static void updateAverageRating(Actor actor) {
        actor.averageRating = calculateAverageRating(actor.filmography);
    }

    public String getName() {
        return name;
    }

    public String getCareer_description() {
        return career_description;
    }

    public List<Video> getFilmography() {
        return filmography;
    }

    public void setFilmography(List<Video> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public void setAwards(Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
    }

    public Double getAverageRating() {
        return averageRating;
    }
    @Override
    public String toString() {
        return "Actor{" +
                "name='" + name + '\'' +
                '}';
    }

}
