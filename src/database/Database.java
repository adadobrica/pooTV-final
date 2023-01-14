package database;

import actions.OnPageRateAction;
import actions.OnPageLikeAction;
import actions.OnPageWatchAction;
import actions.ChangePageActionDetails;
import actions.ChangePageActionMovie;
import actions.Filter;
import actions.OnPageBuyTokensAction;
import actions.OnPageDefaultAction;
import actions.OnPageFilterAction;
import actions.OnPagePremiumAction;
import actions.OnPagePurchaseAction;
import actions.OnPageSearchAction;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import input.ActionsInput;
import input.CredentialsInput;
import input.InputData;
import input.MoviesInput;
import input.UserInput;

import movies.Movies;
import output.Output;
import output.ParseOutput;
import recommendations.ContextRecommendations;
import recommendations.GenreNotifierStrategy;
import recommendations.LikedNotifierStrategy;
import recommendations.NormalNotifierStrategy;
import recommendations.Notifications;

import user.Credentials;
import user.User;
import user.UserController;
import user.UserView;

import java.util.ArrayList;
import java.util.Stack;

public class Database {
    private ArrayList<UserInput> databaseUsers;
    private ArrayList<ActionsInput> actions;
    private String currentPage;
    private String previousPage;
    private final String defaultPage = "unauthenticated homepage";
    private final String authenticatedPage = "authenticated homepage";
    private User currentUser = new User();
    private  ArrayList<MoviesInput> movies;
    private ArrayList<Movies> currentMoviesForUser;
    private ArrayList<Movies> currentMovieOnPage;

    private Stack<String> pageStack = new Stack<>();

    public Database(final InputData data) {
        this.actions = data.getActions();
        this.movies = data.getMovies();
        this.databaseUsers = data.getUsers();
    }

    /**
     * Main method that parses the given input, executing different actions
     * according to its type: "on page" or "change page"
     * @param output the array containing the entire output
     * @param objectMapper an objectMapper that helps for outputting nodes
     */

    public void parseInput(final ArrayNode output, final ObjectMapper objectMapper) {
        JsonNode outputNode = objectMapper.createObjectNode();
        currentPage = defaultPage;
        String page, feature, type;
        CredentialsInput credentialsInput = new CredentialsInput();
        int counter = 0;
        for (ActionsInput action : actions) {
            type = action.getType();
            page = action.getPage();

            switch (type) {
                case "change page" -> {
                    changePage(page, output, outputNode, objectMapper, action);
                }

                case "on page" -> {
                    feature = action.getFeature();
                    page = action.getPage();
                    onPage(page, feature, output, outputNode, objectMapper,
                            action, type, pageStack);
                }
                case "back" -> {
                    back(pageStack, outputNode, output, objectMapper, currentPage);
                }
                case "database" -> {
                    database(outputNode, output, objectMapper, action);
                }
                default -> {
                    break;
                }
            }
            counter++;
            if (counter == actions.size()) {
                sendNotifications(output, outputNode, objectMapper);
            }
        }
    }

    /**
     * Method that sends notifications to the user
     * @param output an arrayNode containing the output
     * @param outputNode a JsonNode for outputting a node in an array
     * @param objectMapper an objectMapper that helps for the output format
     */

    public void sendNotifications(final ArrayNode output, final JsonNode outputNode,
                                  final ObjectMapper objectMapper) {
        ContextRecommendations contextRecommendations = new ContextRecommendations();

        if (currentUser.getCredentials().getAccountType().equals("premium")) {
            if (currentUser.getIsSubscribed() == 0) {
                int liked = 0;
                ArrayList<String> likedGenres = new ArrayList<>();
                for (int i = 0; i < currentUser.getLikedMovies().size(); i++) {
                    liked = 1;
                    likedGenres.addAll(currentUser.getLikedMovies().get(i).getGenres());
                }
                int stop = 0;
                String recMovie;
                if (liked == 1) {
                    contextRecommendations.setStrategy(new LikedNotifierStrategy());
                    currentUser = contextRecommendations.executeStrategy(new Notifications(),
                            currentUser, output, outputNode, objectMapper,
                            movies, null);
                } else {
                    contextRecommendations.setStrategy(new NormalNotifierStrategy());
                    currentUser = contextRecommendations.executeStrategy(new Notifications(),
                            currentUser, output, outputNode, objectMapper,
                            movies, null);
                }
            } else {
                contextRecommendations.setStrategy(new GenreNotifierStrategy());
                currentUser = contextRecommendations.executeStrategy(new Notifications(),
                        currentUser, output, outputNode, objectMapper, movies,
                        currentMoviesForUser);
            }
        }
    }

