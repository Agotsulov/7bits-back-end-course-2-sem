package it.sevenbits.web.contorller;

import it.sevenbits.core.model.Meta;
import it.sevenbits.core.repository.tasks.TasksRepository;
import it.sevenbits.web.model.AddTaskRequest;
import it.sevenbits.web.model.ListTaskWithMetaResponse;
import it.sevenbits.web.model.PatchTaskRequest;
import it.sevenbits.core.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/tasks")
public class TasksController {

    @Value("${meta.defaultStatus}")
    private String defaultStatus;
    @Value("${meta.defaultOrder}")
    private String defaultOrder;
    @Value("${meta.defaultPage}")
    private int defaultPage;
    @Value("${meta.defaultSize}")
    private int defaultSize;
    @Value("${meta.minSize}")
    private int defaultMinSize;
    @Value("${meta.maxSize}")
    private int defaultMaxSize;


    private final TasksRepository tasksRepository;

    public TasksController(final TasksRepository tasksRepository){
        this.tasksRepository = tasksRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ListTaskWithMetaResponse> all(
            @RequestParam(value = "status", required = false, defaultValue = "inbox") final String statusQuery,
            @RequestParam(value = "order", required = false, defaultValue = "desc") final String orderQuery,
            @RequestParam(value = "page", required = false, defaultValue = "1") final Integer pageQuery,
            @RequestParam(value = "size", required = false, defaultValue = "25") final Integer sizeQuery
    ) {
        String status = defaultStatus;
        String order = defaultOrder;
        int page = defaultPage;
        int size = defaultSize;
        if ("done".equals(statusQuery)) {
            status = statusQuery;
        }
        if (pageQuery > 0) {
            page = pageQuery;
        }
        if ("asc".equals(orderQuery)) {
            order = orderQuery;
        }
        if (defaultMinSize <= sizeQuery && sizeQuery <= defaultMaxSize) {
            size = sizeQuery;
        }
        int total = tasksRepository.size();
        if (page > total / size) {
            page = total / size + 1;
        } 
        Meta meta = new Meta(total, page, size,
                "/tasks?status=" + status + "&order=" + order + "&&page=" + (page + 1) + "&size=" + size,
                "/tasks?status=" + status + "&order=" + order + "&&page=" + (page - 1) + "&size=" + size,
                "/tasks?status=" + status + "&order=" + order + "&&page=" + 1 + "&size=" + size,
                "/tasks?status=" + status + "&order=" + order + "&&page=" + (total / size + 1) + "&size=" + size
                );

        ListTaskWithMetaResponse listTaskWithMetaResponse = new ListTaskWithMetaResponse(meta,
                tasksRepository.getAll(status, order, page, size));

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(listTaskWithMetaResponse);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Task> create(@RequestBody final AddTaskRequest newTask) {
        if (newTask == null ||
                newTask.getText() == null ||
                "".equals(newTask.getText())) {
            return ResponseEntity.badRequest().build();
        }

        Task task = tasksRepository.create(newTask);

        /*
        URI location = UriComponentsBuilder.fromPath("/users/")
                    .path(String.valueOf(task.getId().toString()))
                    .build().toUri();
        */
        return ResponseEntity.ok().body(task);
    }

    @RequestMapping(method = RequestMethod.GET,value = "{id}")
    @ResponseBody
    public ResponseEntity<Task> getTask(@PathVariable("id") final String id) {
        UUID uuid = UUID.fromString(id);
        Task task = tasksRepository.get(uuid);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(task);
    }

    @RequestMapping(method = RequestMethod.PATCH,value = "{id}")
    @ResponseBody
    public ResponseEntity<Void> patchTask(@PathVariable("id") final String id,
                            @RequestBody final PatchTaskRequest newStatus) {
        if (newStatus == null
                || newStatus.getStatus() == null
                || "".equals(newStatus.getStatus())
                || !("inbox".equals(newStatus.getStatus()) || "done".equals(newStatus.getStatus()))
        ) {
            return ResponseEntity.badRequest().build();
        }
        Task task = tasksRepository.update(UUID.fromString(id), newStatus);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        task.setStatus(newStatus.getStatus());
        return ResponseEntity.noContent().build();

    }

    @RequestMapping(method = RequestMethod.DELETE,value = "{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteTask(@PathVariable("id") final String id) {
        Task task = tasksRepository.remove(UUID.fromString(id));
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
