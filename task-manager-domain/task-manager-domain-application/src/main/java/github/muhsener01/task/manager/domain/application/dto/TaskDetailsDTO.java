package github.muhsener01.task.manager.domain.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TaskDetailsDTO {

    UUID getId();
    String getTitle();
    String getDescription();
    String getStatus();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
}
