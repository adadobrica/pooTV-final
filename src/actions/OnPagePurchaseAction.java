package actions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import movies.Movies;
import output.ParseOutput;
import user.User;
import user.UserController;

import java.util.ArrayList;

public final class OnPagePurchaseAction extends PageAction {
    private String feature;

    public OnPagePurchaseAction(final String type, final String page,
                                final String feature) {
        super(type, page);
        this.feature = feature;
    }

    public OnPagePurchaseAction() {
        super(null, null);
    }

    /**
     * Method that purchases a movie from a given list
     * @param currentUser information about the current user
     * @param currentMoviesForUser list of available movies for the user
     * @param movieName String containing the name of the movie
     * @param output the output array
     * @param outputNode the output node
     * @param objectMapper object that maps the output
     * @return the current user
     */
    public User purchaseMovieFromList(final User currentUser,
                                      final ArrayList<Movies> currentMoviesForUser,
                                      final String movieName, final ArrayNode output,
                                      final JsonNode outputNode,
                                      final ObjectMapper objectMapper) {
        Movies purchasedMovie = new Movies();
        for (Movies movie : currentMoviesForUser) {
            if (movie.getName().equals(movieName)) {
                purchasedMovie = movie;
            }
        }
        if (currentUser.getCredentials().getAccountType().equals("premium")) {
            new UserController().purchaseMovie(currentUser, purchasedMovie);

            ParseOutput.getInstance().printNormalOutput(output, outputNode, objectMapper,
                    currentUser.getPurchasedMovies(), currentUser);
        }
        return currentUser;
    }

    /**
     * Method that purchases the current movie on page
     * @param currentUser information about the current user
     * @param currentMovieOnPage the current movie available on the page
     * @param output the output array
     * @param outputNode the output node
     * @param objectMapper object that maps the output
     * @return the current user
     */

    public User purchaseMovie(final User currentUser,
                              final ArrayList<Movies> currentMovieOnPage,
                              final ArrayNode output, final JsonNode outputNode,
                              final ObjectMapper objectMapper) {
        int alreadyPurchased = 0;
        for (int i = 0; i < currentUser.getPurchasedMovies().size(); i++) {
            if (currentMovieOnPage.get(0).getName().
                    equals(currentUser.getPurchasedMovies().get(i).getName())) {
                alreadyPurchased = 1;
                break;
            }
        }
        if (alreadyPurchased == 1) {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
            return currentUser;
        }
        if (currentUser.getCredentials().getAccountType().equals("premium")) {
            int numFreeMovies = currentUser.getNumFreePremiumMovies();
            if (numFreeMovies >= 1) {
                new UserController().decreaseNumFreePremiumMovies(currentUser);
            } else {
                new UserController().setUserTokensCount(currentUser);
            }
        } else {
            new UserController().setUserTokensCount(currentUser);
        }

        new UserController().addPurchasedMovieStandard(currentUser, currentMovieOnPage);
        ParseOutput.getInstance().printNormalOutput(output, outputNode, objectMapper,
                currentMovieOnPage, currentUser);

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
        return "OnPagePurchaseAction{"
                + "feature='" + feature + '\''
                + '}';
    }
}
