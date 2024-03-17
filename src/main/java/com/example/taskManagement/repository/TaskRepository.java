package com.example.taskManagement.repository;

import com.example.taskManagement.enums.Status;
import com.example.taskManagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@org.springframework.stereotype.Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {


    public Task findByTitleAndUserId(String title,int userId);

    public Task findByIdAndUserId(int id,int userId);

    public List<Task> findByUserId(int userId);

    public List<Task> findByStatusAndUserId(String status,int userId);

    @Query(value = "select * from task order by title;",nativeQuery = true)
    public List<Task> orderByTitle();
}
