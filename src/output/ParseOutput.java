package output;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import movies.Movies;
import user.User;

import java.util.ArrayList;

public final class ParseOutput {
    private static ParseOutput instance;
    private ParseOutput() {

    }

    /**
     * Method that initializes an instance of the ParseOutput class
     * @return a single instance of this class
     */
    public static synchronized ParseOutput getInstance() {
        if (instance == null) {
            instance = new ParseOutput();
        }
        return instance;
    }

    /**
     * Method that outputs an error in the desired format
     * @param output the output array
     * @param outputNode the output node
     * @param objectMapper helps to map the value into the output
     */

    public void printOutputError(final ArrayNode output, JsonNode outputNode,
                                 final ObjectMapper objectMapper) {
        Output printOutput = new Output("Error", new ArrayList<>(), null);
        outputNode = objectMapper.valueToTree(printOutput);
        output.add(outputNode);
    }

    /**
     * Method that outputs with given parameters
     * @param output the output array
     * @param outputNode the output node
     * @param objectMapper helps to map the value into the output
     * @param currentMoviesList an arrayList of movies
     * @param currentUser the current user
     */

    public void printNormalOutput(final ArrayNode output, JsonNode outputNode,
                                  final ObjectMapper objectMapper,
                                  final ArrayList<Movies> currentMoviesList,
                                  final User currentUser) {
        Output printOutput = new Output(null, currentMoviesList, currentUser);
        outputNode = objectMapper.valueToTree(printOutput);
        output.add(outputNode);
    }

}
