package actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class Filter {
    @JsonProperty("sort")
    private Sort sort;
    @JsonProperty("contains")
    private Contains contains;

    public Filter(final Sort sort, final Contains contains) {
        this.sort = sort;
        this.contains = contains;
    }

    public Filter() {

    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(final Sort sort) {
        this.sort = sort;
    }

    public Contains getContains() {
        return contains;
    }

    public void setContains(final Contains contains) {
        this.contains = contains;
    }

    @Override
    public String toString() {
        return "Filter{"
                + "sort=" + sort
                + ", contains=" + contains
                + '}';
    }
}
