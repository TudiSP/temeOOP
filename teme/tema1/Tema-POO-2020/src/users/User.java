package users;

import common.MainContainer;
import entertainment.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class User {
    private String username;
    private String subscription ;
    private Map<String, Integer> history;
    private List<String> favourite;

    public User(String username, String subscription, Map<String, Integer> history, List<String> favourite) {
        this.username = username;
        this.subscription = subscription;
        this.history = history;
        this.favourite = favourite;

    }
    public boolean hasSeen(String title) {
        if(getHistory().containsKey(title)) {
            return true;
        }
        return false;
    }

    public static Double numberOfViews(Video video, List<User> users) {
        Double no_views = 0.0;
        if(users != null) {
            for (User user : users) {
                if (user.hasSeen(video.getTitle())) {
                    no_views++;
                }
            }
        }
        return no_views;
    }

    public static Double numberOfFavourites(Video video, List<User> users) {
        Double no_fav = 0.0;
        if(users != null) {
            for (User user : users) {
                for (String favourite : user.getFavourite()) {
                    if (video.getTitle().equals(favourite)) {
                        no_fav++;
                    }
                }
            }
        }
        return no_fav;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public List<String> getFavourite() {
        return favourite;
    }

    public void setFavourite(List<String> favourite) {
        this.favourite = favourite;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public void setHistory(Map<String, Integer> history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", subscription='" + subscription + '\'' +
                '}';
    }
}
