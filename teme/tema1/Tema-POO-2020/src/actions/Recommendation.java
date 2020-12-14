package actions;

import common.MainContainer;
import entertainment.Genre;
import entertainment.Video;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import users.User;
import utils.Utils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public final class Recommendation implements Action {
    private final int id;
    private final String type;
    private final String username;
    private final String genre;

    /**
     * basic constructor
     * @param id
     * @param type
     * @param username
     * @param genre
     */
    public Recommendation(final int id, final String type,
                          final String username, final String genre) {
        this.id = id;
        this.type = type;
        this.username = username;
        this.genre = genre;
    }

    /**
     * generates JSONObject based on the task's requirements
     * @param fileWriter
     * @param user
     * @return
     * @throws IOException
     */
    public JSONObject standard(final Writer fileWriter,
                               final User user) throws IOException {
        for (Video video : MainContainer.getVideos()) {
            if (!user.hasSeen(video.getTitle())) {
                return fileWriter.writeFile(id, null,
                        "StandardRecommendation result: " + video.getTitle());
            }
        }
        return fileWriter.writeFile(id, null, "StandardRecommendation cannot be applied!");
    }

    /**
     * generates JSONObject based on the task's requirements
     * @param fileWriter
     * @param user
     * @return
     * @throws IOException
     */
    public JSONObject bestUnseen(final Writer fileWriter,
                                 final User user) throws IOException {
        List<Video> sortedUnseenVideoList = new ArrayList<>(MainContainer.getVideos());

        // remove all videos seen by the user
        sortedUnseenVideoList.removeIf(video -> user.hasSeen(video.getTitle()));
        if (!sortedUnseenVideoList.isEmpty()) { //if list is not empty sort it
            sortedUnseenVideoList = Video.sortVideoList(sortedUnseenVideoList,
                    sortedUnseenVideoList.size(), "ratings_unseen", "desc");
            return fileWriter.writeFile(id, null, "BestRatedUnseenRecommendation result: "
                    + sortedUnseenVideoList.get(0).getTitle());
            // generate output and return it as JSONObject
        }
        return fileWriter.writeFile(id, null, "BestRatedUnseenRecommendation cannot be applied!");
    }

    /**
     * generates JSONObject based on the task's requirements
     * @param fileWriter
     * @param user
     * @return
     * @throws IOException
     */
    public JSONObject popular(final Writer fileWriter,
                              final User user) throws IOException {
        Map<Genre, Integer> popularityMap = new HashMap<>();
        for (Video video : MainContainer.getVideos()) {
            for (Genre genre : video.getGenres()) {
                popularityMap.merge(genre, video.getViews().intValue(), Integer::sum);
            }
        }

        popularityMap = popularityMap.entrySet() // sorting the map
                .stream()
                .sorted(((Comparator<Map.Entry<Genre, Integer>>) (o1, o2) ->
                        o1.getValue().compareTo(o2.getValue())).reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
        for (Genre genre : popularityMap.keySet()) {
            for (Video video : MainContainer.getVideos()) {
                if (video.getGenres().contains(genre) && !user.hasSeen(video.getTitle())) {
                    return fileWriter.writeFile(id, null,
                            "PopularRecommendation result: " + video.getTitle());
                }
            }
        }
        return fileWriter.writeFile(id, null, "PopularRecommendation cannot be applied!");

    }

    /**
     * generates JSONObject based on the task's requirements
     * @param fileWriter
     * @param user
     * @return
     * @throws IOException
     */
    public JSONObject favorite(final Writer fileWriter,
                               final User user) throws IOException {
        Map<Video, Integer> favoriteMap = new HashMap<>();
        for (Video video : MainContainer.getVideos()) {
            if (video.getnrFavourites() != 0) {
                favoriteMap.merge(video, video.getnrFavourites().intValue(), Integer::sum);
            }
        }

        favoriteMap = favoriteMap.entrySet() // sorting the map
                .stream()
                .sorted(((Comparator<Map.Entry<Video, Integer>>) (o1, o2) -> {
                    if (o1.getValue().compareTo(o2.getValue()) != 0) {
                        return o1.getValue().compareTo(o2.getValue());
                    } else { // if value is equal first for first appearance in database
                        for (Video video : MainContainer.getVideos()) {
                            if (o1.getKey().getTitle().equals(video.getTitle())) {
                                return 1;
                            }
                            if (o2.getKey().getTitle().equals(video.getTitle())) {
                                return -1;
                            }
                        }
                        return 0;
                    }
                }).reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));

        for (Video video : favoriteMap.keySet()) {
            if (!user.hasSeen(video.getTitle())) {
                return fileWriter.writeFile(id, null,
                        "FavoriteRecommendation result: " + video.getTitle());
            }
        }
        return fileWriter.writeFile(id, null,
                "FavoriteRecommendation cannot be applied!");

    }

    /**
     * generates JSONObject based on the task's requirements
     * @param fileWriter
     * @param user
     * @return
     * @throws IOException
     */
    public JSONObject search(final Writer fileWriter,
                             final User user) throws IOException {
        List<Video> sortedVideoList = new ArrayList<>(MainContainer.getVideos());

        // remove all actors with no rated videos from list
        // if video isn't in the specified genre,
        // remove it
        sortedVideoList.removeIf(video -> !video.getGenres().contains(Utils.stringToGenre(genre))
                || user.hasSeen(video.getTitle()));

        if (!sortedVideoList.isEmpty()) { //if list is not empty sort it
            sortedVideoList = Video.sortVideoList(sortedVideoList, sortedVideoList.size(),
                    "ratings", "asc");
            return fileWriter.writeFile(id, null, "SearchRecommendation result: "
                    + Video.toStringVideoList(sortedVideoList));
            // generate output and return it as JSONObject
        }
        return fileWriter.writeFile(id, null, "SearchRecommendation cannot be applied!");

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
        User user = Utils.searchUserByName(MainContainer.getUsers(), username);
        switch (type) {
            case "standard":
                arrayResult.add(standard(fileWriter, user));
                break;
            case "best_unseen":
                arrayResult.add(bestUnseen(fileWriter, user));
                break;
            case "popular":
                if (user.getSubscription().equals("PREMIUM")) {
                    arrayResult.add(popular(fileWriter, user));
                } else {
                    arrayResult.add(fileWriter.writeFile(id, null,
                            "PopularRecommendation cannot be applied!"));
                }
                break;
            case "favorite":
                if (user.getSubscription().equals("PREMIUM")) {
                    arrayResult.add(favorite(fileWriter, user));
                } else {
                    arrayResult.add(fileWriter.writeFile(id, null,
                            "FavoriteRecommendation cannot be applied!"));
                }
                break;
            case "search":
                if (user.getSubscription().equals("PREMIUM")) {
                    arrayResult.add(search(fileWriter, user));
                } else {
                    arrayResult.add(fileWriter.writeFile(id, null,
                            "SearchRecommendation cannot be applied!"));
                }
                break;
            default:
                arrayResult.add(null);
        }
    }
}
