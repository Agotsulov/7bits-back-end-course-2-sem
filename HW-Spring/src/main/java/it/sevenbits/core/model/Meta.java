package it.sevenbits.core.model;

public class Meta {

    private int total;
    private int page;
    private int size;

    private String next;
    private String prev;
    private String first;
    private String last;

    public Meta(int total, int page, int size, String next, String prev, String first, String last) {
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
