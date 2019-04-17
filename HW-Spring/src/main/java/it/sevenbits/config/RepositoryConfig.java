package it.sevenbits.config;

import it.sevenbits.core.repository.tasks.DatabaseTasksRepository;
import it.sevenbits.core.repository.tasks.TasksRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

@Configuration
public class RepositoryConfig {
    @Bean
    public TasksRepository tasksRepository(final @Qualifier("JdbcOperations") JdbcOperations jdbcOperations) {
        return new DatabaseTasksRepository(jdbcOperations);
    }
}
