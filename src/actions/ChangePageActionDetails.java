package actions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import movies.Movies;
import output.ParseOutput;
import user.User;

import java.util.ArrayList;

public final class ChangePageActionDetails extends PageAction {
    ChangePageActionDetails(final String type, final String page) {
        super(type, page);
    }
    public ChangePageActionDetails() {
        super(null, null);
    }

    /**
     * Method that checks whether a movie exists in the database or not
     * @param movie the string containing the name of the movie
     * @param currentMoviesForUser list containing the user's available movies
     * @return the searched movie, or null if it doesn't exist
     */
    public Movies checkValidMovie(final String movie,
                                  final ArrayList<Movies> currentMoviesForUser) {
        Movies getMovie = null;
        if (currentMoviesForUser != null) {
            for (Movies value : currentMoviesForUser) {
                if (value.getName().equals(movie)) {
                    getMovie = value;
                    break;
                }
            }
        }
        return getMovie;
    }

    /**
     * Method that outputs details about a movie
     * @param getMovie String of the desired movie
     * @param output the output array
     * @param outputNode the output node
     * @param objectMapper object that maps the output
     * @param currentUser information about the current user
     * @return details about the movie
     */

    public ArrayList<Movies> printMovieDetails(final Movies getMovie, final ArrayNode output,
                                               final JsonNode outputNode,
                                               final ObjectMapper objectMapper,
                                               final User currentUser) {
        ArrayList<Movies> details = new ArrayList<>();
        details.add(getMovie);

        ParseOutput.getInstance().printNormalOutput(output, outputNode, objectMapper,
                details, currentUser);
        return details;
    }

}
