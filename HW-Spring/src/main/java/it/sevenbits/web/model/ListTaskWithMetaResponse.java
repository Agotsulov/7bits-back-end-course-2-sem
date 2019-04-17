package it.sevenbits.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sevenbits.core.model.Meta;
import it.sevenbits.core.model.Task;

import java.util.List;

/**
 *
 */
public class ListTaskWithMetaResponse {

    @JsonProperty("_meta")
    private Meta meta;
    private List<Task> tasks;

    /**
     * @param meta meta
     * @param tasks list tasks
     */
    public ListTaskWithMetaResponse(final Meta meta, final List<Task> tasks) {
        this.meta = meta;
        this.tasks = tasks;
    }

    public Meta getMeta() {
        return meta;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
