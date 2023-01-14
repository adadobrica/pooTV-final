package input;

import actions.Filter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public final class ActionsInput {
    @JsonProperty("type")
    private String type;
    @JsonProperty("page")
    private String page;

    @JsonProperty("feature")
    private String feature;
    @JsonProperty("credentials")
    private CredentialsInput credentialsInput;

    @JsonProperty("movie")
    private String movie;

    @JsonProperty("startsWith")
    private String startsWith;

    @JsonProperty("filters")
    private Filter filters;

    @JsonProperty("count")
    private String count;

    @JsonProperty("rate")
    private String rate;

    @JsonProperty("objectType")
    private String objectType;
    @JsonProperty("subscribedGenre")
    private String subscribedGenre;

    @JsonProperty("addedMovie")
    private MoviesInput addedMovie;
    @JsonProperty("deletedMovie")
    private String deletedMovie;

    public CredentialsInput getCredentialsInput() {
        return credentialsInput;
    }

    public void setCredentialsInput(final CredentialsInput credentialsInput) {
        this.credentialsInput = credentialsInput;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(final String feature) {
        this.feature = feature;
    }

    public String getPage() {
        return page;
    }

    public void setPage(final String page) {
        this.page = page;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(final String movie) {
        this.movie = movie;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(final String startsWith) {
        this.startsWith = startsWith;
    }

    public Filter getFilters() {
        return filters;
    }

    public void setFilters(final Filter filters) {
        this.filters = filters;
    }

    public String getCount() {
        return count;
    }

    public void setCount(final String count) {
        this.count = count;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(final String rate) {
        this.rate = rate;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(final String objectType) {
        this.objectType = objectType;
    }

    public String getSubscribedGenre() {
        return subscribedGenre;
    }

    public void setSubscribedGenre(final String subscribedGenre) {
        this.subscribedGenre = subscribedGenre;
    }

    public MoviesInput getAddedMovie() {
        return addedMovie;
    }

    public void setAddedMovie(final MoviesInput addedMovie) {
        this.addedMovie = addedMovie;
    }

    public String getDeletedMovie() {
        return deletedMovie;
    }

    public void setDeletedMovie(final String deletedMovie) {
        this.deletedMovie = deletedMovie;
    }

    @Override
    public String toString() {
        return "ActionsInput{"
                + "type='" + type + '\''
                + ", page='" + page + '\''
                + ", feature='" + feature + '\''
                + ", credentialsInput=" + credentialsInput
                + ", movie='" + movie + '\''
                + ", startsWith='" + startsWith + '\''
                + ", filters=" + filters
                + ", count='" + count + '\''
                + ", rate='" + rate + '\''
                + ", objectType='" + objectType + '\''
                + '}';
    }
}
