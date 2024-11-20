package com.chernov.task_tracker_api.store.repositories;


import com.chernov.task_tracker_api.store.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {



}
