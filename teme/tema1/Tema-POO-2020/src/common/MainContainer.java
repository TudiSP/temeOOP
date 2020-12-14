package common;

import actor.Actor;
import entertainment.Video;
import users.User;

import java.util.List;

public final class MainContainer {
    private static List<Video> videos;
    private static List<User> users;
    private static List<Actor> actors;

    private MainContainer() {

    }

    public static List<Video> getVideos() {
        return videos;
    }

    public static void setVideos(final List<Video> videos) {
        MainContainer.videos = videos;
    }

    public static List<User> getUsers() {
        return users;
    }

    public static void setUsers(final List<User> users) {
        MainContainer.users = users;
    }

    public static List<Actor> getActors() {
        return actors;
    }

    public static void setActors(final List<Actor> actors) {
        MainContainer.actors = actors;
    }

}
