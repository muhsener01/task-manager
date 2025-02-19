package github.muhsener01.task.manager.data.access.adapter;

import github.muhsener01.task.manager.domain.application.dto.TaskDetailsDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class TaskDetails implements TaskDetailsDTO {

    private UUID id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt ;


    public TaskDetails() {
    }

    public TaskDetails(UUID id, String title, String description, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
