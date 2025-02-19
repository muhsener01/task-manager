package github.muhsener01.task.manager.data.access.adapter;


import github.muhsener01.task.manager.data.access.entity.TaskEntity;
import github.muhsener01.task.manager.data.access.exception.DuplicatedRecordException;
import github.muhsener01.task.manager.data.access.exception.EntryNotFoundException;
import github.muhsener01.task.manager.data.access.mapper.DataAccessMapper;
import github.muhsener01.task.manager.domain.application.dto.TaskDetailsDTO;
import github.muhsener01.task.manager.domain.application.ports.output.DataAccessException;
import github.muhsener01.task.manager.domain.application.ports.output.TaskRepository;
import github.muhsener01.task.manager.domain.core.entity.Task;
import github.muhsener01.task.manager.domain.core.exception.Assert;
import github.muhsener01.task.manager.domain.core.valueobject.TaskId;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class TaskRepositoryMapDBAdapter implements TaskRepository {

    private static final Logger log = LoggerFactory.getLogger(TaskRepositoryMapDBAdapter.class);
    private final DB db;
    private final HTreeMap<UUID, TaskEntity> taskMap;

    @SuppressWarnings("unchecked")
    public TaskRepositoryMapDBAdapter(DB db) {
        this.db = db;
        this.taskMap = (HTreeMap<UUID, TaskEntity>) db.hashMap("tasks").createOrOpen();

    }

    @Override
    public Task save(Task task) throws DataAccessException {
        try {
            assert task.getId() != null;

            TaskEntity taskEntity = taskMap.get(task.getId().val());

            if (taskEntity != null)
                throw new DuplicatedRecordException("Duplicated Task record with ID: " + task.getId().val());

            TaskEntity taskToBeSaved = DataAccessMapper.toJpaEntity(task);
            taskToBeSaved.setCreatedAt(LocalDateTime.now());

            taskMap.put(taskToBeSaved.getId(), taskToBeSaved);
            db.commit();

            return task;
        } catch (Exception e) {
            db.rollback();
            log.debug("Error while saving Task: {}", e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Task> findById(TaskId id) throws DataAccessException {
        try {
            Assert.notNull(id, "null id argument for method findById()");

            TaskEntity taskEntity = taskMap.get(id.val());

            if (taskEntity == null)
                return Optional.empty();

            return Optional.of(DataAccessMapper.toDomainEntity(taskEntity));
        } catch (Exception e) {
            log.error("Error when fetching Task by id:  {}", e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<TaskDetailsDTO> queryById(TaskId id) throws DataAccessException {
        try {
            Assert.notNull(id, "null id argument for method queryById()");

            TaskEntity taskEntity = taskMap.get(id.val());

            if (taskEntity == null)
                return Optional.empty();

            return Optional.of(DataAccessMapper.toDetailsDTO(taskEntity));
        } catch (Exception e) {
            db.rollback();
            log.error("Error when querying Task: {}", e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public List<TaskDetailsDTO> queryAll() {
        try {

            Comparator<TaskEntity> comparator = (t1, t2) -> {
                if (t1.getCreatedAt().isAfter(t2.getCreatedAt()))
                    return -1;
                else if (t1.getCreatedAt().equals(t2.getCreatedAt()))
                    return 0;
                else
                    return 1;
            };

            List<TaskEntity> taskEntities = taskMap.getValues().stream().sorted(comparator).toList();

            return DataAccessMapper.toDetailsDTO(taskEntities);


        } catch (Exception e) {
            log.debug("Error when querying all tasks: {}", e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public void removeAll() {
        try {

            taskMap.clear();
            db.commit();

        } catch (Exception exception) {
            db.rollback();
            log.debug("Error while removing all tasks: {}", exception.getMessage());
            throw new DataAccessException(exception.getMessage(), exception);
        }
    }

    @Override
    public Task update(Task taskToUpdate) {
        try {

            UUID key = taskToUpdate.getId().val();

            TaskEntity oldEntity = taskMap.get(key);

            if (oldEntity == null)
                throw new EntryNotFoundException("No record found with provided id: " + key.toString());



            TaskEntity newEntity = DataAccessMapper.toJpaEntity(taskToUpdate);
            newEntity.setCreatedAt(oldEntity.getCreatedAt());
            newEntity.setUpdatedAt(LocalDateTime.now());
            taskMap.put(key, newEntity);

            db.commit();

            return taskToUpdate;

        } catch (Exception exception) {
            db.rollback();
            log.debug("Error while updating task with ID '{}':  {}", taskToUpdate.getId().val(), exception.getMessage());
            throw new DataAccessException(exception.getMessage(), exception);
        }
    }
}
