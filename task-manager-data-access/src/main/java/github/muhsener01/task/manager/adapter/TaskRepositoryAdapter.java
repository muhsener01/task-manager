package github.muhsener01.task.manager.adapter;


import github.muhsener01.task.manager.domain.application.dto.TaskDetailsDTO;
import github.muhsener01.task.manager.domain.application.ports.output.DataAccessException;
import github.muhsener01.task.manager.domain.application.ports.output.TaskRepository;
import github.muhsener01.task.manager.domain.core.entity.Task;
import github.muhsener01.task.manager.domain.core.exception.Assert;
import github.muhsener01.task.manager.domain.core.valueobject.TaskId;
import github.muhsener01.task.manager.entity.TaskJpaEntity;
import github.muhsener01.task.manager.mapper.DataAccessMapper;
import github.muhsener01.task.manager.repository.TaskJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component

public class TaskRepositoryAdapter implements TaskRepository {

    private static final Logger log = LoggerFactory.getLogger(TaskRepositoryAdapter.class);
    private final TaskJpaRepository jpaRepository;


    public TaskRepositoryAdapter(TaskJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional
    public Task save(Task task) throws DataAccessException {
        try {
            TaskJpaEntity taskToBeSaved = DataAccessMapper.toJpaEntity(task);

            TaskJpaEntity savedTask = jpaRepository.save(taskToBeSaved);

            return task;

        } catch (Exception e) {
            log.error("Error while saving Task: {} ", e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Task> findById(TaskId id) throws DataAccessException {
        try {
            Assert.notNull(id, "id argument cannot be null in method findById()");


            Optional<TaskJpaEntity> byId = jpaRepository.findById(id.val());
            if(byId.isEmpty())
                return Optional.empty();

            return Optional.of(DataAccessMapper.toDomainEntity(byId.get()));

        } catch (Exception e) {
            log.error("Error while fetching Task: {}", e.getMessage());
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<TaskDetailsDTO> queryById(TaskId id) throws DataAccessException {
        try{
            Assert.notNull(id, "id argument cannot be null in method queryById()");


            return jpaRepository.findDetailsById(id.val());

        }catch (Exception e){
            log.error("Error while querying Task: {}" , e.getMessage());
            throw new DataAccessException(e.getMessage() , e);
        }
    }
}
