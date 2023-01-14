package actions;

import input.CredentialsInput;
import input.UserInput;
import user.Credentials;
import user.User;
import user.UserController;

import java.util.ArrayList;

public final class OnPageDefaultAction extends PageAction {
    private String feature;
    private Credentials credentials;

    public OnPageDefaultAction(final String type,
                               final String page,
                               final String feature,
                               final Credentials credentials) {
        super(type, page);
        this.feature = feature;
        this.credentials = credentials;
    }

    public OnPageDefaultAction() {
        super(null, null);
    }

    /**
     * Method that checks whether a user's login credentials are valid
     * @param credentialsInput the credentials
     * @param databaseUsers a database of all the users
     * @return whether the credentials are valid or not
     */
    public int checkLoginCredentials(final Credentials credentialsInput,
                                     final ArrayList<UserInput> databaseUsers) {
        int valid = -1;
        String credentialName = credentialsInput.getName();
        String credentialPassword = credentialsInput.getPassword();

        for (UserInput databaseUser : databaseUsers) {
            String name = databaseUser.getCredentialsInput().getName();
            String password = databaseUser.getCredentialsInput().getPassword();
            if (name.equals(credentialName) && password.equals(credentialPassword)) {
                valid = 1;
                break;
            }
        }
        return valid;
    }

    /**
     * Method that registers a new user and adds them into the database of users
     * @param credentialsInput the user's credentials
     * @param databaseUsers the database of users
     */

    public void register(final CredentialsInput credentialsInput,
                         final ArrayList<UserInput> databaseUsers) {
        UserInput userInput = new UserInput(credentialsInput);
        databaseUsers.add(userInput);
    }

    /**
     * Method that performs the login action
     * @param databaseUsers the database containing all the users
     * @param credentialsInput the credentials of the user
     * @param currentUser information about the current user
     * @return the current user
     */

    public User loginSuccess(final ArrayList<UserInput> databaseUsers,
                             final CredentialsInput credentialsInput,
                             final User currentUser) {
        for (UserInput databaseUser : databaseUsers) {
            String name = databaseUser.getCredentialsInput().getName();
            String password = databaseUser.getCredentialsInput().getPassword();
            if (credentialsInput.getName().equals(name)
                    && credentialsInput.getPassword().equals(password)) {
                return new UserController(currentUser).
                        returnNewUser(databaseUser.getCredentialsInput());
            }

        }
        return currentUser;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(final String feature) {
        this.feature = feature;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

}
