package it.sevenbits.config;

import it.sevenbits.core.repository.tasks.DatabaseTasksRepository;
import it.sevenbits.core.repository.tasks.TasksRepository;
import it.sevenbits.core.repository.users.DatabaseUsersRepository;
import it.sevenbits.core.repository.users.UsersRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

@Configuration
public class RepositoryConfig {
    @Bean
    public TasksRepository tasksRepository(
            @Qualifier("JdbcOperations") final JdbcOperations jdbcOperations) {
        return new DatabaseTasksRepository(jdbcOperations);
    }

    @Bean
    public UsersRepository usersRepository(
            @Qualifier("JdbcOperations") final JdbcOperations jdbcOperations) {
        return new DatabaseUsersRepository(jdbcOperations);
    }
}
