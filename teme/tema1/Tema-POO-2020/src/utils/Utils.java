package utils;

import actor.Actor;
import actor.ActorsAwards;
import common.Constants;
import entertainment.Genre;
import entertainment.Season;
import entertainment.Video;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import users.User;

import java.util.*;

/**
 * The class contains static methods that helps with parsing.
 *
 * We suggest you add your static methods here or in a similar class.
 */
public final class Utils {
    /**
     * for coding style
     */
    private Utils() {
    }

    /**
     * Transforms a string into an enum
     *
     * @param genre of video
     * @return an Genre Enum
     */
    public static Genre stringToGenre(final String genre) {
        return switch (genre.toLowerCase()) {
            case "action" -> Genre.ACTION;
            case "adventure" -> Genre.ADVENTURE;
            case "drama" -> Genre.DRAMA;
            case "comedy" -> Genre.COMEDY;
            case "crime" -> Genre.CRIME;
            case "romance" -> Genre.ROMANCE;
            case "war" -> Genre.WAR;
            case "history" -> Genre.HISTORY;
            case "thriller" -> Genre.THRILLER;
            case "mystery" -> Genre.MYSTERY;
            case "family" -> Genre.FAMILY;
            case "horror" -> Genre.HORROR;
            case "fantasy" -> Genre.FANTASY;
            case "science fiction" -> Genre.SCIENCE_FICTION;
            case "action & adventure" -> Genre.ACTION_ADVENTURE;
            case "sci-fi & fantasy" -> Genre.SCI_FI_FANTASY;
            case "animation" -> Genre.ANIMATION;
            case "kids" -> Genre.KIDS;
            case "western" -> Genre.WESTERN;
            case "tv movie" -> Genre.TV_MOVIE;
            default -> null;
        };
    }

    /**
     * Transforms a string into an enum
     *
     * @param award for actors
     * @return an ActorsAwards Enum
     */
    public static ActorsAwards stringToAwards(final String award) {
        return switch (award) {
            case "BEST_SCREENPLAY" -> ActorsAwards.BEST_SCREENPLAY;
            case "BEST_SUPPORTING_ACTOR" -> ActorsAwards.BEST_SUPPORTING_ACTOR;
            case "BEST_DIRECTOR" -> ActorsAwards.BEST_DIRECTOR;
            case "BEST_PERFORMANCE" -> ActorsAwards.BEST_PERFORMANCE;
            case "PEOPLE_CHOICE_AWARD" -> ActorsAwards.PEOPLE_CHOICE_AWARD;
            default -> null;
        };
    }

    public static List<ActorsAwards> actorsAwardsList(final List<String> awards){
        List<ActorsAwards> awardsList = new ArrayList<>();
        for (String award : awards) {
            awardsList.add(stringToAwards(award));
        }
        if(!awards.isEmpty()) {
            return awardsList;
        }
        return null;
    }

    /**
     * Transforms an array of JSON's into an array of strings
     *
     * @param array of JSONs
     * @return a list of strings
     */
    public static ArrayList<String> convertJSONArray(final JSONArray array) {
        if (array != null) {
            ArrayList<String> finalArray = new ArrayList<>();
            for (Object object : array) {
                finalArray.add((String) object);
            }
            return finalArray;
        } else {
            return null;
        }
    }

    /**
     * Transforms an array of JSON's into a map
     *
     * @param jsonActors array of JSONs
     * @return a map with ActorsAwardsa as key and Integer as value
     */
    public static Map<ActorsAwards, Integer> convertAwards(final JSONArray jsonActors) {
        Map<ActorsAwards, Integer> awards = new LinkedHashMap<>();

        for (Object iterator : jsonActors) {
            awards.put(stringToAwards((String) ((JSONObject) iterator).get(Constants.AWARD_TYPE)),
                    Integer.parseInt(((JSONObject) iterator).get(Constants.NUMBER_OF_AWARDS)
                            .toString()));
        }

        return awards;
    }

    /**
     * Transforms an array of JSON's into a map
     *
     * @param movies array of JSONs
     * @return a map with String as key and Integer as value
     */
    public static Map<String, Integer> watchedMovie(final JSONArray movies) {
        Map<String, Integer> mapVideos = new LinkedHashMap<>();

        if (movies != null) {
            for (Object movie : movies) {
                mapVideos.put((String) ((JSONObject) movie).get(Constants.NAME),
                        Integer.parseInt(((JSONObject) movie).get(Constants.NUMBER_VIEWS)
                                .toString()));
            }
        } else {
            System.out.println("NU ESTE VIZIONAT NICIUN FILM");
        }

        return mapVideos;
    }

    public static User stringToUserSearch(List<User> users, String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static Video stringToVideoSearch(List<Video> videos, String title) {
        for (Video video : videos) {
            if (video.getTitle().equals(title)) {
                return video;
            }
        }
        return null;
    }

    public static List<Video> stringToVideoSearchList(List<Video> videos, List<String> titles) {
        List<Video> videoList = new ArrayList<>();
        for (String title : titles) {
            Video video = Utils.stringToVideoSearch(videos, title);
            if(video != null) {
                videoList.add(video);
            }
        }
        if(!videoList.isEmpty()) {
            return videoList;
        }
        return null;
    }

    public static Season numberToSeasonSearch(List<Season> seasons, int currentSeason) {
        for (Season season : seasons) {
            if (season.getCurrentSeason() == currentSeason) {
                return season;
            }
        }
        return null;
    }

    public static Actor stringToActorSearch(List<Actor> actors, String name) {
        for(Actor actor : actors) {
            if(actor.getName().equals(name)) {
                return actor;
            }
        }
        return null;
    }

    public static List<Actor> stringToActorSearchList(List<Actor> actors, List<String> names) {
        List<Actor> actorlist = new ArrayList<>();
        for(String name : names) {
            Actor actor = stringToActorSearch(actors, name);
            if(actor != null) {
                actorlist.add(actor);
            }
        }
        if(!actorlist.isEmpty()) {
            return actorlist;
        }
        return null;
    }

    public static User searchUserByName(List<User> users, String name) {
        for(User user : users) {
            if(user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public static Actor searchActorByName(List<Actor> actors, String name) {
        for(Actor actor : actors) {
            if(actor.getName().equals(name)) {
                return actor;
            }
        }
        return null;
    }

    public static Video searchVideoByName(List<Video> videos, String name) {
        for(Video video : videos) {
            if(video.getTitle().equals(name)) {
                return video;
            }
        }
        return null;
    }

}