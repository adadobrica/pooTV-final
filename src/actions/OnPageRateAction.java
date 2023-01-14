package actions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.MoviesInput;
import movies.Movies;
import output.ParseOutput;
import user.User;
import user.UserController;

import java.util.ArrayList;

public final class OnPageRateAction extends PageAction {
    private String feature;
    private int rate;

    public OnPageRateAction(final String type,
                            final String page,
                            final String feature,
                            final int rate) {
        super(type, page);
        this.feature = feature;
        this.rate = rate;
    }

    public OnPageRateAction() {
        super(null, null);
    }

    /**
     * Method that rates a movie from the list of movies
     * @param currentUser information about the current user
     * @param movieName String containing the name of the movie
     * @param output the output array
     * @param outputNode the output node
     * @param objectMapper object that maps the output
     * @return the current user
     */
    public User rateMovieFromList(final User currentUser,
                                  final String movieName,
                                  final ArrayNode output,
                                  final JsonNode outputNode,
                                  final ObjectMapper objectMapper) {
        int valid = 0;
        for (int i = 0; i < currentUser.getWatchedMovies().size(); i++) {
            if (currentUser.getWatchedMovies().get(i).getName().equals(movieName)) {
                valid = 1;
                break;
            }
        }
        if (valid == 0) {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
        }

        return currentUser;
    }

    /**
     * Method that rates the current movie on page
     * @param currentUser information about the current user
     * @param movies list of all the available movies
     * @param currentMovieOnPage the current movie on page
     * @param output the output array
     * @param outputNode the output node
     * @param objectMapper object that maps the output
     * @param rateNum the rating given to the movie
     * @return the current user
     */

    public User rateMovie(final User currentUser,
                          final ArrayList<MoviesInput> movies,
                          final ArrayList<Movies> currentMovieOnPage,
                          final ArrayNode output,
                          final JsonNode outputNode,
                          final ObjectMapper objectMapper,
                          final double rateNum) {
        final int rateBoundary = 6;
        String movieName = currentMovieOnPage.get(0).getName();
        if (rateNum < rateBoundary) {
            new UserController().addRatedMovie(currentUser, currentMovieOnPage, rateNum);
            ParseOutput.getInstance().printNormalOutput(output, outputNode, objectMapper,
                    currentMovieOnPage, currentUser);

            for (MoviesInput movie : movies) {
                if (movie.getName().equals(movieName)) {
                    movie.setRating(currentMovieOnPage.get(0).getRating());
                    movie.setNumRatings(currentMovieOnPage.get(0).getNumRatings());
                    break;
                }
            }
        } else {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
        }

        return currentUser;
    }
    public String getFeature() {
        return feature;
    }

    public void setFeature(final String feature) {
        this.feature = feature;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(final int rate) {
        this.rate = rate;
    }

}
