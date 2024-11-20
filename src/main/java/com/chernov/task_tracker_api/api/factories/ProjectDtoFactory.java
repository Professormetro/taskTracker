package com.chernov.task_tracker_api.api.factories;


import com.chernov.task_tracker_api.api.dto.ProjectDto;
import com.chernov.task_tracker_api.store.entities.ProjectEntity;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
public class ProjectDtoFactory {

    public ProjectDto makeProjectDto(ProjectEntity entity) {

        return ProjectDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .build();

    }

}
