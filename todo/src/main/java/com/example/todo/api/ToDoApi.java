package com.example.todo.api;


import com.example.todo.entity.ToDo;
import com.example.todo.entity.myenum.ToDoStatus;
import com.example.todo.service.ToDoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/todo")
public class ToDoApi {
    @Autowired
    ToDoServices toDoServices;

    @RequestMapping(method = RequestMethod.GET,path = "")
    public ResponseEntity<Iterable<ToDo>> getList(){
        return ResponseEntity.ok(toDoServices.findAll());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/crate")
    public ResponseEntity<ToDo> crete(@RequestBody ToDo toDo) {
        toDo.setCreatedAt(LocalDateTime.now());
        toDo.setStatus(ToDoStatus.ACTIVE);
        return ResponseEntity.ok(toDoServices.save(toDo));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    public ResponseEntity<ToDo> update(@PathVariable Long id, @RequestBody ToDo toDo) {
        Optional<ToDo> optionalToDo = toDoServices.findById(id);
        if (!optionalToDo.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        ToDo existToDo = optionalToDo.get();
        existToDo.setName(toDo.getName());
        existToDo.setUpdatedAt(LocalDateTime.now());
        return ResponseEntity.ok(toDoServices.save(existToDo));
    }

    //delete vinh vien
    @RequestMapping(method = RequestMethod.DELETE,value = "/deletevv/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id ){
        if (!toDoServices.findById(id).isPresent()){
            ResponseEntity.badRequest().build();
        }
        toDoServices.deleteById(id);
        return ResponseEntity.ok().build();
    }
    //delete mem

    @RequestMapping(method = RequestMethod.PUT, value = "/deletemem/{id}")
    public ResponseEntity<ToDo> deleteBlog(@PathVariable Long id) {
        Optional<ToDo> toDoOptional = toDoServices.findById(id);

        if (!toDoOptional.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        ToDo existToDo = toDoOptional.get();

        existToDo.setStatus(ToDoStatus.DELETED);
        existToDo.setDeletedAt(LocalDateTime.now());

        return ResponseEntity.ok(toDoServices.save(existToDo));
    }

    @RequestMapping(value = "/find/{name}", method = RequestMethod.GET)
    public ResponseEntity<Iterable<ToDo>>myBlog(String name) {
        List<ToDo> toDoList = toDoServices.findByName(name);
        if (!toDoList.isEmpty()){
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(toDoList);
    }
}
