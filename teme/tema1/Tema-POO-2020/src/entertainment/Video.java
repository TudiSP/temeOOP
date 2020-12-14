package entertainment;

import common.MainContainer;
import users.User;
import utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Video {
    protected int launchYear;
    protected String title;
    protected List<Genre> genres;
    protected String type;
    protected Double averageRating;
    protected Double nrFavourites;
    protected Double duration;
    protected Double views;

    /**
     *
     * @param launchYear
     * @param title
     * @param genres
     * @param type
     */
    public Video(final int launchYear, final String title, final ArrayList<String> genres,
                 final String type) {
        this.launchYear = launchYear;
        this.title = title;
        this.genres = new ArrayList<>();
        for (int i = 0; i < genres.size(); i++) {
            // convert Arraylist<String> to List<Genre>/Arraylist<Genre>
            this.genres.add(Utils.stringToGenre(genres.get(i)));
        }
        this.type = type;
        this.nrFavourites = User.numberOfFavourites(this, MainContainer.getUsers());
        this.views = User.numberOfViews(this, MainContainer.getUsers());
        this.averageRating = 0.0;
    }

    /**
     * sorts video list based on a criteria and sorting order and trims it's size
     * to the numberOfVideos parameter, or less.
     * @param videos
     * @param numberOfVideos
     * @param criteria
     * @param sortingOrder
     * @return
     */
    public static List<Video> sortVideoList(final List<Video> videos, final int numberOfVideos,
                                            final String criteria, final String sortingOrder) {
        List<Video> sortedVideoList = videos;
        if (sortingOrder.equals("asc")) {
            sortedVideoList.sort(new Comparator<Video>() {
                @Override
                public int compare(final Video o1, final Video o2) {
                    switch (criteria) {
                        case "ratings_unseen":
                            return o1.averageRating.compareTo(o2.averageRating);
                        case "ratings":
                            if (o1.averageRating.compareTo(o2.averageRating) != 0) {
                                return o1.averageRating.compareTo(o2.averageRating);
                            }
                            return o1.title.compareTo(o2.title);
                        case "favorite":
                            if (o1.nrFavourites.compareTo(o2.nrFavourites) != 0) {
                                return o1.nrFavourites.compareTo(o2.nrFavourites);
                            }
                            return o1.title.compareTo(o2.title);
                        case "longest":
                            if (o1.duration.compareTo(o2.duration) != 0) {
                                return o1.duration.compareTo(o2.duration);
                            }
                            return o1.title.compareTo(o2.title);
                        case "most_viewed":
                            if (o1.views.compareTo(o2.views) != 0) {
                                return o1.views.compareTo(o2.views);
                            }
                            return o1.title.compareTo(o2.title);
                        default:
                            return o1.title.compareTo(o2.title);
                    }
                }
            });
        } else { // do in descendent order
            sortedVideoList.sort(new Comparator<Video>() {
                @Override
                public int compare(final Video o1, final Video o2) {
                    switch (criteria) {
                        case "ratings_unseen":
                            return o1.averageRating.compareTo(o2.averageRating);
                        case "ratings":
                            if (o1.averageRating.compareTo(o2.averageRating) != 0) {
                                return o1.averageRating.compareTo(o2.averageRating);
                            }
                            return o1.title.compareTo(o2.title);
                        case "favorite":
                            if (o1.nrFavourites.compareTo(o2.nrFavourites) != 0) {
                                return o1.nrFavourites.compareTo(o2.nrFavourites);
                            }
                            return o1.title.compareTo(o2.title);
                        case "longest":
                            if (o1.duration.compareTo(o2.duration) != 0) {
                                return o1.duration.compareTo(o2.duration);
                            }
                            return o1.title.compareTo(o2.title);
                        case "most_viewed":
                            if (o1.views.compareTo(o2.views) != 0) {
                                return o1.views.compareTo(o2.views);
                            }
                            return o1.title.compareTo(o2.title);
                        default:
                            return o1.title.compareTo(o2.title);
                    }
                }
            }.reversed());
        }

        if (numberOfVideos != 0 && numberOfVideos < sortedVideoList.size()) {
            Iterator<Video> i = sortedVideoList.listIterator(numberOfVideos);
            while (i.hasNext()) {
                i.next();
                i.remove();
            }
        }
        return sortedVideoList;
    }

    /**
     * takes a video list and converts it into a string based on their titles
     * @param videos
     * @return
     */
    public static String toStringVideoList(final List<Video> videos) {
        String output = "";

        output += "[";
        for (Video video : videos) {
            output += video.title;
            output += ", ";
        }
        output = output.substring(0, output.length() - 2); // remove last 2 characters -> ", "
        output += "]";

        return output;
    }

    /**
     * check the filters for this video
     * @param filters
     * @return
     */
    public final boolean checkFilters(final List<List<String>> filters) {
        if (filters.get(0).get(0) != null) {
            if (launchYear != Integer.parseInt(filters.get(0).get(0))) { // check for first filter
                return false;
            }
        }

        if (filters.get(1).get(0) != null) {
            for (String genre : filters.get(1)) {
                if (!genres.contains(Utils.stringToGenre(genre))) { //check for genres
                    return false;
                }
            }
        }
        return true;
    }

    public final Double getAverageRating() {
        return averageRating;
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(final String title) {
        this.title = title;
    }

    public final List<Genre> getGenres() {
        return genres;
    }

    public final void setGenres(final List<Genre> genres) {
        this.genres = genres;
    }

    public final String getType() {
        return type;
    }

    public final void setType(final String type) {
        this.type = type;
    }

    public final Double getnrFavourites() {
        return nrFavourites;
    }

    public final void setNrFavourites(final Double nrFavourites) {
        this.nrFavourites = nrFavourites;
    }

    public final Double getViews() {
        return views;
    }

    public final void setViews(final Double views) {
        this.views = views;
    }
}
