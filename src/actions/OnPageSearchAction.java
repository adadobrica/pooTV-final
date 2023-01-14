package actions;

import movies.Movies;

import java.util.ArrayList;

public final class OnPageSearchAction extends PageAction {
    private String feature;
    private String startsWith;

    public OnPageSearchAction(final String type,
                              final String page,
                              final String feature,
                              final String startsWith) {
        super(type, page);
        this.feature = feature;
        this.startsWith = startsWith;
    }

    public OnPageSearchAction() {
        super(null, null);
    }

    /**
     * Method that searches a specific film that starts with a given string
     * @param currentMovies list of all the movies available to the user
     * @param startsWithInput String to search the movie
     * @return an array list of the searched movies
     */

    public ArrayList<Movies> search(final ArrayList<Movies> currentMovies,
                                    final String startsWithInput) {
        ArrayList<Movies> searchedMovies = new ArrayList<>();
        for (Movies currentMovie : currentMovies) {
            if (currentMovie.getName().startsWith(startsWithInput)) {
                searchedMovies.add(currentMovie);
            }
        }
        return searchedMovies;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(final String feature) {
        this.feature = feature;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(final String startsWith) {
        this.startsWith = startsWith;
    }

}
