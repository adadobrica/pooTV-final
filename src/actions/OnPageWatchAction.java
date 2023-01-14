package actions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import movies.Movies;
import output.ParseOutput;
import user.User;
import user.UserController;

import java.util.ArrayList;

public final class OnPageWatchAction extends PageAction {
    private String feature;

    public OnPageWatchAction(final String type,
                             final String page,
                             final String feature) {
        super(type, page);
        this.feature = feature;
    }

    public OnPageWatchAction() {
        super(null, null);
    }

    /**
     * Method that adds a movie to the user's list of watched movies
     * @param currentUser information about the current user
     * @param movieName String containing the movie name
     * @param output the output array
     * @param outputNode the output node
     * @param objectMapper object that maps the output
     * @return the current user
     */

    public User watchMovieFromList(final User currentUser, final String movieName,
                                   final ArrayNode output,
                                   final JsonNode outputNode,
                                   final ObjectMapper objectMapper) {
        int valid = 0;
        for (int i = 0; i < currentUser.getPurchasedMovies().size(); i++) {
            if (currentUser.getPurchasedMovies().get(i).getName().equals(movieName)) {
                Movies watchedMovie = currentUser.getPurchasedMovies().get(i);
                new UserController().addWatchedMovie(currentUser, watchedMovie);
                valid = 1;
                break;
            }
        }
        if (valid == 1) {
            ParseOutput.getInstance().printNormalOutput(output, outputNode, objectMapper,
                    currentUser.getWatchedMovies(), currentUser);
        } else {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
        }

        return currentUser;
    }

    /**
     * Method that adds the current movie on page to the user's list of watched movies
     * @param currentUser information about the user
     * @param currentMoviesForUser list of available movies for the user
     * @param currentMovieOnPage the current movie available on page
     * @param output the output array
     * @param outputNode the output node
     * @param objectMapper object that maps the output
     * @return the current user
     */

    public User watchMovie(final User currentUser,
                           final ArrayList<Movies> currentMoviesForUser,
                           final ArrayList<Movies> currentMovieOnPage,
                           final ArrayNode output,
                           final JsonNode outputNode,
                           final ObjectMapper objectMapper) {

        if (currentMoviesForUser.size() == 1) {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
        } else {
            Movies watchedMovie = currentMovieOnPage.get(0);
            String movieName = watchedMovie.getName();
//            int purchased = 0;
//            for (int i = 0; i < currentUser.getPurchasedMovies().size(); i++) {
//                if (currentUser.getPurchasedMovies().get(i).getName().equals(movieName)) {
//                    purchased = 1;
//                    break;
//                }
//            }
//            if (purchased == 0) {
//                ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
//                return currentUser;
//            }
            int alreadyWatched = 0;
            for (int  i = 0; i < currentUser.getWatchedMovies().size(); i++) {
                if (currentUser.getWatchedMovies().get(i).getName().equals(movieName)) {
//                    watchedMovie.setRating(currentUser.getWatchedMovies().get(i).getRating() - 1);
                    alreadyWatched = 1;
                    break;
                }
            }
            if (alreadyWatched == 0) {
                new UserController().addWatchedMovie(currentUser, watchedMovie);
                ParseOutput.getInstance().printNormalOutput(output, outputNode, objectMapper,
                        currentMovieOnPage, currentUser);
            }
        }
        return currentUser;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(final String feature) {
        this.feature = feature;
    }


    @Override
    public String toString() {
        return "OnPageWatchAction{"
                + "feature='" + feature + '\''
                + '}';
    }
}
