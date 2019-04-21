package it.sevenbits.core.model;

/**
 * Meta model
 */
public class Meta {

    private int total;
    private int page;
    private int size;

    private String next;
    private String prev;
    private String first;
    private String last;

    /**
     * @param total count of all tasks
     * @param page current page
     * @param size current tasks on page
     * @param next url for next page
     * @param prev url for prev page
     * @param first url for first page
     * @param last url for last page
     */
    public Meta(final int total,
                final int page,
                final int size,
                final String next,
                final String prev,
                final String first,
                final String last) {
        this.total = total;
        this.page = page;
        this.size = size;
        this.next = next;
        this.prev = prev;
        this.first = first;
        this.last = last;
    }

    public int getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public String getNext() {
        return next;
    }

    public String getPrev() {
        return prev;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }
}
