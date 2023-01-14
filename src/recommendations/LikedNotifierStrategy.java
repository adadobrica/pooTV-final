package recommendations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.MoviesInput;
import movies.Movies;
import output.ParseOutput;
import user.User;

import java.util.ArrayList;

public class LikedNotifierStrategy implements NotifierStrategy {
    /**
     * Method that sends notifications for the user based on their liked movies
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
                     final ObjectMapper objectMapper,
                     final ArrayList<MoviesInput> movies,
                     final ArrayList<Movies> currentMoviesForUser) {
        String recMovie;
        for (MoviesInput movie : movies) {
            if (movie.getNumLikes() > 0) {
                recMovie = movie.getName();
                Notifications recNotifications = new Notifications();
                recNotifications.setMovieName(recMovie);
                recNotifications.setMessage("Recommendation");
                currentUser.getNotifications().add(recNotifications);
                ParseOutput.getInstance().printNormalOutput(output, outputNode, objectMapper,
                        null, currentUser);
                break;
            }
        }
        return currentUser;
    }
}
