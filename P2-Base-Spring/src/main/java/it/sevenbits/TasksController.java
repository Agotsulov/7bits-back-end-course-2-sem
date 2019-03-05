package it.sevenbits;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
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
    public List<Task> list() {
        return tasksRepository.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Task> create(@RequestBody Task newTask) {
        UUID id = UUID.randomUUID();
        Task task = tasksRepository.put(id, newTask);
        URI location = UriComponentsBuilder.fromPath("/items/")
                .path(String.valueOf(id.toString()))
                .build().toUri();
        return ResponseEntity.created(location).body(task);
    }
}
