package main;

import actions.Action;
import actions.Command;
import actions.Query;
import actions.Recommendation;
import common.MainContainer;
import entertainment.Show;
import entertainment.Video;
import net.sf.json.JSON;
import org.json.JSONObject;
import utils.Utils;
import actor.Actor;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import entertainment.Movie;
import fileio.*;
import org.json.simple.JSONArray;
import users.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.MarshalledObject;
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
        List<UserInputData> userdatapl = input.getUsers();  // Input for users
        List<User> users = new ArrayList<>();
        for(UserInputData userdata : userdatapl) {
            User user = new User(userdata.getUsername(), userdata.getSubscriptionType(), userdata.getHistory(), userdata.getFavoriteMovies());
            users.add(user);
        }
        MainContainer.setUsers(users);

        List<MovieInputData> moviedatapl = input.getMovies();  //Input for Movies
        List<Movie> movies = new ArrayList<>();
        for(MovieInputData moviedata : moviedatapl) {
            Movie movie = new Movie(moviedata.getYear(), moviedata.getTitle(), moviedata.getGenres(), moviedata.getDuration(), moviedata.getCast(),
                    "movie");
            movies.add(movie);
        }

        List<SerialInputData> serialdatapl = input.getSerials();  //Input for Serials(Show)
        List<Show> serials = new ArrayList<>();
        for(SerialInputData serialdata : serialdatapl) {
            Show serial = new Show(serialdata.getYear(), serialdata.getTitle(), serialdata.getCast(), serialdata.getGenres(), serialdata.getNumberSeason(),
                    serialdata.getSeasons(), "serial");
            serials.add(serial);
        }
        List<Video> videos = new ArrayList<>(); // add all movies and serials into one List
        videos.addAll(movies);
        videos.addAll(serials);

        List<ActorInputData> actordatapl = input.getActors(); // Input for Actors
        List<Actor> actors = new ArrayList<>();
        for(ActorInputData actordata : actordatapl) {
            List<Video> filmography = Utils.stringToVideoSearchList(videos, actordata.getFilmography());
            Actor actor = new Actor(actordata.getName(), actordata.getCareerDescription(), filmography, actordata.getAwards());
            actors.add(actor);
        }
        MainContainer.setActors(actors);

        for(Video video : videos) {
            if(video instanceof Movie) {
                List<Actor> actorsMovie = Utils.stringToActorSearchList(actors, ((Movie) video).getActorNames());
                if(actorsMovie != null) { //populate actors field in movie only if list is not null
                    ((Movie) video).setActors(actorsMovie);
                }
            }

            if(video instanceof Show) {
                List<Actor> actorsShow = Utils.stringToActorSearchList(actors, ((Show) video).getActorNames());
                if(actorsShow != null) { //populate actors field in show only if list is not null
                    ((Show) video).setActors(actorsShow);
                }
            }
        }
        MainContainer.setVideos(videos);


        List<ActionInputData> actiondatapl = input.getCommands();  //Input for Actions
        List<Action> actions = new ArrayList<>();
        for (ActionInputData actiondata : actiondatapl) {
            Action action = null;
            switch (actiondata.getActionType()){
                case "command":
                    action = new Command(actiondata.getActionId(), actiondata.getType(), actiondata.getUsername(),
                            actiondata.getTitle(), actiondata.getGrade(), actiondata.getSeasonNumber());
                    break;
                case "query":
                    action = new Query(actiondata.getActionId(), actiondata.getNumber(), actiondata.getFilters(), actiondata.getSortType(),
                            actiondata.getCriteria());
                    break;
                case "recommendation":
                    action = new Recommendation(actiondata.getActionId(), actiondata.getType(), actiondata.getUsername());
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + actiondata.getActionType());
            }
            if(action != null) {
                actions.add(action);
            }
        }

        for(Action action : actions) { // executing actions
            /*if(action instanceof Command) {
                System.out.println(((Command) action).getId() + ", command, " + ((Command) action).getType());
            }
            if(action instanceof Query) {
                System.out.println(((Query) action).getId() + ", query, " + ((Query) action).getCriteria());
            }
            System.out.println("actors: " + MainContainer.getActors().toString());
            System.out.println("users: " + MainContainer.getUsers().toString());
            System.out.println("videos: " + MainContainer.getVideos().toString());
             */
            action.execute(fileWriter, arrayResult);
        }

        fileWriter.closeJSON(arrayResult);
    }
}
