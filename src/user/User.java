package user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import input.CredentialsInput;
import input.UserInput;
import movies.Movies;
import recommendations.Notifications;

import java.util.ArrayList;

public final class User {
    private CredentialsInput credentials = new CredentialsInput();
    private int tokensCount = 0;
    private static final int CONSTANT = 15;
    private int numFreePremiumMovies = CONSTANT;
    private ArrayList<Movies> purchasedMovies = new ArrayList<>();
    private ArrayList<Movies> watchedMovies = new ArrayList<>();
    private ArrayList<Movies> likedMovies = new ArrayList<>();
    private ArrayList<Movies> ratedMovies = new ArrayList<>();
    private ArrayList<Notifications> notifications = new ArrayList<>();
    @JsonIgnore
    private int isSubscribed = 0;

    @JsonIgnore
    private ArrayList<String> subscribedGenre = new ArrayList<>();

    public User(final UserInput user) {
        this.credentials = user.getCredentialsInput();
    }

    public User(final CredentialsInput credentialsInput) {
        this.credentials = credentialsInput;
    }
    public User() {

    }

    public CredentialsInput getCredentials() {
        return credentials;
    }

    public void setCredentials(final CredentialsInput credentials) {
        this.credentials = credentials;
    }

    public int getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public ArrayList<Movies> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(final ArrayList<Movies> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<Movies> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(final ArrayList<Movies> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<Movies> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(final ArrayList<Movies> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<Movies> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(final ArrayList<Movies> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    public ArrayList<Notifications> getNotifications() {
        return notifications;
    }

    public void setNotifications(final ArrayList<Notifications> notifications) {
        this.notifications = notifications;
    }

    public int getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(final int isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    public ArrayList<String> getSubscribedGenre() {
        return subscribedGenre;
    }

    public void setSubscribedGenre(final ArrayList<String> subscribedGenre) {
        this.subscribedGenre = subscribedGenre;
    }

    @Override
    public String toString() {
        return "User{"
                + "credentials=" + credentials
                + ", tokensCount=" + tokensCount
                + ", numFreePremiumMovies=" + numFreePremiumMovies
                + ", purchasedMovies=" + purchasedMovies
                + ", watchedMovies=" + watchedMovies
                + ", likedMovies=" + likedMovies
                + ", ratedMovies=" + ratedMovies
                + '}';
    }
}
