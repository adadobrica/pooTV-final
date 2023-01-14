package actions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import movies.Movies;
import output.ParseOutput;
import user.User;

public final class OnPageLikeAction extends PageAction {
    private String feature;

    public OnPageLikeAction(final String type,
                            final String page,
                            final String feature) {
        super(type, page);
        this.feature = feature;
    }

    public OnPageLikeAction() {
        super(null, null);
    }

    /**
     * Method that searches for a movie from the list of all the movies
     * @param currentUser information about the current user
     * @param i the index of the movie
     * @return the movie
     */
    public Movies findMovieFromList(final User currentUser, final  int i) {
        Movies updatedMovie = new Movies();

        currentUser.getWatchedMovies().get(i).setNumLikes(currentUser.
                getWatchedMovies().get(i).getNumLikes() + 1);
        currentUser.getLikedMovies().add(currentUser.getWatchedMovies().get(i));

        updatedMovie = currentUser.getWatchedMovies().get(i);
        return updatedMovie;
    }

    /**
     * Method that outputs the liked movie
     * @param currentUser information about the current user
     * @param valid whether the movie is valid or not
     * @param output the output array
     * @param outputNode the output node
     * @param objectMapper object that maps the output
     */

    public void printLikedMovies(final User currentUser,
                                 final int valid, final ArrayNode output,
                                 final JsonNode outputNode,
                                 final ObjectMapper objectMapper) {
        if (valid == 1) {
            ParseOutput.getInstance().printNormalOutput(output, outputNode, objectMapper,
                    currentUser.getWatchedMovies(), currentUser);
        } else {
            ParseOutput.getInstance().printOutputError(output, outputNode, objectMapper);
        }
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(final String feature) {
        this.feature = feature;
    }

}
