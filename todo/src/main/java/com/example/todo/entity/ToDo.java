package com.example.todo.entity;


import com.example.todo.entity.base.BaseEntity;
import com.example.todo.entity.myenum.ToDoStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "todos")
public class ToDo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "text")
    private String description;
    private ToDoStatus status;


}
