package database;

import input.MoviesInput;

import java.util.ArrayList;

public interface DatabaseAction {
    /**
     * Method that will be used for overriding
     * @param movies movie taken from the input
     * @param movieName the movie name
     * @param movieDatabase the movie database
     * @return the new updated database
     */
    ArrayList<MoviesInput> execute(MoviesInput movies,
                                   String movieName,
                                   ArrayList<MoviesInput> movieDatabase);
}