    /**
     * Method that either deletes or adds a movie to the database
     * @param outputNode a JsonNode for outputting a node in an array
     * @param output an arrayNode containing the output
     * @param objectMapper an objectMapper that helps for the output format
     * @param action the action taken from the input
     */

    public void database(final JsonNode outputNode, final ArrayNode output,
                         final ObjectMapper objectMapper,
                         final ActionsInput action) {
        String feature = action.getFeature();
        DatabaseFactory databaseFactory = new DatabaseFactory();
        if (feature.equals("delete")) {
            String deletedMovie = action.getDeletedMovie();
            // check if movie exists
            int exists = 0;
            for (MoviesInput movie : movies) {
                if (movie.getName().equals(deletedMovie)) {
                    exists = 1;
                    break;
                }
            }
            DatabaseAction deleteAction = databaseFactory.databaseAction("delete");
            movies = deleteAction.execute(null, deletedMovie, movies);
        }
        if (feature.equals("add")) {
            MoviesInput addedMovie = action.getAddedMovie();
            // check if movie already exists in the database
            String movieName = addedMovie.getName();
            int exists = 0;
            for (MoviesInput movie : movies) {
                if (movie.getName().equals(movieName)) {
                    exists = 1;
                    break;
                }
            }
            if (exists == 1) {
                ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
            } else {
                DatabaseAction addAction = databaseFactory.databaseAction("add");
                movies = addAction.execute(addedMovie, null, movies);

                if (currentUser.getIsSubscribed() == 1) {
                    // check if user is subscribed to the new genre added
                    int hasBeenAdded = 0;
                    for (int i = 0; i < currentUser.getSubscribedGenre().size(); i++) {
                        for (int j = 0; j < addedMovie.getGenres().size(); j++) {
                            if (currentUser.getSubscribedGenre().get(i)
                                    .equals(addedMovie.getGenres().get(j))) {
                                Notifications addedNotifications = new Notifications();
                                addedNotifications.setMessage("ADD");
                                addedNotifications.setMovieName(addedMovie.getName());
                                currentUser.getNotifications().add(addedNotifications);
                                hasBeenAdded = 1;
                                break;
                            }
                        }
                        if (hasBeenAdded == 1) {
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Method that marks a user as subscribed
     * @param currentPage the current page the user is on
     * @param outputNode a JsonNode for outputting a node in an array
     * @param output an arrayNode containing the output
     * @param objectMapper an objectMapper that helps for the output format
     * @param action the action taken from the input
     */

    public void subscribe(final String currentPage, final JsonNode outputNode,
                          final ArrayNode output, final ObjectMapper objectMapper,
                          final ActionsInput action) {
        String subscribedGenre = action.getSubscribedGenre();
        int valid = 0;
        for (int i = 0; i < currentUser.getSubscribedGenre().size(); i++) {
            if (currentUser.getSubscribedGenre().get(i).equals(subscribedGenre)) {
                valid = 1;
                break;
            }
        }
        if (currentUser.getIsSubscribed() == 1 && valid == 1) {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
            this.currentPage = defaultPage;
            return;
        }
        if (!currentPage.equals("see details")) {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
        } else {
            currentUser.setIsSubscribed(1);
            //currentUser.setSubscribedGenre(action.getSubscribedGenre());
            currentUser.getSubscribedGenre().add(action.getSubscribedGenre());
        }
    }

    /**
     * Method that allows the user to go back to the previous page
     * @param pageStack a stack containing the user's navigated pages
     * @param outputNode a JsonNode for outputting a node in an array
     * @param output an arrayNode containing the output
     * @param objectMapper an objectMapper that helps for the output format
     * @param currentPage string containing the current page
     */

    public void back(final Stack<String> pageStack, final JsonNode outputNode,
                     final ArrayNode output, final ObjectMapper objectMapper,
                     final String currentPage) {

        if (pageStack.isEmpty()) {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
            if (this.currentPage.equals("see details")) {
                pageStack.push("see details");
            }
            return;
        }
        if (pageStack.size() == 1) {
            String lastPage = pageStack.pop();
            if (currentUser.getTokensCount() == 0
                    && currentUser.getCredentials().getAccountType().equals("standard")) {
                currentMoviesForUser = new ArrayList<>();
                ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
                return;
            }
            if (lastPage.equals(authenticatedPage)) {
                ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
                return;
            }
            if (lastPage.equals("see details")) {
                ParseOutput.getInstance().printNormalOutput(output, outputNode, objectMapper,
                        currentMoviesForUser, currentUser);
                this.currentPage = "see details";
                return;
            }
            return;
        }
        String lastPage = pageStack.pop();

        if (!pageStack.isEmpty()) {
            String previousPage = pageStack.pop();

            if (currentUser.getTokensCount() == 0
                    && currentUser.getCredentials().getAccountType().equals("standard")) {

                currentMoviesForUser = new ArrayList<>();
                ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
                return;
            }
            this.currentPage = previousPage;

            if (this.currentPage.equals("movies") && !lastPage.equals("movies")) {
                ParseOutput.getInstance().printNormalOutput(output, outputNode, objectMapper,
                        currentMoviesForUser, currentUser);
            } else if (lastPage.equals("see details")) {
                this.currentPage = "movies";
                ParseOutput.getInstance().printNormalOutput(output, outputNode, objectMapper,
                        currentMoviesForUser, currentUser);
            }
        }
        if (lastPage.equals(authenticatedPage)) {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
        }
    }

    /**
     * Method that only executes "on page" actions
     * @param page the given page
     * @param feature the feature the user wants to have
     * @param output an arrayNode containing the output
     * @param outputNode a JsonNode for outputting a node in an array
     * @param objectMapper an objectMapper that helps for the output format
     * @param action the given action
     * @param type represents the type of action implemented in the method; in this case,
     *             type is always "on page"
     */

    public void onPage(final String page, final String feature, final ArrayNode output,
                       JsonNode outputNode, final ObjectMapper objectMapper,
                       final ActionsInput action, final String type, Stack<String> pageStack) {
        CredentialsInput credentialsInput;
        if (feature.equals("subscribe")) {
            subscribe(currentPage, outputNode, output, objectMapper, action);
        }
        if (feature.equals("like") || feature.equals("watch") || feature.equals("rate")) {
            if (currentPage.equals(defaultPage)) {
                ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
                return;
            }
        }
        if (feature.equals("login") && currentPage.equals("login")
                && previousPage.equals(defaultPage)) {

            credentialsInput = action.getCredentialsInput();
            Credentials credentials = new Credentials(credentialsInput);

            OnPageDefaultAction loginAction = new OnPageDefaultAction(type, page,
                    feature, credentials);
            int error = loginAction.checkLoginCredentials(credentials, databaseUsers);
            if (error == -1) {
                ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
                currentPage = defaultPage;
            } else {
                pageStack = new Stack<>();
                currentUser = loginAction.loginSuccess(databaseUsers,
                        credentialsInput, currentUser);
                updateCurrentUser();
                ParseOutput.getInstance().printNormalOutput(output, outputNode, objectMapper,
                        new ArrayList<>(), currentUser);

                currentPage = authenticatedPage;
                pageStack.push(currentPage);
                this.pageStack = pageStack;
                ChangePageActionMovie changePageActionMovie = new ChangePageActionMovie();
                currentMoviesForUser = changePageActionMovie.printCurrentMoviesList(currentUser,
                        movies);
            }
        } else if (feature.equals("login") && currentPage.equals(authenticatedPage)) {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
            currentPage = defaultPage;
        } else if (feature.equals("login") && currentPage.equals(defaultPage)
                && currentUser != null) {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
            currentPage = authenticatedPage;
        }

        if (feature.equals("register") && previousPage.equals(defaultPage)) {
            credentialsInput = action.getCredentialsInput();
            OnPageDefaultAction registerAction = new OnPageDefaultAction();
            registerAction.register(credentialsInput, databaseUsers);

            UserView userView = new UserView();
            currentUser = userView.printUserDetails(credentialsInput, output,
                    outputNode, objectMapper);
            currentPage = authenticatedPage;
            this.pageStack.push(currentPage);
            ChangePageActionMovie changePageActionMovie = new ChangePageActionMovie();
            currentMoviesForUser = changePageActionMovie.printCurrentMoviesList(currentUser,
                    movies);
        }

        if (feature.equals("search") && currentPage.equals("movies")) {
            OnPageSearchAction onPageSearchAction = new OnPageSearchAction();
            ArrayList<Movies> searchedMovies = onPageSearchAction.search(currentMoviesForUser,
                    action.getStartsWith());
            ParseOutput.getInstance().printNormalOutput(output, outputNode, objectMapper,
                    searchedMovies, currentUser);
        }

        if (feature.equals("filter") && currentPage.equals("movies")) {
            Filter filter = action.getFilters();
            OnPageFilterAction onPageFilterAction = new OnPageFilterAction();
            ArrayList<Movies> filteredMovies = onPageFilterAction.filter(currentMoviesForUser,
                    filter, movies);
            currentMoviesForUser = filteredMovies;
            ParseOutput.getInstance().printNormalOutput(output, outputNode, objectMapper,
                    filteredMovies, currentUser);
        }

        if (feature.equals("filter") && !currentPage.equals("movies")) {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
        }

        if (feature.equals("buy tokens") && currentPage.equals("upgrades")) {
            int count = Integer.parseInt(action.getCount());
            OnPageBuyTokensAction tokens = new OnPageBuyTokensAction();
            currentUser = tokens.buyTokens(Integer.parseInt(action.getCount()), currentUser);
            System.out.println(currentUser.getTokensCount());
            updateDatabaseUsers();
        }

        if (feature.equals("buy premium account") && currentPage.equals("upgrades")) {
            currentUser = new OnPagePremiumAction().upgradePremium(currentUser);
            updateDatabaseUsers();
        }

        if (feature.equals("purchase") && currentPage.equals("see details")
                && action.getMovie() != null) {
            String movieName = action.getMovie();
            currentUser = new OnPagePurchaseAction().purchaseMovieFromList(currentUser,
                    currentMoviesForUser, movieName, output, outputNode, objectMapper);
            updateDatabaseUsers();
        } else if (feature.equals("purchase") && currentPage.equals("see details")) {
            if (currentMovieOnPage == null) {
                ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
            }

            currentUser = new OnPagePurchaseAction().purchaseMovie(currentUser, currentMovieOnPage,
                    output, outputNode, objectMapper);
            updateDatabaseUsers();
        }

        if (feature.equals("watch") && currentPage.equals("see details")
                && action.getMovie() != null) {
            String movieName = action.getMovie();
            currentUser = new OnPageWatchAction().watchMovieFromList(currentUser, movieName,
                    output, outputNode, objectMapper);
            updateDatabaseUsers();

        } else if (feature.equals("watch") && currentPage.equals("see details")) {
            if (currentMovieOnPage == null) {
                ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
            } else {
                currentUser = new OnPageWatchAction().watchMovie(currentUser,
                        currentMoviesForUser, currentMovieOnPage, output, outputNode,
                        objectMapper);
                updateDatabaseUsers();
            }
        }

        if (feature.equals("like") && currentPage.equals("see details")
                && action.getMovie() != null) {
            String movieName = action.getMovie();
            int valid = 0;
            Movies updatedMovie = new Movies();
            for (int i = 0; i < currentUser.getWatchedMovies().size(); i++) {
                if (currentUser.getWatchedMovies().get(i).getName().equals(movieName)) {
                    updatedMovie = new OnPageLikeAction().findMovieFromList(currentUser, i);
                    valid = 1;
                    break;
                }
            }
            if (valid == 1) {
                ParseOutput.getInstance().printNormalOutput(output, outputNode, objectMapper,
                        currentUser.getWatchedMovies(), currentUser);
                updateMovie(movies, updatedMovie, "like");
                updateDatabaseUsers();

            } else {
                ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
            }
        } else if (feature.equals("like") && currentPage.equals("see details")) {
            int doNext = 0;
            if (currentMoviesForUser.size() == 1) {
                ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
                doNext = 1;
            }
            if (currentMovieOnPage == null) {
                ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
            } else if (doNext == 0) {
                String movieName = currentMovieOnPage.get(0).getName();
                int valid = 0;
                for (int i = 0; i < currentUser.getWatchedMovies().size(); i++) {
                    if (currentUser.getWatchedMovies().get(i).getName().equals(movieName)) {
                        currentUser = new UserController().addLikedMovie(currentUser, i);
                        valid = 1;
                        updateMovie(movies, currentMovieOnPage.get(0), "like");
                        updateDatabaseUsers();
                        break;
                    }
                }
                OnPageLikeAction likeAction = new OnPageLikeAction();
                likeAction.printLikedMovies(currentUser,
                        valid, output, outputNode, objectMapper);
            }
        }

        if (feature.equals("rate") && currentPage.equals("see details")
                && action.getMovie() != null) {
            currentUser = new OnPageRateAction().rateMovieFromList(currentUser, action.getMovie(),
                    output, outputNode, objectMapper);
            updateDatabaseUsers();
        } else if (feature.equals("rate") && currentPage.equals("see details")) {
            if (currentMovieOnPage == null) {
                //ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
                Output printOutput = new Output("Error", new ArrayList<>(), null);
                outputNode = objectMapper.valueToTree(printOutput);
                output.add(outputNode);
            } else {
                double rateNum = Double.parseDouble(action.getRate());
                currentUser = new OnPageRateAction().rateMovie(currentUser, movies,
                        currentMovieOnPage, output, outputNode, objectMapper, rateNum);
                updateDatabaseUsers();
            }
        }

        if (feature.equals("purchase") && !currentPage.equals("see details")) {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
            currentPage = "see details";
        }
        if (feature.equals("watch") && !currentPage.equals("see details")) {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
            currentPage = "see details";
        }
        if (feature.equals("rate") && !currentPage.equals("see details")) {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
            currentPage = "see details";
        }
        if (feature.equals("like") && !currentPage.equals("see details")) {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
            currentPage = "see details";
        }
    }

    /**
     * Method that updates the information of the current user after each action
     */

    public void updateCurrentUser() {

        for (UserInput databaseUser : databaseUsers) {
            if (databaseUser.getCredentialsInput().getName()
                    .equals(currentUser.getCredentials().getName())) {
                currentUser.setTokensCount(databaseUser.getTokensCount());
                currentUser.setNumFreePremiumMovies(databaseUser.getNumFreePremiumMovies());
                currentUser.setPurchasedMovies(databaseUser.getPurchasedMovies());
                currentUser.setWatchedMovies(databaseUser.getWatchedMovies());
                currentUser.setLikedMovies(databaseUser.getLikedMovies());
                currentUser.setRatedMovies(databaseUser.getRatedMovies());
            }
        }
        if (currentMoviesForUser != null) {
            for (Movies value : currentMoviesForUser) {
                String movieName = value.getName();
                for (int j = 0; j < currentUser.getPurchasedMovies().size(); j++) {
                    if (currentUser.getPurchasedMovies().get(j).getName().equals(movieName)) {
                        currentUser.getPurchasedMovies().get(j).
                                setNumRatings(value.getNumRatings());
                        currentUser.getPurchasedMovies().get(j).setRating(value.getRating());
                    }
                }
                for (int j = 0; j < currentUser.getWatchedMovies().size(); j++) {
                    if (currentUser.getWatchedMovies().get(j).getName().equals(movieName)) {
                        currentUser.getWatchedMovies().get(j).
                                setNumRatings(value.getNumRatings());
                        currentUser.getWatchedMovies().get(j).setRating(value.getRating());
                    }
                }
                for (int j = 0; j < currentUser.getLikedMovies().size(); j++) {
                    if (currentUser.getLikedMovies().get(j).getName().equals(movieName)) {
                        currentUser.getLikedMovies().get(j).
                                setNumRatings(value.getNumRatings());
                        currentUser.getLikedMovies().get(j).setRating(value.getRating());
                    }
                }
                for (int j = 0; j < currentUser.getRatedMovies().size(); j++) {
                    if (currentUser.getRatedMovies().get(j).getName().equals(movieName)) {
                        currentUser.getRatedMovies().get(j).
                                setNumRatings(value.getNumRatings());
                        currentUser.getRatedMovies().get(j).setRating(value.getRating());
                    }
                }
            }
        }
    }

    /**
     * Method that updates the information of the user from the user database
     */

    public void updateDatabaseUsers() {
        for (UserInput databaseUser : databaseUsers) {
            assert currentUser != null;
            if (databaseUser.getCredentialsInput().getName().
                    equals(currentUser.getCredentials().getName())) {
                databaseUser.setTokensCount(currentUser.getTokensCount());
                databaseUser.setNumFreePremiumMovies(currentUser.getNumFreePremiumMovies());
                databaseUser.setPurchasedMovies(currentUser.getPurchasedMovies());
                databaseUser.setWatchedMovies(currentUser.getWatchedMovies());
                databaseUser.setLikedMovies(currentUser.getLikedMovies());
                databaseUser.setRatedMovies(currentUser.getRatedMovies());
                databaseUser.setNotifications(currentUser.getNotifications());
                databaseUser.setIsSubscribed(currentUser.getIsSubscribed());
            }
        }
    }

    /**
     * Method for the "change page" action
     * @param page the given page
     * @param output  an arrayNode containing the output
     * @param outputNode a JsonNode for outputting a node in an array
     * @param objectMapper an objectMapper that helps for the output format
     * @param action the given action
     */

    public void changePage(final String page, final ArrayNode output, final JsonNode outputNode,
                           final ObjectMapper objectMapper, final ActionsInput action) {
        if (currentPage.equals(defaultPage) && page.equals("login")) {
            currentPage = "login";
            previousPage = defaultPage;
        }

        if (currentPage.equals(defaultPage) && page.equals("register")) {
            currentPage = page;
            previousPage = defaultPage;
        }

        if (page.equals("login") && currentPage.equals(authenticatedPage)) {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
            currentPage = defaultPage;
        }

        if (page.equals("logout") && currentPage.equals(authenticatedPage)) {
            currentPage = defaultPage;
        } else if (page.equals("logout") && currentPage.equals(defaultPage)) {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
        }

        if (page.equals("movies") && currentPage.equals(authenticatedPage)) {
            ChangePageActionMovie changePageActionMovie = new ChangePageActionMovie();
            ArrayList<Movies> currentMoviesList =
                    changePageActionMovie.printCurrentMoviesList(currentUser, movies);
            currentMoviesForUser = currentMoviesList;

            ParseOutput.getInstance().printNormalOutput(output, outputNode, objectMapper,
                    currentMoviesList, currentUser);
            currentPage = "movies";
            this.pageStack.push(currentPage);
        }

        if (page.equals("see details")) {
            String movie = action.getMovie();
            Movies getMovie = null;

            ChangePageActionDetails actionDetails = new ChangePageActionDetails();
            getMovie = actionDetails.checkValidMovie(movie, currentMoviesForUser);
            if (getMovie == null) {
                ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
                currentMovieOnPage = null;
                currentPage = "movies";
                this.pageStack.push(currentPage);
                ChangePageActionMovie changePageActionMovie = new ChangePageActionMovie();
                currentMoviesForUser = changePageActionMovie.printCurrentMoviesList(currentUser,
                        movies);
            } else {
                currentMovieOnPage = actionDetails.printMovieDetails(getMovie,
                        output, outputNode, objectMapper, currentUser);
                currentPage = "see details";
                this.pageStack.push(currentPage);
            }
        }

        if (page.equals("upgrades") && currentPage.equals(authenticatedPage)) {
            currentPage = "upgrades";
            this.pageStack.push(currentPage);
            previousPage = "authenticated homepage";
        }

        if (page.equals("movies") && currentPage.equals("upgrades")) {
            currentPage = "movies";
            this.pageStack.push(currentPage);
            ParseOutput.getInstance().printNormalOutput(output, outputNode, objectMapper,
                    currentMoviesForUser, currentUser);
        }

        if (page.equals("logout")) {
            currentPage = defaultPage;
            this.pageStack.push(currentPage);
        }

        if (page.equals("movies") && currentPage.equals("see details")) {
            currentPage = "movies";
            this.pageStack.push(currentPage);
            ChangePageActionMovie changePageActionMovie = new ChangePageActionMovie();
            currentMoviesForUser = changePageActionMovie.printCurrentMoviesList(currentUser,
                    movies);
            ParseOutput.getInstance().printNormalOutput(output, outputNode, objectMapper,
                    currentMoviesForUser, currentUser);
        }

        if (currentPage.equals("movies") && page.equals("login")) {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
            currentPage = defaultPage;
            this.pageStack.push(currentPage);
        }

        if (currentPage.equals(defaultPage) && page.equals("movies")) {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
            currentPage = defaultPage;
            this.pageStack.push(currentPage);
        }
    }

    /**
     * Method that updates the number of likes for a given movie
     * @param movies the list of all the movies available in the database
     * @param updatedMovie the movie that has been liked
     * @param command a string containing the command to know which field needs to be updated
     */

    public void updateMovie(final ArrayList<MoviesInput> movies, final Movies updatedMovie,
                            final String command) {
        if (command.equals("like")) {
            String movieName = updatedMovie.getName();
            for (MoviesInput movie : movies) {
                if (movie.getName().equals(movieName)) {
                    movie.setNumLikes(movie.getNumLikes() + 1);
                }
            }
        }
    }
}
