package main;

import actions.Action;
import actions.Command;
import actions.Query;
import actions.Recommendation;
import actor.Actor;
import checker.Checker;
import checker.Checkstyle;
import common.Constants;
import common.MainContainer;
import entertainment.Movie;
import entertainment.Show;
import entertainment.Video;
import fileio.*;
import org.json.simple.JSONArray;
import users.User;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation
        List<UserInputData> userdatapl = input.getUsers(); // Input for users
        List<User> users = new ArrayList<>();
        for (UserInputData userdata : userdatapl) {
            User user = new User(userdata.getUsername(), userdata.getSubscriptionType(),
                    userdata.getHistory(), userdata.getFavoriteMovies());
            users.add(user);
        }
        MainContainer.setUsers(users);

        List<MovieInputData> moviedatapl = input.getMovies(); //Input for Movies
        List<Movie> movies = new ArrayList<>();
        for (MovieInputData moviedata : moviedatapl) {
            Movie movie = new Movie(moviedata.getYear(), moviedata.getTitle(),
                    moviedata.getGenres(), moviedata.getDuration(),
                    moviedata.getCast(), "movie");
            movies.add(movie);
        }

        List<SerialInputData> serialdatapl = input.getSerials(); //Input for Serials(Show)
        List<Show> serials = new ArrayList<>();
        for (SerialInputData serialdata : serialdatapl) {
            Show serial = new Show(serialdata.getYear(), serialdata.getTitle(),
                    serialdata.getCast(), serialdata.getGenres(),
                    serialdata.getNumberSeason(),
                    serialdata.getSeasons(), "serial");
            serials.add(serial);
        }
        List<Video> videos = new ArrayList<>(); // add all movies and serials into one List
        videos.addAll(movies);
        videos.addAll(serials);


        List<ActorInputData> actordatapl = input.getActors(); // Input for Actors
        List<Actor> actors = new ArrayList<>();
        for (ActorInputData actordata : actordatapl) {
            List<Video> filmography = Utils.stringToVideoSearchList(videos,
                    actordata.getFilmography());
            Actor actor = new Actor(actordata.getName(),
                    actordata.getCareerDescription(), filmography,
                    actordata.getAwards());
            actors.add(actor);
        }
        MainContainer.setActors(actors);

        for (Video video : videos) {
            if (video instanceof Movie) {
                List<Actor> actorsMovie = Utils.stringToActorSearchList(actors,
                        ((Movie) video).getActorNames());
                //populate actors field in movie only if list is not null
                if (actorsMovie != null) {
                    ((Movie) video).setActors(actorsMovie);
                }
            }

            if (video instanceof Show) {
                List<Actor> actorsShow = Utils.stringToActorSearchList(actors,
                        ((Show) video).getActorNames());
                //populate actors field in show only if list is not null
                if (actorsShow != null) {
                    ((Show) video).setActors(actorsShow);
                }
            }
        }
        MainContainer.setVideos(videos);


        List<ActionInputData> actiondatapl = input.getCommands(); //Input for Actions
        List<Action> actions = new ArrayList<>();
        for (ActionInputData actiondata : actiondatapl) {
            Action action =
                    switch (actiondata.getActionType()) {
                        case "command" -> new Command(actiondata.getActionId(),
                                actiondata.getType(), actiondata.getUsername(),
                                actiondata.getTitle(), actiondata.getGrade(),
                                actiondata.getSeasonNumber());
                        case "query" -> new Query(actiondata.getActionId(),
                                actiondata.getNumber(), actiondata.getFilters(),
                                actiondata.getSortType(), actiondata.getCriteria(),
                                actiondata.getObjectType());
                        case "recommendation" -> new Recommendation(actiondata.getActionId(),
                                actiondata.getType(), actiondata.getUsername(),
                                actiondata.getGenre());
                        default -> throw new IllegalStateException("Unexpected value: "
                                + actiondata.getActionType());
                    };
            actions.add(action);
        }

        for (Action action : actions) { // executing actions
            action.execute(fileWriter, arrayResult);
        }

        fileWriter.closeJSON(arrayResult);
    }
}
