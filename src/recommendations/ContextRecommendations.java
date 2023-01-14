package recommendations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.MoviesInput;
import movies.Movies;
import user.User;

import java.util.ArrayList;

public class ContextRecommendations {
    private NotifierStrategy strategy;

    /**
     * Getter for the strategy
     * @return the strategy
     */

    public NotifierStrategy getStrategy() {
        return strategy;
    }

    /**
     * Setter for the strategy
     * @param strategy the new strategy
     */
    public void setStrategy(final NotifierStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Method that executes a specific strategy
     * @param notifications the notifications for the subscribed user
     * @param currentUser information regarding the current user
     * @param output an arrayNode containing the output
     * @param outputNode a JsonNode for outputting a node in an array
     * @param objectMapper an objectMapper that helps for the output format
     * @param movies the database of movies
     * @param currentMoviesForUser a list of all the available movies for the current user
     * @return the current user
     */
    public User executeStrategy(final Notifications notifications, final User currentUser,
                                final ArrayNode output, final JsonNode outputNode,
                                final ObjectMapper objectMapper,
                                final ArrayList<MoviesInput> movies,
                                final ArrayList<Movies> currentMoviesForUser) {
        return strategy.send(notifications, currentUser, output, outputNode, objectMapper, movies,
                currentMoviesForUser);
    }
}
