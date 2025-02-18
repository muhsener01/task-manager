package github.muhsener01.task.manager.repository;


import github.muhsener01.task.manager.domain.application.dto.TaskDetailsDTO;
import github.muhsener01.task.manager.entity.TaskJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TaskJpaRepository extends JpaRepository<TaskJpaEntity, UUID> {



    Optional<TaskDetailsDTO> findDetailsById(UUID uuid);

}
