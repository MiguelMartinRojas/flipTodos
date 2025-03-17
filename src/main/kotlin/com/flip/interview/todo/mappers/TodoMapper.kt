package com.flip.interview.todo.mappers

import com.flip.interview.todo.entities.Todo
import com.flip.interview.todo.models.TodoDto
import org.springframework.stereotype.Component

@Component
class TodoMapper {
    fun toDto(todo: Todo): TodoDto {
        return TodoDto(
            id = todo.id,
            title = todo.title,
            category = todo.category,
            description = todo.description,
            done = todo.done
        )
    }

    fun toDtoList(todos: List<Todo>): List<TodoDto> {
        return todos.map { toDto(it) }
    }

    fun toEntity(todoDto: TodoDto): Todo {
        return Todo(
            id = todoDto.id,
            title = todoDto.title,
            category = todoDto.category,
            description = todoDto.description,
            done = todoDto.done
        )
    }
}