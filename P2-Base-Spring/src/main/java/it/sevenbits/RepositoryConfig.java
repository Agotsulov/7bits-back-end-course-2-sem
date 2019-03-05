package it.sevenbits;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    @Bean
    public TasksRepository tasksRepository() {
        return new TasksRepository();
    }
}
