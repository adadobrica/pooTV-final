package actions;

import input.MoviesInput;
import movies.Movies;

import java.util.ArrayList;
import java.util.Comparator;

public final class OnPageFilterAction extends PageAction {
    private String feature;
    private Filter filters;

    public OnPageFilterAction(final String type,
                              final String page,
                              final String feature,
                              final Filter filters) {
        super(type, page);
        this.feature = feature;
        this.filters = filters;
    }

    public OnPageFilterAction() {
        super(null, null);
    }

    /**
     * Method that filters an array of movies based on certain criteria
     * @param currentMovies list of current movies
     * @param filtersInput the given filter
     * @param movies list of all the available movies
     * @return the filtered array of movies
     */
    public ArrayList<Movies> filter(final ArrayList<Movies> currentMovies,
                                    final Filter filtersInput,
                                    final ArrayList<MoviesInput> movies) {
        ArrayList<Movies> tempMovies = new ArrayList<>();
        for (MoviesInput movie : movies) {
            Movies newMovies = new Movies(movie);
            tempMovies.add(newMovies);
        }

        ArrayList<Movies> filteredMovies = new ArrayList<>();
        int isSorted = 0;

        if (filtersInput.getSort() != null) {
            Sort sort = filtersInput.getSort();
            String rating = sort.getRating();

            if (filtersInput.getSort().getRating() != null
                    && filtersInput.getSort().getDuration() != null) {
                if (filtersInput.getSort().getDuration().equals("decreasing")
                && filtersInput.getSort().getRating().equals("increasing")) {
                   currentMovies.sort(Comparator.comparingInt(Movies::getDuration).reversed()
                           .thenComparingDouble(Movies::getRating));
                   return currentMovies;
                }

                if (filtersInput.getSort().getDuration().equals("decreasing")
                && filtersInput.getSort().getRating().equals("decreasing")) {
                    currentMovies.sort(Comparator.comparingInt(Movies::getDuration).reversed()
                            .thenComparingDouble(Movies::getRating).reversed());
                    return currentMovies;
                }
            }
            if (filtersInput.getSort().getRating() != null
                    && filtersInput.getSort().getDuration() == null) {
                if (filtersInput.getSort().getRating().equals("increasing")) {
                    currentMovies.sort(Comparator.comparingDouble(Movies::getRating));
                    return currentMovies;
                }
                if (filtersInput.getSort().getRating().equals("decreasing")) {
                    currentMovies.sort(Comparator.comparingDouble(Movies::getRating).reversed());
                    return currentMovies;
                }
            }
            isSorted = 1;
        }

        if (isSorted == 0 && filtersInput.getContains() != null) {

            if (filtersInput.getContains().getActors() != null) {
                ArrayList<String> fields = filtersInput.getContains().getActors();
                String actor = fields.get(0);
                for (Movies currentMovie : currentMovies) {
                    for (int j = 0; j < currentMovie.getActors().size(); j++) {
                        if (currentMovie.getActors().get(j).equals(actor)) {
                            filteredMovies.add(currentMovie);
                        }
                    }
                }
            }
            if (filtersInput.getContains().getGenre() != null
                    && filtersInput.getContains().getActors() == null) {
                ArrayList<String> fields = filtersInput.getContains().getGenre();
                String genre = fields.get(0);
                for (Movies currentMovie : currentMovies) {
                    for (int j = 0; j < currentMovie.getGenres().size(); j++) {
                        if (currentMovie.getGenres().get(j).equals(genre)) {
                            filteredMovies.add(currentMovie);
                        }
                    }
                }
            }
            if (filtersInput.getContains().getActors() != null
                    && filtersInput.getContains().getGenre() != null) {
                ArrayList<String> actorFields = filtersInput.getContains().getActors();
                ArrayList<String> genreFields = filtersInput.getContains().getGenre();
                String actor = actorFields.get(0);
                String genre = genreFields.get(0);
                filteredMovies = new ArrayList<>();
                for (Movies currentMovie : currentMovies) {
                    for (int j = 0; j < currentMovie.getGenres().size(); j++) {
                        if (currentMovie.getGenres().get(j).equals(genre)) {
                            filteredMovies.add(currentMovie);
                        }
                    }
                }
                int isValid = 0;
                for (Movies currentMovie : filteredMovies) {
                    for (int j = 0; j < currentMovie.getActors().size(); j++) {
                        if (currentMovie.getActors().get(j).equals(actor)) {
                            isValid = 1;
                            break;
                        }
                    }
                }
                if (isValid == 0) {
                    return new ArrayList<>();
                }
                return filteredMovies;
            }
            return filteredMovies;
        }
        return currentMovies;
    }


    public String getFeature() {
        return feature;
    }

    public void setFeature(final String feature) {
        this.feature = feature;
    }

    public Filter getFilters() {
        return filters;
    }

    public void setFilters(final Filter filters) {
        this.filters = filters;
    }


    @Override
    public String toString() {
        return "OnPageFilterAction{"
                + "feature='" + feature + '\''
                + ", filters=" + filters
                + '}';
    }
}
