package it.sevenbits.web.contorller;

import it.sevenbits.core.repository.Repository;
import it.sevenbits.web.model.AddTaskRequest;
import it.sevenbits.web.model.PatchTaskRequest;
import it.sevenbits.core.model.Task;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/tasks")
public class TasksController {
    private final Repository tasksRepository;

    public TasksController(Repository tasksRepository){
        this.tasksRepository = tasksRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Task>> all(
            @RequestParam(value="status", required=false) final String status
    ) {
        String s = "inbox";
        if ("done".equals(status)) {
            s = status;
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(tasksRepository.getAll(s));
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

        URI location = UriComponentsBuilder.fromPath("/users/")
                .path(String.valueOf(task.getId().toString()))
                .build().toUri();
        return ResponseEntity.created(location).body(task);
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
