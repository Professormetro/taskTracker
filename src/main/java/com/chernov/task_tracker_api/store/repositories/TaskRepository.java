package com.chernov.task_tracker_api.store.repositories;

import com.chernov.task_tracker_api.store.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.config.Task;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

}
