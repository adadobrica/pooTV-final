package user;

import input.CredentialsInput;
import movies.Movies;

import java.util.ArrayList;

public final class UserController {
    private User user;
    private UserView userView;
    public UserController(final User user) {
        this.user = user;
    }

    public UserController(final User user, final UserView userView) {
        this.user = user;
        this.userView = userView;
    }

    public UserController() {

    }

    /**
     * Creates a new user
     * @param credentialsInput user's newest credentials
     * @return the new user
     */

    public User returnNewUser(final CredentialsInput credentialsInput) {
        this.user = new User(credentialsInput);
        return user;
    }

    /**
     * Method that marks the current user as subscribed and adds their subscribed genre
     * @param genre the subscribed genre for the user
     * @param currentUser information about the current user
     * @return the current user
     */

    public User subscribeAndAddGenre(final String genre, final User currentUser) {
        currentUser.setIsSubscribed(1);
        currentUser.getSubscribedGenre().add(genre);
        return currentUser;
    }

    /**
     * Adds a new movie to the user's list of watched movies
     * @param currentUser information about the current user
     * @param watchedMovie information about the watched movie
     * @return the user
     */

    public User addWatchedMovie(final User currentUser, final Movies watchedMovie) {
        currentUser.getWatchedMovies().add(watchedMovie);
        return currentUser;
    }

    /**
     * Adds a new rated movie to the user's list of rated movies
     * @param currentUser information about the current user
     * @param currentMovieOnPage a list containing the current movie available on page
     * @param rateNum the rating given to the movie
     * @return the current user
     */

    public User addRatedMovie(final User currentUser,
                              final ArrayList<Movies> currentMovieOnPage,
                              final double rateNum) {
        int alreadyRated = 0;
        String movieName = currentMovieOnPage.get(0).getName();
        for (int i = 0; i < currentUser.getRatedMovies().size(); i++) {
            if (currentUser.getRatedMovies().get(i).getName().equals(movieName)) {
                alreadyRated = 1;
                break;
            }
        }
        if (alreadyRated == 0) {
            currentMovieOnPage.get(0).setRating(currentMovieOnPage.get(0).getRating() + rateNum);
            currentMovieOnPage.get(0).setNumRatings(currentMovieOnPage.get(0).getNumRatings() + 1);
            currentMovieOnPage.get(0).setRating(currentMovieOnPage.get(0).getRating()
                    / currentMovieOnPage.get(0).getNumRatings());
            currentUser.getRatedMovies().add(currentMovieOnPage.get(0));
        }
        return currentUser;
    }

    /**
     * Adds a liked movie to the user's list of liked movies
     * @param currentUser information about the current user
     * @param i a given index
     * @return the current user
     */

    public User addLikedMovie(final User currentUser, final int i) {
        currentUser.getWatchedMovies().get(i).setNumLikes(currentUser.
                getWatchedMovies().get(i).getNumLikes() + 1);
        currentUser.getLikedMovies().add(currentUser.getWatchedMovies().get(i));
        return currentUser;
    }

    /**
     * Method that decreases a user's number of free premium movies available
     * @param currentUser information about the current user
     * @return the current user
     */

    public User decreaseNumFreePremiumMovies(final User currentUser) {
        currentUser.setNumFreePremiumMovies(currentUser.getNumFreePremiumMovies() - 1);
        return currentUser;
    }

    /**
     * Method that allows the premium user to purchase a movie
     * @param currentUser information about the user
     * @param purchasedMovie the movie about to be purchased
     * @return the current user
     */

    public User purchaseMovie(final User currentUser, final Movies purchasedMovie) {
        currentUser.setNumFreePremiumMovies(currentUser.getNumFreePremiumMovies() - 1);
        currentUser.getPurchasedMovies().add(purchasedMovie);
        return currentUser;
    }

    /**
     * Method that allows the standard user to purchase a movie
     * @param currentUser information about the current user
     * @param currentMovieOnPage the current movie available on page
     * @return the current user
     */

    public User addPurchasedMovieStandard(final User currentUser,
                                          final ArrayList<Movies> currentMovieOnPage) {
        currentUser.getPurchasedMovies().add(currentMovieOnPage.get(0));
        return currentUser;
    }

    /**
     * Method that allows the user to upgrade to premium
     * @param currentUser information about the current user
     * @return the current user
     */

    public User setPremium(final User currentUser) {
        final int numConstant = 10;
        currentUser.setTokensCount(currentUser.getTokensCount() - numConstant);
        currentUser.getCredentials().setAccountType("premium");
        return currentUser;
    }

    /**
     * Method that allows the user to buy new tokens
     * @param currentUser information about the current user
     * @param newBalance the user's newest balance
     * @param count a number containing how many tokens the user bought
     * @return the current user
     */

    public User setNewTokensBought(final User currentUser,
                                   final String newBalance,
                                   final int count) {
        currentUser.getCredentials().setBalance(newBalance);
        currentUser.setTokensCount(count);
        return currentUser;
    }

    /**
     * Method that decreases a user's number of tokens after buying a movie
     * @param currentUser information about the current user
     * @return the current user
     */

    public User setUserTokensCount(final User currentUser) {
        currentUser.setTokensCount(currentUser.getTokensCount() - 2);
        return currentUser;
    }

    /**
     * Method that sets a user's credentials
     * @param credentialsInput the new credentials
     */

    public void setUserCredentials(final CredentialsInput credentialsInput) {
        user.setCredentials(credentialsInput);
    }

    /**
     * Method that resets the user's number of tokens
     * @param tokens the newest number of tokens
     */

    public void setTokensCount(final int tokens) {
        user.setTokensCount(tokens);
    }

    /**
     * Method that updates the user view and prints a string
     */

    public void updateView() {
        userView.printUserString(user);
    }

}
