package actions;

public final class Sort {
    private String rating;
    private String duration;

    public Sort(final String rating, final String duration) {
        this.rating = rating;
        this.duration = duration;
    }

    public Sort() {

    }

    public String getRating() {
        return rating;
    }

    public void setRating(final String rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(final String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Sort{"
                + "rating='" + rating + '\''
                + ", duration='" + duration + '\''
                + '}';
    }
}
