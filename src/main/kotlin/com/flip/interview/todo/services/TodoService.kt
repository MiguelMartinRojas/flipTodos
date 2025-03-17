package com.flip.interview.todo.services

import com.flip.interview.todo.mappers.TodoMapper
import com.flip.interview.todo.models.TodoDto
import com.flip.interview.todo.repositories.TodoRepository
import org.springframework.stereotype.Service

@Service
class TodoService(
    private val todoRepository: TodoRepository,
    private val todoMapper: TodoMapper
) {

    fun getAllTodos(): List<TodoDto> = todoMapper.toDtoList(todoRepository.findAll())
    fun getTodoById(id: Long): TodoDto = todoRepository.findById(id)
        .orElseThrow { RuntimeException("Todo not found with ID: $id") }.let {
            todoMapper.toDto(it)
        }

    fun createTodo(todoDto: TodoDto): TodoDto =
        todoRepository.save(todoMapper.toEntity(todoDto)).let { todoMapper.toDto(it) }


    fun markTodosAsDone(ids: List<Long>): List<TodoDto> {
        val todos = todoRepository.findAllById(ids).map { it.copy(done = true) }
        return todoMapper.toDtoList(todoRepository.saveAll(todos))
    }

}