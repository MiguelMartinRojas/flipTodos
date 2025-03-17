package com.flip.interview.todo.mappers

import com.flip.interview.todo.entities.Todo
import com.flip.interview.todo.models.TodoDto
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class) // Enable Mockito JUnit 5 extension
class TodoMapperTest {

    @InjectMocks
    private lateinit var todoMapper: TodoMapper

    @Test
    fun `toDto should convert Todo entity to TodoDto correctly`() {
        // Given
        val todo = Todo(
            id = 1,
            title = "Test Todo",
            category = "Work",
            description = "Test Description",
            done = false
        )

        // When
        val todoDto = todoMapper.toDto(todo)

        // Then
        assertEquals(todo.id, todoDto.id)
        assertEquals(todo.title, todoDto.title)
        assertEquals(todo.category, todoDto.category)
        assertEquals(todo.description, todoDto.description)
        assertEquals(todo.done, todoDto.done)
    }

    @Test
    fun `toDtoList should convert List of Todo entities to List of TodoDtos correctly`() {
        // Given
        val todos = listOf(
            Todo(1, "Todo 1", "Work", "Description 1", false),
            Todo(2, "Todo 2", "Personal", "Description 2", true)
        )

        // When
        val todoDtos = todoMapper.toDtoList(todos)

        // Then
        assertEquals(todos.size, todoDtos.size)
        assertTrue(todoDtos.containsAll(todos.map { todoMapper.toDto(it) }))
    }

    @Test
    fun `toEntity should convert TodoDto to Todo entity correctly`() {
        // Given
        val todoDto = TodoDto(
            id = 1,
            title = "Test Todo",
            category = "Work",
            description = "Test Description",
            done = false
        )

        // When
        val todo = todoMapper.toEntity(todoDto)

        // Then
        assertEquals(todoDto.id, todo.id)
        assertEquals(todoDto.title, todo.title)
        assertEquals(todoDto.category, todo.category)
        assertEquals(todoDto.description, todo.description)
        assertEquals(todoDto.done, todo.done)
    }
}