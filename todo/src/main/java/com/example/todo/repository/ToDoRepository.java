package com.example.todo.repository;

import com.example.todo.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ToDoRepository extends JpaRepository<ToDo,Long> {
    List<ToDo> findByName(String name);
}
