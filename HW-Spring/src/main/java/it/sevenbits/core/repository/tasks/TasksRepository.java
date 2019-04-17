package it.sevenbits.core.repository.tasks;

import it.sevenbits.core.model.Task;

import java.util.List;

/**
 *
 */
public interface TasksRepository {

    /**
     * Create task in repository with this text for this user
     * @param text task text
     * @param owner owner id
     * @return created task
     */
    Task create(String text, String owner);

    /**
     * Return task for user from repository
     * @param id task id
     * @param owner user id
     * @return task or null (if not found)
     */
    Task get(String id, String owner);

    /**
     * Delete task from repository
     * @param id task id
     * @param owner user id
     * @return deleted task
     */
    Task remove(String id, String owner);

    /**
     * Change params of task in repository for user
     * @param task task
     * @param owner user id
     */
    void update(Task task, String owner);

    /**
     * Return all tasks with this params
     * @param status status
     * @param order order
     * @param page page
     * @param size size
     * @param owner owner
     * @return list tasks
     */
    List<Task> getAll(String status,
                      String order,
                      int page,
                      int size,
                      String owner);

    /**
     * Count of tasks for this user
     * @param owner user id
     * @return Count of tasks
     */
    int size(String owner);
}
