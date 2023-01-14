package actions;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public final class Contains {
    @JsonProperty("actors")
    private ArrayList<String> actors;
    @JsonProperty("genre")
    private ArrayList<String> genre;

    public Contains(final ArrayList<String> actors,
                    final ArrayList<String> genre) {
        this.actors = actors;
        this.genre = genre;
    }

    public Contains() {

    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(final ArrayList<String> genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Contains{"
                + "actors=" + actors
                + ", genre=" + genre
                + '}';
    }
}
