package actions;

import input.MoviesInput;
import movies.Movies;
import user.User;

import java.util.ArrayList;

public final class ChangePageActionMovie extends PageAction {
    private String movieName;

    public ChangePageActionMovie(final String type,
                                 final String page,
                                 final String movie) {
        super(type, page);
        this.movieName = movie;
    }

    public ChangePageActionMovie() {
        super(null, null);
    }

    public String getMovie() {
        return movieName;
    }

    public void setMovie(final String movie) {
        this.movieName = movie;
    }

    /**
     * Method that outputs a list of all the current movies available to the user
     * @param currentUser information about the current user
     * @param movies the database of movies
     * @return the current movies
     */

    public ArrayList<Movies> printCurrentMoviesList(final User currentUser,
                                                    final ArrayList<MoviesInput> movies) {
        ArrayList<Movies> currentMoviesList = new ArrayList<>();
        for (MoviesInput moviesInput : movies) {
            Movies movie = new Movies(moviesInput);
            int valid = 0;
            for (int i = 0; i < movie.getCountriesBanned().size(); i++) {
                if (currentUser.getCredentials().getCountry().
                        equals(movie.getCountriesBanned().get(i))) {
                    valid = -1;
                    break;
                }
            }
            if (valid == 0) {
                currentMoviesList.add(movie);
            }
        }
        return currentMoviesList;
    }

}
