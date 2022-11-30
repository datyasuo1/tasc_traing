package com.example.todo.service;

import com.example.todo.entity.ToDo;
import com.example.todo.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToDoServices {
    @Autowired
    ToDoRepository toDoRepository;

    public Iterable<ToDo> findAll() {
        return toDoRepository.findAll();
    }

    public List<ToDo> findByName (String name){
        return toDoRepository.findByName(name);
    }
    public ToDo save(ToDo toDo){
        return toDoRepository.save(toDo);
    }

    public void deleteById(Long id) {
        toDoRepository.deleteById(id);
    }

    public Optional<ToDo> findById(Long id) {
        return toDoRepository.findById(id);
    }


}
