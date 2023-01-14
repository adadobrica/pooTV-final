package input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import movies.Movies;
import recommendations.Notifications;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class UserInput {
    @JsonProperty("credentials")
    private CredentialsInput credentialsInput;
    @JsonIgnore
    private int tokensCount = 0;
    @JsonIgnore
    private static final int CONSTANT = 15;
    @JsonIgnore
    private int numFreePremiumMovies = CONSTANT;
    @JsonIgnore
    private ArrayList<Movies> purchasedMovies = new ArrayList<>();
    @JsonIgnore
    private ArrayList<Movies> watchedMovies = new ArrayList<>();
    @JsonIgnore
    private ArrayList<Movies> likedMovies = new ArrayList<>();
    @JsonIgnore
    private ArrayList<Movies> ratedMovies = new ArrayList<>();
    @JsonIgnore
    private ArrayList<Notifications> notifications = new ArrayList<>();
    @JsonIgnore
    private int isSubscribed = 0;

    public UserInput() {

    }

    public int getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(final int isSubscribed) {
        this.isSubscribed = isSubscribed;
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

    public UserInput(final CredentialsInput credentialsInput) {
        this.credentialsInput = credentialsInput;
    }

    public CredentialsInput getCredentialsInput() {
        return credentialsInput;
    }

    public void setCredentialsInput(final CredentialsInput credentialsInput) {
        this.credentialsInput = credentialsInput;
    }

    public ArrayList<Notifications> getNotifications() {
        return notifications;
    }

    public void setNotifications(final ArrayList<Notifications> notifications) {
        this.notifications = notifications;
    }

}
