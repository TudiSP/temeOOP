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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The class contains static methods that helps with parsing.
 * <p>
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

    public static List<ActorsAwards> actorsAwardsList(final List<String> awards) {
        List<ActorsAwards> awardsList = new ArrayList<>();
        for (String award : awards) {
            awardsList.add(stringToAwards(award));
        }
        if (!awards.isEmpty()) {
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

    /**
     * Search for a user in a list based on it's username
     * @param users
     * @param username
     * @return
     */
    public static User stringToUserSearch(final List<User> users, final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * search for a video in a list based on a title(String)
     * @param videos
     * @param title
     * @return
     */
    public static Video stringToVideoSearch(final List<Video> videos, final String title) {
        for (Video video : videos) {
            if (video.getTitle().equals(title)) {
                return video;
            }
        }
        return null;
    }

    /**
     * get a sub-list of videos based on a list of titles(Strings)
     * @param videos
     * @param titles
     * @return
     */
    public static List<Video> stringToVideoSearchList(final List<Video> videos,
                                                      final List<String> titles) {
        List<Video> videoList = new ArrayList<>();
        for (String title : titles) {
            Video video = Utils.stringToVideoSearch(videos, title);
            if (video != null) {
                videoList.add(video);
            }
        }
        if (!videoList.isEmpty()) {
            return videoList;
        }
        return null;
    }

    /**
     * get a Season by it's number in a list
     * @param seasons
     * @param currentSeason
     * @return
     */
    public static Season numberToSeasonSearch(final List<Season> seasons, final int currentSeason) {
        for (Season season : seasons) {
            if (season.getCurrentSeason() == currentSeason) {
                return season;
            }
        }
        return null;
    }

    /**
     * output an actor searched in a list of actors by it's name(String)
     * @param actors
     * @param name
     * @return
     */
    public static Actor stringToActorSearch(final List<Actor> actors, final String name) {
        for (Actor actor : actors) {
            if (actor.getName().equals(name)) {
                return actor;
            }
        }
        return null;
    }

    /**
     * get a sub-list of actors based on a list of names
     * @param actors
     * @param names
     * @return
     */
    public static List<Actor> stringToActorSearchList(final List<Actor> actors,
                                                      final List<String> names) {
        List<Actor> actorlist = new ArrayList<>();
        for (String name : names) {
            Actor actor = stringToActorSearch(actors, name);
            if (actor != null) {
                actorlist.add(actor);
            }
        }
        if (!actorlist.isEmpty()) {
            return actorlist;
        }
        return null;
    }

    /**
     * output an user by searching by it's name(String) in a list
     * @param users
     * @param name
     * @return
     */
    public static User searchUserByName(final List<User> users, final String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    /**
     * output an actor by searching by it's name(String) in a list
     * @param actors
     * @param name
     * @return
     */
    public static Actor searchActorByName(final List<Actor> actors, final String name) {
        for (Actor actor : actors) {
            if (actor.getName().equals(name)) {
                return actor;
            }
        }
        return null;
    }

    /**
     * output a video by searching by it's name(String) in a list
     * @param videos
     * @param name
     * @return
     */
    public static Video searchVideoByName(final List<Video> videos, final String name) {
        for (Video video : videos) {
            if (video.getTitle().equals(name)) {
                return video;
            }
        }
        return null;
    }
}
