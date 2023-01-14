package recommendations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.MoviesInput;
import movies.Movies;
import user.User;

import java.util.ArrayList;

public interface NotifierStrategy {
    /**
     * Strategy method to send notifications to the user
     * @param notifications the notifications
     * @param currentUser the current user
     * @param output an arrayNode containing the output
     * @param outputNode a JsonNode for outputting a node in an array
     * @param objectMapper an objectMapper that helps for the output format
     * @param movies database of the movies
     * @param currentMoviesForUser the movies available to the user
     * @return the current user
     */
    User send(Notifications notifications, User currentUser,
              ArrayNode output, JsonNode outputNode,
              ObjectMapper objectMapper,
              ArrayList<MoviesInput> movies,
              ArrayList<Movies> currentMoviesForUser);
}
