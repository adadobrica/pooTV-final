package movies;

import input.MoviesInput;

import java.util.ArrayList;

public final class Movies {
    private String name;
    private String year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;
    private int numLikes = 0;
    private double rating = 0.00;
    private int numRatings = 0;

    public Movies(final MoviesInput movies) {
        this.name = movies.getName();
        this.year = movies.getYear();
        this.duration = movies.getDuration();
        this.genres = movies.getGenres();
        this.actors = movies.getActors();
        this.countriesBanned = movies.getCountriesBanned();
        this.rating = movies.getRating();
        this.numRatings = movies.getNumRatings();
        this.numLikes = movies.getNumLikes();
    }

    public Movies() {

    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(final String year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }

    @Override
    public String toString() {
        return "Movies{"
                + "name='" + name + '\''
                + ", year=" + year
                + ", duration=" + duration
                + ", genres=" + genres
                + ", actors=" + actors
                + ", countriesBanned=" + countriesBanned
                + ", numLikes=" + numLikes
                + ", rating=" + rating
                + ", numRatings=" + numRatings
                + '}';
    }
}
