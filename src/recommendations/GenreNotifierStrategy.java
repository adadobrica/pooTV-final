package recommendations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.MoviesInput;
import movies.Movies;
import output.ParseOutput;
import user.User;

import java.util.ArrayList;

public class GenreNotifierStrategy implements NotifierStrategy {
    /**
     * Method that sends notifications to the user based on its preferred genres
     * @param notifications the notifications
     * @param currentUser the current user
     * @param output an arrayNode containing the output
     * @param outputNode a JsonNode for outputting a node in an array
     * @param objectMapper an objectMapper that helps for the output format
     * @param movies database of the movies
     * @param currentMoviesForUser the movies available to the user
     * @return the current user
     */
    @Override
    public User send(final Notifications notifications, final User currentUser,
                     final ArrayNode output, final JsonNode outputNode,
                     final ObjectMapper objectMapper, final ArrayList<MoviesInput> movies,
                     final ArrayList<Movies> currentMoviesForUser) {
        Notifications recs = new Notifications();
        recs.setMessage("Recommendation");
        String subscribedGenre = currentUser.getSubscribedGenre().get(0);
        ArrayList<Movies> recommendedMovies = new ArrayList<>();
        for (Movies value : currentMoviesForUser) {
            for (int j = 0; j < value.getGenres().size(); j++) {
                if (value.getGenres().get(j).equals(subscribedGenre)) {
                    recommendedMovies.add(value);
                    break;
                }
            }
        }
        recs.setMovieName(recommendedMovies.get(0).getName());
        currentUser.getNotifications().add(recs);
        ParseOutput.getInstance().printNormalOutput(output, outputNode, objectMapper,
                null, currentUser);
        return currentUser;
    }
}
