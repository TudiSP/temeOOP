package actions;

import actor.Actor;
import common.MainContainer;
import entertainment.Genre;
import entertainment.Video;
import fileio.Writer;
import main.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import users.User;
import utils.Utils;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Recommendation implements Action{
    private int id;
    private String type;
    private String username;
    private String genre;

    public Recommendation(int id, String type, String username, String genre) {
        this.id = id;
        this.type = type;
        this.username = username;
        this.genre = genre;
    }

    public JSONObject standard(Writer fileWriter, User user) throws IOException {
        for(Video video : MainContainer.getVideos()) {
            if(!user.hasSeen(video.getTitle())) {
                return fileWriter.writeFile(id, null, "StandardRecommendation result: " + video.getTitle());
            }
        }
        return fileWriter.writeFile(id, null, "error ->");
    }

    public JSONObject bestUnseen(Writer fileWriter, User user) throws IOException {
        List<Video> sortedUnseenVideoList = new ArrayList<>();
        sortedUnseenVideoList.addAll(MainContainer.getVideos());

        Iterator<Video> i = sortedUnseenVideoList.iterator();
        while(i.hasNext()) { // remove all videos seen by the user
            Video video = i.next();
            if(user.hasSeen(video.getTitle())) {
                i.remove();
            }
        }
        if(!sortedUnseenVideoList.isEmpty()) { //if list is not empty sort it
            sortedUnseenVideoList = Video.sortVideoList(sortedUnseenVideoList, sortedUnseenVideoList.size(), "ratings_unseen", "desc");
            return fileWriter.writeFile(id, null, "BestRatedUnseenRecommendation result: " + sortedUnseenVideoList.get(0).getTitle());
            // generate output and return it as JSONObject
        }
        return fileWriter.writeFile(id, null, "error ->");
    }

    public JSONObject popular(Writer fileWriter, User user) throws IOException {
        Map<Genre, Integer> popularityMap = new HashMap<Genre, Integer>();
        for(Video video : MainContainer.getVideos()){
           for(Genre genre : video.getGenres()) {
               popularityMap.merge(genre, video.getViews().intValue(), Integer::sum);
           }
        }

        popularityMap = popularityMap.entrySet() // sorting the map
                .stream()
                .sorted(new Comparator<Map.Entry<Genre, Integer>>() {
                    @Override
                    public int compare(Map.Entry<Genre, Integer> o1, Map.Entry<Genre, Integer> o2) {
                        return o1.getValue().compareTo(o2.getValue());
                    }
                }.reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                        ));
        for(Genre genre : popularityMap.keySet()) {
            for (Video video : MainContainer.getVideos()) {
                if(video.getGenres().contains(genre) && !user.hasSeen(video.getTitle())) {
                    return fileWriter.writeFile(id, null, "PopularRecommendation result: " + video.getTitle());
                }
            }
        }
        return fileWriter.writeFile(id, null, "error ->");

    }

    public JSONObject favorite(Writer fileWriter, User user) throws IOException {
        Map<Video, Integer> favoriteMap = new HashMap<Video, Integer>();
        for(Video video : MainContainer.getVideos()){
            if(video.getnrFavourites() != 0) {
                favoriteMap.merge(video, video.getnrFavourites().intValue(), Integer::sum);
            }
        }

        favoriteMap = favoriteMap.entrySet() // sorting the map
                .stream()
                .sorted(new Comparator<Map.Entry<Video, Integer>>() {
                    @Override
                    public int compare(Map.Entry<Video, Integer> o1, Map.Entry<Video, Integer> o2) {
                        return o1.getValue().compareTo(o2.getValue());
                    }
                }.reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
        for(Video video : favoriteMap.keySet()) {
            if(!user.hasSeen(video.getTitle())) {
                return fileWriter.writeFile(id, null, "FavoriteRecommendation result: " + video.getTitle());
            }
        }
        return fileWriter.writeFile(id, null, "error ->");

    }

    public JSONObject search(Writer fileWriter, User user) throws IOException {
        List<Video> sortedVideoList = new ArrayList<Video>();
        sortedVideoList.addAll(MainContainer.getVideos());

        Iterator<Video> i = sortedVideoList.iterator();
        while(i.hasNext()) { // remove all actors with no rated videos from list
            Video video = i.next();
            if(!video.getGenres().contains(Utils.stringToGenre(genre)) || user.hasSeen(video.getTitle())) { // if video isn't in the specified genre,
                i.remove();                                               // remove it
            }
        }

        if(!sortedVideoList.isEmpty()) { //if list is not empty sort it
            sortedVideoList = Video.sortVideoList(sortedVideoList, sortedVideoList.size(), "ratings", "asc");
            return fileWriter.writeFile(id, null, "SearchRecommendation result: " + Video.toStringVideoList(sortedVideoList));
            // generate output and return it as JSONObject
        }
        return fileWriter.writeFile(id, null, "error ->");

    }

    public void execute(Writer fileWriter, JSONArray arrayResult) throws IOException{
        User user = Utils.searchUserByName(MainContainer.getUsers(), username);
        switch (type) {
            case "standard":
                arrayResult.add(standard(fileWriter, user));
                break;
            case "best_unseen":
                arrayResult.add(bestUnseen(fileWriter, user));
                break;
            case "popular":
                arrayResult.add(popular(fileWriter, user));
                break;
            case "favorite":
                arrayResult.add(favorite(fileWriter, user));
                break;
            case "search":
                arrayResult.add(search(fileWriter, user));
                break;
        }
    }
}
