package com.chernov.task_tracker_api.api.factories;


import com.chernov.task_tracker_api.api.dto.ProjectDto;
import com.chernov.task_tracker_api.api.dto.TaskDto;
import com.chernov.task_tracker_api.store.entities.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoFactory {

    public TaskDto makeTaskDto(TaskEntity entity) {


        return TaskDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .description(entity.getDescription())
                .build();

    }

}
