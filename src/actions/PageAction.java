package actions;

public abstract class PageAction {
    private String type;
    private String page;

    PageAction(final String type, final String page) {
        this.type = type;
        this.page = page;
    }

    /**
     * Getter for the page's type
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for page's type
     * @param type String containing the type of page
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Getter for the page string
     * @return a page
     */
    public String getPage() {
        return page;
    }

    /**
     * Setter for the page
     * @param page the new page
     */

    public void setPage(final String page) {
        this.page = page;
    }

}
