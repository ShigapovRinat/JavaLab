package ru.javalab.downloadingFiles.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.javalab.downloadingFiles.models.FileInfo;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class FilesInfoRepositoryImpl implements FilesInfoRepository {

    //language=SQL
    private static final String SQL_INSERT = "insert into \"file_info\"(storage_file_name, original_file_name, \"size\", type, path)" +
            "values (?,?,?,?,?)";
    //language=SQL
    private static final String SQL_FIND_BY_STORAGE_FILE_NAME = "select * from \"file_info\" where storage_file_name = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public FilesInfoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<FileInfo> fileInfoRowMapper = (row, rowNumber) ->
        FileInfo.builder()
                .storageFileName(row.getString("storage_file_name"))
                .originalFileName(row.getString("original_file_name"))
                .type(row.getString("type"))
                .path(row.getString("path"))
                .id(row.getLong("id"))
                .size(row.getLong("size"))
                .build();

    @Override
    public Optional<FileInfo> find(String storageFileName) {
        try {
            FileInfo fileInfo = jdbcTemplate.queryForObject(SQL_FIND_BY_STORAGE_FILE_NAME, new Object[]{storageFileName}, fileInfoRowMapper);
            return Optional.ofNullable(fileInfo);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<FileInfo> findAll() {
        return null;
    }

    @Override
    public void save(FileInfo fileInfo) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
            statement.setString(1, fileInfo.getStorageFileName());
            statement.setString(2, fileInfo.getOriginalFileName());
            statement.setLong(3, fileInfo.getSize());
            statement.setString(4, fileInfo.getType());
            statement.setString(5, fileInfo.getPath());
            return statement;
        }, keyHolder);
        fileInfo.setId((Long) keyHolder.getKey());
    }

    @Override
    public void delete(String storageFileName) {

    }
}
