package users;

import entertainment.Video;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public final class User {
    private final String username;
    private final String subscription;
    private final Map<String, Integer> history;
    private final List<String> favourite;
    private int noRatings;

    /**
     *
     * @param username
     * @param subscription
     * @param history
     * @param favourite
     */

    public User(final String username, final String subscription, final Map<String,
            Integer> history, final List<String> favourite) {
        this.username = username;
        this.subscription = subscription;
        this.history = history;
        this.favourite = favourite;
        this.noRatings = 0;

    }

    /**
     * sort a user list based on criteria and sortType and return it
     * @param sortedUserList
     * @param number
     * @param criteria
     * @param sortType
     * @return
     */
    public static List<User> sortUserList(final List<User> sortedUserList,
                                          final int number, final String criteria,
                                          final String sortType) {
        if (sortType.equals("asc")) {
            sortedUserList.sort(new Comparator<User>() {
                @Override
                public int compare(final User o1, final User o2) {
                    if (Integer.compare(o1.noRatings, o2.noRatings) != 0) {
                        return Integer.compare(o1.noRatings, o2.noRatings);
                    } else {
                        return o1.username.compareTo(o2.username);
                    }
                }
            });
        } else {
            sortedUserList.sort(new Comparator<User>() {
                @Override
                public int compare(final User o1, final User o2) {
                    if (Integer.compare(o1.noRatings, o2.noRatings) != 0) {
                        return Integer.compare(o1.noRatings, o2.noRatings);
                    } else {
                        return o1.username.compareTo(o2.username);
                    }
                }
            }.reversed());
        }

        if (number != 0 && number < sortedUserList.size()) {
            Iterator<User> i = sortedUserList.listIterator(number);
            while (i.hasNext()) {
                i.next();
                i.remove();
            }
        }
        return sortedUserList;
    }

    /**
     * convert a list of users to a list String based on their usernames
     * @param users
     * @return
     */
    public static String toStringUserList(final List<User> users) {
        String output = "";

        output += "[";
        for (User user : users) {
            output += user.username;
            output += ", ";
        }
        // remove last 2 characters -> ", "
        output = output.substring(0, output.length() - 2);
        output += "]";

        return output;
    }

    /**
     * calculate how many views a video has
     * @param video
     * @param users
     * @return
     */
    public static Double numberOfViews(final Video video, final List<User> users) {
        Double noViews = 0.0;
        if (users != null) {
            for (User user : users) {
                if (user.hasSeen(video.getTitle())) {
                    noViews += user.history.get(video.getTitle());
                }
            }
        }
        return noViews;
    }

    /**
     * calculate how many users added the video to their favourite list
     * @param video
     * @param users
     * @return
     */
    public static Double numberOfFavourites(final Video video, final List<User> users) {
        Double noFav = 0.0;
        if (users != null) {
            for (User user : users) {
                for (String favourite : user.getFavourite()) {
                    if (video.getTitle().equals(favourite)) {
                        noFav++;
                    }
                }
            }
        }
        return noFav;
    }

    /**
     * calculate if a user has seen a video
     * @param title
     * @return
     */
    public boolean hasSeen(final String title) {
        return getHistory().containsKey(title);
    }

    public String getUsername() {
        return username;
    }

    public String getSubscription() {
        return subscription;
    }

    public List<String> getFavourite() {
        return favourite;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public int getNoRatings() {
        return noRatings;
    }

    public void setNoRatings(final int noRatings) {
        this.noRatings = noRatings;
    }
}
