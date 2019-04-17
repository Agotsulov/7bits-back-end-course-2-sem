package it.sevenbits.web.contorller;

import it.sevenbits.core.model.Meta;
import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.tasks.TasksRepository;
import it.sevenbits.core.repository.users.UsersRepository;
import it.sevenbits.web.model.AddTaskRequest;
import it.sevenbits.web.model.ListTaskWithMetaResponse;
import it.sevenbits.web.model.PatchTaskRequest;
import it.sevenbits.core.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("/tasks")
public class TasksController {

    @Value("${meta.defaultStatus}")
    private String defaultStatus = "inbox";
    @Value("${meta.defaultOrder}")
    private String defaultOrder = "desc";
    @Value("${meta.defaultPage}")
    private int defaultPage = 1;
    @Value("${meta.defaultSize}")
    private int defaultSize = 1;
    @Value("${meta.minSize}")
    private int defaultMinSize = 1;
    @Value("${meta.maxSize}")
    private int defaultMaxSize = 1;

    private final UsersRepository usersRepository;
    private final TasksRepository tasksRepository;

    public TasksController(final TasksRepository tasksRepository,
                           final UsersRepository usersRepository) {
        this.tasksRepository = tasksRepository;
        this.usersRepository = usersRepository;
    }

    private String getCurrentId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = usersRepository.findByUserName(username);
        return user.getId();
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

        String owner = getCurrentId();

        int total = tasksRepository.size(owner);
        Meta meta = new Meta(total, page, size,
                "/tasks?status=" + status + "&order=" + order + "&&page=" + (page + 1) + "&size=" + size,
                "/tasks?status=" + status + "&order=" + order + "&&page=" + (page - 1) + "&size=" + size,
                "/tasks?status=" + status + "&order=" + order + "&&page=" + 1 + "&size=" + size,
                "/tasks?status=" + status + "&order=" + order + "&&page=" + (total / size + 1) + "&size=" + size
        );


        ListTaskWithMetaResponse listTaskWithMetaResponse = new ListTaskWithMetaResponse(meta,
                tasksRepository.getAll(status, order, page, size, owner));

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

        Task task = tasksRepository.create(newTask.getText(), getCurrentId());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", "/tasks/" + task.getId().toString());
        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    @ResponseBody
    public ResponseEntity<Task> getTask(@PathVariable("id") final String id) {
        Task task = tasksRepository.get(id, getCurrentId());
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(task);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "{id}")
    @ResponseBody
    public ResponseEntity<Void> patchTask(@PathVariable("id") final String id,
                            @RequestBody final PatchTaskRequest patchTaskRequest) {
        Task task = tasksRepository.get(id, getCurrentId());
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        task.setStatus(patchTaskRequest.getStatus());
        task.setText(patchTaskRequest.getText());
        tasksRepository.update(task, getCurrentId());
        return ResponseEntity.noContent().build();

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteTask(@PathVariable("id") final String id) {
        Task task = tasksRepository.remove(id, getCurrentId());
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
