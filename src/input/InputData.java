package input;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class InputData {

    @JsonProperty("users")
    private ArrayList<UserInput> users = new ArrayList<>();

    public ArrayList<UserInput> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<UserInput> users) {
        this.users = users;
    }

    @JsonProperty("movies")
    private ArrayList<MoviesInput> movies = new ArrayList<>();

    public ArrayList<MoviesInput> getMovies() {
        return movies;
    }

    public void setMovies(final ArrayList<MoviesInput> movies) {
        this.movies = movies;
    }

    @JsonProperty("actions")
    private ArrayList<ActionsInput> actions = new ArrayList<>();

    public ArrayList<ActionsInput> getActions() {
        return actions;
    }

    public void setActions(final ArrayList<ActionsInput> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return "InputData{"
                + "users=" + users
                + ", movies=" + movies
                + ", actions=" + actions
                + '}';
    }
}
