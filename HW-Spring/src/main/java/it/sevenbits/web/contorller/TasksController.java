package it.sevenbits.web.contorller;

import it.sevenbits.web.model.AddTaskRequest;
import it.sevenbits.web.model.PatchTaskRequest;
import it.sevenbits.core.model.Task;
import it.sevenbits.core.repository.TasksRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

@Controller
@RequestMapping("/tasks")
public class TasksController {
    private final TasksRepository tasksRepository;

    public TasksController(TasksRepository tasksRepository){
        this.tasksRepository = tasksRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Task>> list() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(tasksRepository.getAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Task> create(@RequestBody AddTaskRequest newTask) {
        if (newTask == null) {
            return ResponseEntity.badRequest().build();
        }
        UUID id = UUID.randomUUID();

        final SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        final String utcTime = sdf.format(new Date());
        Task task = tasksRepository.put(id,
                new Task(id.toString(),
                        newTask.getText(),
                        "inbox",
                        utcTime)
        );
        URI location = UriComponentsBuilder.fromPath("/users/")
                .path(String.valueOf(id.toString()))
                .build().toUri();
        return ResponseEntity.created(location).body(task);
    }

    @RequestMapping(method = RequestMethod.GET,value = "{id}")
    @ResponseBody
    public ResponseEntity<Task> getTask(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        Task task = tasksRepository.get(uuid);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(task);
    }

    @RequestMapping(method = RequestMethod.PATCH,value = "{id}")
    @ResponseBody
    public ResponseEntity<Void> patchTask(@PathVariable("id") String id,
                            @RequestBody PatchTaskRequest newStatus) {
        if (newStatus == null
                || newStatus.getStatus() == null
                || "".equals(newStatus.getStatus())
        ) {
            return ResponseEntity.badRequest().build();
        }
        Task task = tasksRepository.get(UUID.fromString(id));
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        task.setStatus(newStatus.getStatus());
        return ResponseEntity.noContent().build();

    }

    @RequestMapping(method = RequestMethod.DELETE,value = "{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteTask(@PathVariable("id") String id) {
        Task task = tasksRepository.remove(UUID.fromString(id));
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
