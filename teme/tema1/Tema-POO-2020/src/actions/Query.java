package actions;

import actor.Actor;
import actor.ActorsAwards;
import common.MainContainer;
import entertainment.Video;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import users.User;
import utils.Utils;

import java.io.IOException;
import java.util.*;

public class Query implements Action{
    private final int id;
    private final int number;
    private final List<List<String>> filters;
    private final String sort_type;
    private final String criteria;
    private final List<ActorsAwards> awards;
    private final List<String> keywords;


    public Query(int id, int number, List<List<String>> filters, String sort_type, String criteria) {
        this.id = id;
        this.number = number;
        this.filters = filters;
        this.sort_type = sort_type;
        this.criteria = criteria;

        if(filters.get(2) != null) { // initialise keywords array based on input string list
            keywords = new ArrayList<>(); // filters.get(2) is the list where keywords are stocked as strings
            keywords.addAll(filters.get(2));
        } else {
            keywords = null;
        }

        if(filters.get(3) != null) { // initialise awards array based on input string list
            awards = new ArrayList<>(); // filters.get(3) is the list where awards are stocked as strings
            awards.addAll(Utils.actorsAwardsList(filters.get(3)));
        } else {
            awards = null;
        }
    }

    public JSONObject average(Writer fileWriter) throws IOException {
        List<Actor> sortedActorList = new ArrayList<>();
        sortedActorList.addAll(MainContainer.getActors());

        Iterator<Actor> i = sortedActorList.iterator();
        while(i.hasNext()) { // remove all actors with no rated videos from list
            Actor actor = i.next();
            if(actor.getAverageRating() == 0) {
                i.remove();
            }
        }
        if(!sortedActorList.isEmpty()) { //if list is not empty sort it
            sortedActorList = Actor.sortActorList(sortedActorList, awards, number, criteria, sort_type);
            return fileWriter.writeFile(id, null, "Query result: " + Actor.toStringActorList(sortedActorList));
            // generate output and return it as JSONObject
        }
        return fileWriter.writeFile(id, null, "Query result: []");
    }

    public JSONObject awards(Writer fileWriter) throws IOException {
        List<Actor> sortedActorList = new ArrayList<>();
        sortedActorList.addAll(MainContainer.getActors());

        Iterator<Actor> i = sortedActorList.iterator();
        while(i.hasNext()) { // remove all actors with no rated videos from list
            Actor actor = i.next();
            if(!actor.hasAwards(awards)) {
                i.remove();
            }
        }
        if(!sortedActorList.isEmpty()) { //if list is not empty sort it
            sortedActorList = Actor.sortActorList(sortedActorList, awards, number, criteria, sort_type);
            return fileWriter.writeFile(id, null, "Query result: " + Actor.toStringActorList(sortedActorList));
            // generate output and return it as JSONObject
        }
        return fileWriter.writeFile(id, null, "Query result: []");
    }

    public JSONObject filter_description(Writer fileWriter) throws IOException {
        List<Actor> sortedActorList = new ArrayList<>();
        sortedActorList.addAll(MainContainer.getActors());

        Iterator<Actor> i = sortedActorList.iterator();
        while(i.hasNext()) { // remove all actors with no rated videos from list
            Actor actor = i.next();
            if(!actor.hasKeywords(keywords)) {
                i.remove();
            }
        }
        if(!sortedActorList.isEmpty()) { //if list is not empty sort it
            sortedActorList = Actor.sortActorList(sortedActorList, awards, number, criteria, sort_type);
            return fileWriter.writeFile(id, null, "Query result: " + Actor.toStringActorList(sortedActorList));
            // generate output and return it as JSONObject
        }
        return fileWriter.writeFile(id, null, "Query result: []");
    }

