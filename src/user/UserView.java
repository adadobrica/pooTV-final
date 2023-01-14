package user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.CredentialsInput;
import output.Output;

import java.util.ArrayList;

public final class UserView {

    public UserView() { };

    /**
     * Method that outputs details about a user
     * @param credentialsInput user's credentials
     * @param output the output array
     * @param outputNode the output node
     * @param objectMapper object that helps to map the node into the array
     * @return the current user
     */

    public User printUserDetails(final CredentialsInput credentialsInput,
                                 final ArrayNode output, JsonNode outputNode,
                                 final ObjectMapper objectMapper) {
        User currentUser = new User(credentialsInput);
        Output printOutput = new Output(null, new ArrayList<>(), currentUser);
        outputNode = objectMapper.valueToTree(printOutput);
        output.add(outputNode);
        return currentUser;
    }

    /**
     * Method that prints information about the user in a String format
     * @param user information about the user
     */

    public void printUserString(final User user) {
        System.out.println(user.toString());
    }
}
