package com.chernov.task_tracker_api.api.factories;

import com.chernov.task_tracker_api.api.dto.ProjectDto;
import com.chernov.task_tracker_api.api.dto.TaskStateDto;
import com.chernov.task_tracker_api.store.entities.TaskStateEntity;
import org.springframework.stereotype.Component;


@Component
public class TaskStateDtoFactory {

    public TaskStateDto maketaskStateDto(TaskStateEntity entity){

        return TaskStateDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .ordinal(entity.getOrdinal())
                .build();

    }
}
