package database;

import input.MoviesInput;

import java.util.ArrayList;

public class DatabaseAdd implements DatabaseAction {
    /**
     * Method that adds a movie to the database
     * @param movies movie about to be added
     * @param movieName the movie's name
     * @param movieDatabase the entire database containing the movies
     * @return the new database
     */
    @Override
    public ArrayList<MoviesInput> execute(final MoviesInput movies, final String movieName,
                                          final ArrayList<MoviesInput> movieDatabase) {
        movieDatabase.add(movies);
        return movieDatabase;
    }
}
