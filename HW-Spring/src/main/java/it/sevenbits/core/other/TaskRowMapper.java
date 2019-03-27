package it.sevenbits.core.other;

import it.sevenbits.core.model.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper<Task>{

    //Хз как пакет назвать. Это чтобы код не дублировать, а ламбду тут не написать
    @Override
    public Task mapRow(ResultSet resultSet, int i) throws SQLException {
        String id = resultSet.getString(1);
        String name = resultSet.getString(2);
        String status = resultSet.getString(3);
        String createAt = resultSet.getString(4);
        String updateAt = resultSet.getString(5);
        return new Task(id, name, status, createAt, updateAt);
    }
}
