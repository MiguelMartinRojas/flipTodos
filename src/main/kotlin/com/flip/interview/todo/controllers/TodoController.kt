package com.flip.interview.todo.controllers

import com.flip.interview.todo.models.TodoDto
import com.flip.interview.todo.services.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/todos")
class TodoController(private val todoService: TodoService){

    @GetMapping
    fun getAllTodos(): List<TodoDto> = todoService.getAllTodos()

    @GetMapping("/{id}")
    fun getTodoById(@PathVariable id: Long): ResponseEntity<TodoDto> =
        ResponseEntity.ok(todoService.getTodoById(id))

    @PostMapping
    fun createTodo(@RequestBody todo: TodoDto): ResponseEntity<TodoDto> =
        ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(todo))

    @PostMapping("/done")
    fun markTodosAsDone(@RequestBody ids: List<Long>): ResponseEntity<List<TodoDto>> =
        ResponseEntity.status(HttpStatus.OK).body(todoService.markTodosAsDone(ids))
}