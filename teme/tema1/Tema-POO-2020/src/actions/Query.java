package actions;

import actor.Actor;
import actor.ActorsAwards;
import common.MainContainer;
import entertainment.Movie;
import entertainment.Show;
import entertainment.Video;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import users.User;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class Query implements Action {
    private final int id;
    private final int number;
    private final List<List<String>> filters;
    private final String sortType;
    private final String criteria;
    private final List<ActorsAwards> awards;
    private final List<String> keywords;
    private final String objType;
    private static final int KEYINDEX = 2;
    private static final int GENINDEX = 3;

    /**
     *
     * @param id
     * @param number
     * @param filters
     * @param sortType
     * @param criteria
     * @param objType
     */
    public Query(final int id, final int number, final List<List<String>> filters,
                 final String sortType, final String criteria, final String objType) {
        this.id = id;
        this.number = number;
        this.filters = filters;
        this.sortType = sortType;
        this.criteria = criteria;

        if (filters.get(KEYINDEX) != null) {
            // initialise keywords array based on input string list
            keywords = new ArrayList<>();
            // filters.get(2) is the list where keywords are stocked as strings
            keywords.addAll(filters.get(KEYINDEX));
        } else {
            keywords = null;
        }

        if (filters.get(GENINDEX) != null) {
            // initialise awards array based on input string list
            awards = new ArrayList<>();
            // filters.get(GENINDEX) is the list where awards are stocked as strings
            awards.addAll(Objects.requireNonNull(Utils.actorsAwardsList(filters.get(GENINDEX))));
        } else {
            awards = null;
        }

        this.objType = objType;
    }

    /**
     * generates JSONObject based on the task's requirements
     * @param fileWriter
     * @return
     * @throws IOException
     */
    public JSONObject average(final Writer fileWriter) throws IOException {
        List<Actor> sortedActorList = new ArrayList<>(MainContainer.getActors());

        // remove all actors with no rated videos from list
        sortedActorList.removeIf(actor -> actor.getAverageRating() == 0);

        if (!sortedActorList.isEmpty()) { //if list is not empty sort it
            sortedActorList = Actor.sortActorList(sortedActorList,
                    awards, number, criteria, sortType);
            return fileWriter.writeFile(id, null, "Query result: "
                    + Actor.toStringActorList(sortedActorList));
            // generate output and return it as JSONObject
        }
        return fileWriter.writeFile(id, null, "Query result: []");
    }

    /**
     * generates JSONObject based on the task's requirements
     * @param fileWriter
     * @return
     * @throws IOException
     */
    public JSONObject awards(final Writer fileWriter) throws IOException {
        List<Actor> sortedActorList = new ArrayList<>(MainContainer.getActors());

        // remove all actors with no rated videos from list
        sortedActorList.removeIf(actor -> !actor.hasAwards(awards));
        if (!sortedActorList.isEmpty()) { //if list is not empty sort it
            sortedActorList = Actor.sortActorList(sortedActorList,
                    awards, number, criteria, sortType);
            return fileWriter.writeFile(id, null, "Query result: "
                    + Actor.toStringActorList(sortedActorList));
            // generate output and return it as JSONObject
        }
        return fileWriter.writeFile(id, null, "Query result: []");
    }

    /**
     * generates JSONObject based on the task's requirements
     * @param fileWriter
     * @return
     * @throws IOException
     */
    public JSONObject filterDescription(final Writer fileWriter) throws IOException {
        List<Actor> sortedActorList = new ArrayList<>(MainContainer.getActors());

        // remove all actors with no rated videos from list
        sortedActorList.removeIf(actor -> !actor.hasKeywords(keywords));

        if (!sortedActorList.isEmpty()) { //if list is not empty sort it
            sortedActorList = Actor.sortActorList(sortedActorList,
                    awards, number, criteria, sortType);
            return fileWriter.writeFile(id, null, "Query result: "
                    + Actor.toStringActorList(sortedActorList));
            // generate output and return it as JSONObject
        }
        return fileWriter.writeFile(id, null, "Query result: []");
    }

    /**
     * generates JSONObject based on the task's requirements
     * @param fileWriter
     * @return
     * @throws IOException
     */
    public JSONObject ratings(final Writer fileWriter) throws IOException {
        List<Video> sortedVideoList = new ArrayList<>(MainContainer.getVideos());

        Iterator<Video> i = sortedVideoList.iterator();
        while (i.hasNext()) { // remove all actors with no rated videos from list
            Video video = i.next();
            if (!video.checkFilters(filters) || video.getAverageRating() == 0) {
                i.remove();
            } else if (objType.equals("movies") && video instanceof Show) {
                i.remove();
            } else if (objType.equals("shows") && video instanceof Movie) {
                i.remove();
            }
        }
        if (!sortedVideoList.isEmpty()) { //if list is not empty sort it
            sortedVideoList = Video.sortVideoList(sortedVideoList,
                    number, criteria, sortType);
            return fileWriter.writeFile(id, null, "Query result: "
                    + Video.toStringVideoList(sortedVideoList));
            // generate output and return it as JSONObject
        }
        return fileWriter.writeFile(id, null, "Query result: []");
    }

    /**
     * generates JSONObject based on the task's requirements
     * @param fileWriter
     * @return
     * @throws IOException
     */
    public JSONObject favorite(final Writer fileWriter) throws IOException {
        List<Video> sortedVideoList = new ArrayList<>(MainContainer.getVideos());

        Iterator<Video> i = sortedVideoList.iterator();
        while (i.hasNext()) { // remove all actors with no rated videos from list
            Video video = i.next();
            if (!video.checkFilters(filters) || video.getnrFavourites() == 0) {
                i.remove();
            } else if (objType.equals("movies") && video instanceof Show) {
                i.remove();
            } else if (objType.equals("shows") && video instanceof Movie) {
                i.remove();
            }
        }
        if (!sortedVideoList.isEmpty()) { //if list is not empty sort it
            sortedVideoList = Video.sortVideoList(sortedVideoList,
                    number, criteria, sortType);
            return fileWriter.writeFile(id, null, "Query result: "
                    + Video.toStringVideoList(sortedVideoList));
            // generate output and return it as JSONObject
        }
        return fileWriter.writeFile(id, null, "Query result: []");
    }

    /**
     * generates JSONObject based on the task's requirements
     * @param fileWriter
     * @return
     * @throws IOException
     */
    public JSONObject longest(final Writer fileWriter) throws IOException {
        List<Video> sortedVideoList = new ArrayList<>(MainContainer.getVideos());

        Iterator<Video> i = sortedVideoList.iterator();
        while (i.hasNext()) { // remove all actors with no rated videos from list
            Video video = i.next();
            if (!video.checkFilters(filters)) {
                i.remove();
            } else if (objType.equals("movies") && video instanceof Show) {
                i.remove();
            } else if (objType.equals("shows") && video instanceof Movie) {
                i.remove();
            }
        }
        if (!sortedVideoList.isEmpty()) { //if list is not empty sort it
            sortedVideoList = Video.sortVideoList(sortedVideoList,
                    number, criteria, sortType);
            return fileWriter.writeFile(id, null, "Query result: "
                    + Video.toStringVideoList(sortedVideoList));
            // generate output and return it as JSONObject
        }
        return fileWriter.writeFile(id, null, "Query result: []");
    }

    /**
     * generates JSONObject based on the task's requirements
     * @param fileWriter
     * @return
     * @throws IOException
     */
    public JSONObject mostViewed(final Writer fileWriter) throws IOException {
        List<Video> sortedVideoList = new ArrayList<>(MainContainer.getVideos());

        Iterator<Video> i = sortedVideoList.iterator();
        while (i.hasNext()) {
            // remove all videos which have no views or do not check required filters
            Video video = i.next();
            if (!video.checkFilters(filters) || video.getViews() == 0) {
                i.remove();
            } else if (objType.equals("movies") && video instanceof Show) {
                i.remove();
            } else if (objType.equals("shows") && video instanceof Movie) {
                i.remove();
            }
        }
        if (!sortedVideoList.isEmpty()) { //if list is not empty sort it
            sortedVideoList = Video.sortVideoList(sortedVideoList,
                    number, criteria, sortType);
            return fileWriter.writeFile(id, null, "Query result: "
                    + Video.toStringVideoList(sortedVideoList));
            // generate output and return it as JSONObject
        }
        return fileWriter.writeFile(id, null, "Query result: []");
    }

    /**
     * generates JSONObject based on the task's requirements
     * @param fileWriter
     * @return
     * @throws IOException
     */
    public JSONObject noRatings(final Writer fileWriter) throws IOException {
        List<User> sortedUserList = new ArrayList<>(MainContainer.getUsers());

        // remove all Users which have no views or do not check required filters
        sortedUserList.removeIf(user -> user.getNoRatings() == 0);
        if (!sortedUserList.isEmpty()) { //if list is not empty sort it
            sortedUserList = User.sortUserList(sortedUserList,
                    number, criteria, sortType);

            return fileWriter.writeFile(id, null, "Query result: "
                    + User.toStringUserList(sortedUserList));
            // generate output and return it as JSONObject
        }
        return fileWriter.writeFile(id, null, "Query result: []");
    }

    /**
     * implements Action interface's method execute() and adds a JSONObject to the JSONArray
     * by calling the other methods described above.
     * @param fileWriter
     * @param arrayResult
     * @throws IOException
     */
    public void execute(final Writer fileWriter,
                        final JSONArray arrayResult) throws IOException {
        // add action result to JSONArray object
        switch (criteria) {
            case "average" -> arrayResult.add(average(fileWriter));
            case "awards" -> arrayResult.add(awards(fileWriter));
            case "filter_description" -> arrayResult.add(filterDescription(fileWriter));
            case "ratings" -> arrayResult.add(ratings(fileWriter));
            case "favorite" -> arrayResult.add(favorite(fileWriter));
            case "longest" -> arrayResult.add(longest(fileWriter));
            case "most_viewed" -> arrayResult.add(mostViewed(fileWriter));
            case "num_ratings" -> arrayResult.add(noRatings(fileWriter));
            default -> arrayResult.add(null);
        }
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public List<List<String>> getFilters() {
        return filters;
    }

    public String getCriteria() {
        return criteria;
    }


}
