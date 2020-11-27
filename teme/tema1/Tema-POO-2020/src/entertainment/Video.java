package entertainment;

import actor.Actor;
import common.MainContainer;
import users.User;
import utils.Utils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public abstract class Video {
    protected int launchYear;
    protected String title;
    protected List<Genre> genres;
    protected String type;
    protected Double averageRating;
    protected Double nrFavourites;
    protected Double duration;
    protected Double views;

    public Video(int launchYear, String title, ArrayList<String> genres, String type) {
        this.launchYear = launchYear;
        this.title = title;
        this.genres = new ArrayList<>();
        for(int i = 0; i < genres.size(); i++) {
            this.genres.add(Utils.stringToGenre(genres.get(i))); // convert Arraylist<String> to List<Genre>/Arraylist<Genre>
        }
        this.type = type;
        this.nrFavourites = User.numberOfFavourites(this, MainContainer.getUsers());
        this.views = User.numberOfViews(this, MainContainer.getUsers());
        this.averageRating = 0.0;
    }

    public static List<Video> sortVideoList(List<Video> videos, int numberOfVideos, String criteria, String sortingOrder) {
        List<Video> sortedVideoList = videos;
        if(sortingOrder.equals("asc")) {
            sortedVideoList.sort(new Comparator<Video>() {
                @Override
                public int compare(Video o1, Video o2) {
                    switch (criteria) {
                        case "ratings":
                            if(o1.averageRating.compareTo(o2.averageRating) != 0) {
                                return o1.averageRating.compareTo(o2.averageRating);
                            }
                            return o1.title.compareTo(o2.title);
                        case "favorite":
                            if(o1.nrFavourites.compareTo(o2.nrFavourites) != 0) {
                                return o1.nrFavourites.compareTo(o2.nrFavourites);
                            }
                            return o1.title.compareTo(o2.title);
                        case "longest":
                            if(o1.duration.compareTo(o2.duration) != 0) {
                                return o1.duration.compareTo(o2.duration);
                            }
                            return o1.title.compareTo(o2.title);
                        case "most_viewed":
                            if(o1.views.compareTo(o2.views) != 0) {
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
                public int compare(Video o1, Video o2) {
                    switch (criteria) {
                        case "ratings":
                            if(o1.averageRating.compareTo(o2.averageRating) != 0) {
                                return o1.averageRating.compareTo(o2.averageRating);
                            }
                            return o1.title.compareTo(o2.title);
                        case "favorite":
                            if(o1.nrFavourites.compareTo(o2.nrFavourites) != 0) {
                                return o1.nrFavourites.compareTo(o2.nrFavourites);
                            }
                            return o1.title.compareTo(o2.title);
                        case "longest":
                            if(o1.duration.compareTo(o2.duration) != 0) {
                                return o1.duration.compareTo(o2.duration);
                            }
                            return o1.title.compareTo(o2.title);
                        case "most_viewed":
                            if(o1.views.compareTo(o2.views) != 0) {
                                return o1.views.compareTo(o2.views);
                            }
                            return o1.title.compareTo(o2.title);
                        default:
                            return o1.title.compareTo(o2.title);
                    }
                }
            }.reversed());
        }

        if(numberOfVideos != 0 && numberOfVideos < sortedVideoList.size()) {
            Iterator<Video> i = sortedVideoList.listIterator(numberOfVideos);
            while (i.hasNext()) {
                i.next();
                i.remove();
            }
        }
        return sortedVideoList;
    }

    public static String toStringVideoList(List<Video> videos) {
        String output = new String();

        output += "[";
        for(Video video : videos) {
            output += video.title;
            output += ", ";
        }
        output = output.substring(0, output.length() - 2); // remove last 2 characters -> ", "
        output += "]";

        return output;
    }

    public boolean checkFilters(List<List<String>> filters) {
        if(filters.get(0).get(0) != null) {
            if (launchYear != Integer.parseInt(filters.get(0).get(0))) { // check for first filter
                return false;
            }
        }

        if(filters.get(1).get(0) != null) {
            for (String genre : filters.get(1)) {
                if (!genres.contains(Utils.stringToGenre(genre))) { //check for genres
                    return false;
                }
            }
        }
        return true;
    }

    public Double getAverageRating() {
        return averageRating;
    }
    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public int getLaunchYear() {
        return launchYear;
    }

    public void setLaunchYear(int launchYear) {
        this.launchYear = launchYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getnrFavourites() {
        return nrFavourites;
    }

    public void setNrFavourites(Double nrFavourites) {
        this.nrFavourites = nrFavourites;
    }

    @Override
    public String toString() {
        return "Video{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
