package com.chernov.task_tracker_api.api.controllers;


import com.chernov.task_tracker_api.api.dto.AckDto;
import com.chernov.task_tracker_api.api.dto.ProjectDto;
import com.chernov.task_tracker_api.api.exceptions.BadRequestException;
import com.chernov.task_tracker_api.api.exceptions.NotFoundException;
import com.chernov.task_tracker_api.api.factories.ProjectDtoFactory;
import com.chernov.task_tracker_api.store.entities.ProjectEntity;
import com.chernov.task_tracker_api.store.repositories.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class ProjectController {

    ProjectRepository projectRepository;
    ProjectDtoFactory projectDtoFactory;

    private static final String FETCH_PROJECTS = "/api/projects";
    private static final String CREATE_PROJECT = "/api/projects";
    private static final String EDIT_PROJECT = "/api/projects/{project_id}";
    private static final String DELETE_PROJECT = "/api/projects/{project_id}";


    @GetMapping(FETCH_PROJECTS)
    public List<ProjectDto> fetchProject(
            @RequestParam(value = "prefix_name", required = false) Optional<String> optionalPrefixName){

        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

        Stream<ProjectEntity> projectStream = optionalPrefixName
                .map(projectRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(projectRepository::streamAll);

        return projectStream
                .map(projectDtoFactory::makeProjectDto)
                .collect(Collectors.toList());
    }


    @PostMapping(CREATE_PROJECT)
    public ProjectDto createProject(@RequestParam String name) {

        projectRepository
                .findByName(name)
                .ifPresent(project -> {
                    throw new BadRequestException(String.format("Project \"%s\" already exist.", name));
                });

        ProjectEntity project = projectRepository.saveAndFlush(
                ProjectEntity
                        .builder()
                        .name(name)
                        .build()
        );

        return projectDtoFactory.makeProjectDto(project);
    }



    @PatchMapping(EDIT_PROJECT)
    public ProjectDto editProject(
            @RequestParam String name,
            @PathVariable("project_id") Long projectId) {

        if(name.trim().isEmpty()) {
            throw new BadRequestException("Name cant be empty");
        }


        ProjectEntity project = getProjectOrThrowException(projectId);


        projectRepository
                .findByName(name)
                .filter(anotherProject -> !Objects.equals(anotherProject.getId(), projectId))
                .ifPresent(anotherProject -> {
                    throw new BadRequestException(String.format("Project \"%s\" already exist.", name));
                });

        project.setName(name);

        projectRepository.saveAndFlush(project);

        return projectDtoFactory.makeProjectDto(project);
    }



    @DeleteMapping(DELETE_PROJECT)

    public AckDto deleteProject(@PathVariable("project_id") Long projectId) {

        ProjectEntity project = getProjectOrThrowException(projectId);

        projectRepository.deleteById(projectId);

        return AckDto.makeDefault(true);
    }

    private ProjectEntity getProjectOrThrowException(Long projectId) {
        return projectRepository
                .findById(projectId)
                .orElseThrow(() -> new NotFoundException(String.format("Project \"%s\" not found.", projectId)));
    }


}
