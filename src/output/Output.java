package output;

import movies.Movies;
import user.User;

import java.util.ArrayList;

public final class Output {
    private String error;
    private ArrayList<Movies> currentMoviesList = new ArrayList<>();
    private User currentUser;
    public Output(final String error, final ArrayList<Movies> movies,
                  final User currentUser) {
        this.error = error;
        this.currentMoviesList = movies;
        this.currentUser = currentUser;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public ArrayList<Movies> getCurrentMoviesList() {
        return currentMoviesList;
    }

    public void setCurrentMoviesList(final ArrayList<Movies> currentMoviesList) {
        this.currentMoviesList = currentMoviesList;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public String toString() {
        return "Output{"
                + "error='" + error + '\''
                + ", currentMoviesList=" + currentMoviesList
                + ", currentUser=" + currentUser
                + '}';
    }
}
