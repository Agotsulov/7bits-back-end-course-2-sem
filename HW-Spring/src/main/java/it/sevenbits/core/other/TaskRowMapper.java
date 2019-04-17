package it.sevenbits.core.other;

import it.sevenbits.core.model.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(final ResultSet resultSet, final int i) throws SQLException {
        String id = resultSet.getString("id");
        String text = resultSet.getString("text");
        String status = resultSet.getString("status");
        String createAt = resultSet.getString("createAt");
        String updateAt = resultSet.getString("updateAt");
        return new Task(id, text, status, createAt, updateAt);
    }
}