    public JSONObject ratings(Writer fileWriter) throws IOException {
        List<Video> sortedVideoList = new ArrayList<>();
        sortedVideoList.addAll(MainContainer.getVideos());

        Iterator<Video> i = sortedVideoList.iterator();
        while(i.hasNext()) { // remove all actors with no rated videos from list
            Video video = i.next();
            if(!video.checkFilters(filters) || video.getAverageRating() == 0) {
                i.remove();
            }
        }
        if(!sortedVideoList.isEmpty()) { //if list is not empty sort it
            sortedVideoList = Video.sortVideoList(sortedVideoList, number, criteria, sort_type);
            return fileWriter.writeFile(id, null, "Query result: " + Video.toStringVideoList(sortedVideoList));
            // generate output and return it as JSONObject
        }
        return fileWriter.writeFile(id, null, "Query result: []");
    }

    public JSONObject favorite(Writer fileWriter) throws IOException {
        List<Video> sortedVideoList = new ArrayList<>();
        sortedVideoList.addAll(MainContainer.getVideos());

        Iterator<Video> i = sortedVideoList.iterator();
        while(i.hasNext()) { // remove all actors with no rated videos from list
            Video video = i.next();
            System.out.println(video.getTitle() + " " + video.getnrFavourites());
            if(!video.checkFilters(filters) || video.getnrFavourites() == 0) {
                i.remove();
            }
        }
        System.out.println("---------------");
        if(!sortedVideoList.isEmpty()) { //if list is not empty sort it
            sortedVideoList = Video.sortVideoList(sortedVideoList, number, criteria, sort_type);
            return fileWriter.writeFile(id, null, "Query result: " + Video.toStringVideoList(sortedVideoList));
            // generate output and return it as JSONObject
        }
        return fileWriter.writeFile(id, null, "Query result: []");
    }

    public JSONObject longest(Writer fileWriter) throws IOException {
        List<Video> sortedVideoList = new ArrayList<>();
        sortedVideoList.addAll(MainContainer.getVideos());

        Iterator<Video> i = sortedVideoList.iterator();
        while(i.hasNext()) { // remove all actors with no rated videos from list
            Video video = i.next();
            if(!video.checkFilters(filters)) {
                i.remove();
            }
        }
        if(!sortedVideoList.isEmpty()) { //if list is not empty sort it
            sortedVideoList = Video.sortVideoList(sortedVideoList, number, criteria, sort_type);
            return fileWriter.writeFile(id, null, "Query result: " + Video.toStringVideoList(sortedVideoList));
            // generate output and return it as JSONObject
        }
        return fileWriter.writeFile(id, null, "Query result: []");
    }

    public JSONObject mostViewed(Writer fileWriter) throws IOException {
        List<Video> sortedVideoList = new ArrayList<>();
        sortedVideoList.addAll(MainContainer.getVideos());

        Iterator<Video> i = sortedVideoList.iterator();
        while(i.hasNext()) { // remove all actors with no rated videos from list
            Video video = i.next();
            if(!video.checkFilters(filters)) {
                i.remove();
            }
        }
        if(!sortedVideoList.isEmpty()) { //if list is not empty sort it
            sortedVideoList = Video.sortVideoList(sortedVideoList, number, criteria, sort_type);
            return fileWriter.writeFile(id, null, "Query result: " + Video.toStringVideoList(sortedVideoList));
            // generate output and return it as JSONObject
        }
        return fileWriter.writeFile(id, null, "Query result: []");
    }

    public void execute(Writer fileWriter, JSONArray arrayResult) throws IOException {
        switch (criteria) { // add action result to JSONArray object
            case "average":
                arrayResult.add(average(fileWriter));
                break;
            case "awards":
                arrayResult.add(awards(fileWriter));
                break;
            case "filter_description":
                arrayResult.add(filter_description(fileWriter));
                break;
            case "ratings":
                arrayResult.add(ratings(fileWriter));
                break;
            case "favorite":
                arrayResult.add(favorite(fileWriter));
                break;
            case "longest":
                arrayResult.add(longest(fileWriter));
                break;
            case "most_viewed":
                arrayResult.add(mostViewed(fileWriter));
                break;
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

    public String getSort_type() {
        return sort_type;
    }

    public String getCriteria() {
        return criteria;
    }


}
