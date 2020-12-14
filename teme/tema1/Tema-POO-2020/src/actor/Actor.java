package actor;

import entertainment.Video;

import java.util.*;

public final class Actor {
    private final String name;
    private final String careerDescription;
    private List<Video> filmography;
    private Map<ActorsAwards, Integer> awards;

    private Double averageRating;
    private Double queryAwardNumber;

    public Actor(final String name, final String careerDescription,
                 final List<Video> filmography,
                 final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
        this.averageRating = 0.0;
    }

    /**
     * sort an actor list based on a criteria and sortingOrder
     * and trim it's size to numberOfActors
     * @param actors
     * @param queryAwards
     * @param numberOfActors
     * @param criteria
     * @param sortingOrder
     * @return the sorted actor list
     */
    public static List<Actor> sortActorList(final List<Actor> actors,
                                            final List<ActorsAwards> queryAwards,
                                            final int numberOfActors, final String criteria,
                                            final String sortingOrder) {
        List<Actor> sortedActorList = actors;
        if (sortingOrder.equals("asc")) { //sortare ascendenta
            sortedActorList.sort(new Comparator<Actor>() {
                @Override
                public int compare(final Actor o1, final Actor o2) {
                    switch (criteria) {
                        case "average":
                            //if not equal compare based on averageRating
                            if (o1.averageRating.compareTo(o2.averageRating) != 0) {
                                return o1.averageRating.compareTo(o2.averageRating);
                            }
                            return o1.name.compareTo(o2.name);
                        case "awards":
                            o1.queryAwardNumber = o1.calculateNumberAwards();
                            o2.queryAwardNumber = o2.calculateNumberAwards();
                            if (o1.queryAwardNumber.compareTo(o2.queryAwardNumber) != 0) {
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
                public int compare(final Actor o1, final Actor o2) {
                    switch (criteria) {
                        case "average":
                            //if not equal compare based on averageRating
                            if (o1.averageRating.compareTo(o2.averageRating) != 0) {
                                return o1.averageRating.compareTo(o2.averageRating);
                            }
                            return o1.name.compareTo(o2.name);
                        case "awards":
                            o1.queryAwardNumber = o1.calculateNumberAwards();
                            o2.queryAwardNumber = o2.calculateNumberAwards();
                            if (o1.queryAwardNumber.compareTo(o2.queryAwardNumber) != 0) {
                                return o1.queryAwardNumber.compareTo(o2.queryAwardNumber);
                            }
                            return o1.name.compareTo(o2.name);
                        default:
                            return o1.name.compareTo(o2.name);
                    }
                }
            }.reversed());
        }

        if (numberOfActors != 0 && numberOfActors < sortedActorList.size()) {
            Iterator<Actor> i = sortedActorList.listIterator(numberOfActors);
            while (i.hasNext()) {
                i.next();
                i.remove();
            }
        }
        return sortedActorList;
    }

    /**
     * calculate an actor's average rating
     * @param filmography
     * @return
     */
    public static Double calculateAverageRating(final List<Video> filmography) {
        Double sum = 0.0;
        Double nrRatedVideos = 0.0;
        for (Video video : filmography) {
            if (video.getAverageRating() != 0) {
                sum += video.getAverageRating();
                nrRatedVideos++;
            }
        }
        if (nrRatedVideos != 0) {
            return sum / nrRatedVideos;
        }
        return 0.0;
    }

    /**
     * convert a list of actors to a string based on their names
     * @param actors
     * @return
     */
    public static String toStringActorList(final List<Actor> actors) {
        String output = "";

        output += "[";
        for (Actor actor : actors) {
            output += actor.name;
            output += ", ";
        }
        // remove last 2 characters -> ", "
        output = output.substring(0, output.length() - 2);
        output += "]";

        return output;
    }

    /**
     * update the averageRating field based on new data
     * @param actor
     */
    public static void updateAverageRating(final Actor actor) {
        actor.averageRating = calculateAverageRating(actor.filmography);
    }

    /**
     * determine if actor has the awards passed by the parameter
     * @param queryAwards
     * @return
     */
    public boolean hasAwards(final List<ActorsAwards> queryAwards) {
        for (ActorsAwards award : queryAwards) {
            if (awards.containsKey(award) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * calcultate the total number of awards of the actor
     * @return
     */
    public Double calculateNumberAwards() {
        Double nrAwards = 0.0;
        for (Integer nr : awards.values()) {
            nrAwards += nr; // we presume that actor has all the awards from the list
        }
        return nrAwards;
    }

    /**
     * determine if an actor has certain keywords in their careerDescription
     * @param keywords
     * @return
     */
    public boolean hasKeywords(final List<String> keywords) {
        String desc = careerDescription.toLowerCase();
        for (String keyword : keywords) {
            StringTokenizer tokens = new StringTokenizer(desc, " \t\n\r\f,.()'-\"");
            int sw = 0;
            while (tokens.hasMoreTokens()) {
                String word = tokens.nextToken();
                if (word.equals(keyword.toLowerCase())) {
                    sw = 1;
                }
            }
            if (sw == 0) {
                return false;
            }
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public List<Video> getFilmography() {
        return filmography;
    }

    public void setFilmography(final List<Video> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public void setAwards(final Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
    }

    public Double getAverageRating() {
        return averageRating;
    }
}
