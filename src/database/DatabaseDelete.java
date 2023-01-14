package database;

import input.MoviesInput;

import java.util.ArrayList;

public class DatabaseDelete implements DatabaseAction {
    /**
     * Method that deletes a movie from the database
     * @param movies information about the movie
     * @param movieName the movie's name
     * @param movieDatabase list containing all the current movies
     * @return the new database of movies
     */
    @Override
    public ArrayList<MoviesInput> execute(final MoviesInput movies, final String movieName,
                                          final ArrayList<MoviesInput> movieDatabase) {
        for (int i = 0; i < movieDatabase.size(); i++) {
            if (movieDatabase.get(i).getName().equals(movieName)) {
                movieDatabase.remove(i);
                i--;
            }
        }
        return movieDatabase;
    }
}
