package recommendations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.MoviesInput;
import movies.Movies;
import output.ParseOutput;
import user.User;

import java.util.ArrayList;

public class NormalNotifierStrategy implements NotifierStrategy {
    /**
     * Method that sends normal notifications to the user
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
        Notifications normalNotifications = new Notifications();
        normalNotifications.setMovieName("No recommendation");
        normalNotifications.setMessage("Recommendation");
        currentUser.getNotifications().add(normalNotifications);
        ParseOutput.getInstance().printNormalOutput(output, outputNode, objectMapper,
                null, currentUser);
        return currentUser;
    }
}
