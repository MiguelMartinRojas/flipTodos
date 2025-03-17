package com.flip.interview.todo.services

import com.flip.interview.todo.entities.Todo
import com.flip.interview.todo.mappers.TodoMapper
import com.flip.interview.todo.models.TodoDto
import com.flip.interview.todo.repositories.TodoRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import kotlin.test.assertFailsWith

@ExtendWith(MockitoExtension::class) // Enable Mockito JUnit 5 extension
class TodoServiceTest {

    @Mock
    private lateinit var todoRepository: TodoRepository

    @Mock
    private lateinit var todoMapper: TodoMapper

    @InjectMocks
    private lateinit var todoService: TodoService // Service under test

    @BeforeEach
    fun setUp() {
    }

    @Test
    fun `getAllTodos should return mapped list`() {
        // Given
        val todos = listOf(
            Todo(1, "Test Todo", "Work", "Test description", false),
            Todo(2, "Another Todo", "Personal", "Another description", true)
        )
        val expectedDtos = listOf(
            TodoDto(1, "Test Todo", "Work", "Test description", false),
            TodoDto(2, "Another Todo", "Personal", "Another description", true)
        )

        `when`(todoRepository.findAll()).thenReturn(todos)
        `when`(todoMapper.toDtoList(todos)).thenReturn(expectedDtos)

        // When
        val result = todoService.getAllTodos()

        // Then
        assertEquals(expectedDtos, result)
        verify(todoRepository, times(1)).findAll()
        verify(todoMapper, times(1)).toDtoList(todos)
    }

    @Test
    fun `getTodoById should return mapped todo when found`() {
        // Given
        val todo = Todo(1, "Test Todo", "Work", "Test description", false)
        val expectedDto = TodoDto(1, "Test Todo", "Work", "Test description", false)

        `when`(todoRepository.findById(1)).thenReturn(Optional.of(todo))
        `when`(todoMapper.toDto(todo)).thenReturn(expectedDto)

        // When
        val result = todoService.getTodoById(1)

        // Then
        assertEquals(expectedDto, result)
        verify(todoRepository, times(1)).findById(1)
        verify(todoMapper, times(1)).toDto(todo)
    }

    @Test
    fun `getTodoById should throw exception when not found`() {
        // Given
        `when`(todoRepository.findById(1)).thenReturn(Optional.empty())

        // When
        val exception = assertFailsWith<RuntimeException> {
            todoService.getTodoById(1)
        }

        // then
        assertEquals("Todo not found with ID: 1", exception.message)
        verify(todoRepository, times(1)).findById(1)
    }

    @Test
    fun `createTodo should save and return mapped todo`() {
        // Given
        val todoDto = TodoDto(0, "New Todo", "Personal", "New Todo description", false)
        val todo = Todo(0, "New Todo", "Personal", "New Todo description", false)
        val savedTodo = Todo(3, "New Todo", "Personal", "New Todo description", false)
        val savedDto = TodoDto(3, "New Todo", "Personal", "New Todo description", false)

        `when`(todoMapper.toEntity(todoDto)).thenReturn(todo)
        `when`(todoRepository.save(todo)).thenReturn(savedTodo)
        `when`(todoMapper.toDto(savedTodo)).thenReturn(savedDto)

        // When
        val result = todoService.createTodo(todoDto)

        // Then
        assertEquals(savedDto, result)
        verify(todoMapper, times(1)).toEntity(todoDto)
        verify(todoRepository, times(1)).save(todo)
        verify(todoMapper, times(1)).toDto(savedTodo)
    }

    @Test
    fun `markTodosAsDone should mark todos as done and return updated list`() {
        // Given
        val ids = listOf(1L, 3L)
        val todos = listOf(
            Todo(1, "Test Todo", "Work", "Test description", false),
            Todo(2, "Test Todo 2", "Work", null, false),
            Todo(3, "Another Todo", "Personal", "Another description", false))
        val updatedTodos = todos.map { it.copy(done = true) }
        val updatedTodosDtos = updatedTodos.map { todoMapper.toDto(it) }

        `when`(todoRepository.findAllById(ids)).thenReturn(todos)
        `when`(todoRepository.saveAll(updatedTodos)).thenReturn(updatedTodos)
        `when`(todoMapper.toDtoList(updatedTodos)).thenReturn(updatedTodosDtos)

        // When
        val result = todoService.markTodosAsDone(ids)

        // Then
        assertEquals(updatedTodosDtos, result)
        verify(todoMapper, times(1)).toDtoList(updatedTodos)
        verify(todoRepository, times(1)).findAllById(ids)
        verify(todoRepository, times(1)).saveAll(updatedTodos)
    }

    @Test
    fun `getTodosByCategory should return todos filtered by category`() {
        // Given
        val category1 = "Work"
        val category2 = "School"
        val todos = listOf(
            Todo(1, "Work Todo 1", category1, "Description 1", false),
            Todo(2, "Work Todo 2", category1, "Description 2", true),
            Todo(2, "Work Todo 3", category2, "Description 3", true)
        )
        val todosResult = todos.filter { it.category == category1 }
        val todoDtos = todosResult.map { TodoDto(it.id, it.title, it.category, it.description, it.done) }

        `when`(todoRepository.findByCategory(category1)).thenReturn(todosResult)
        `when`(todoMapper.toDtoList(todosResult)).thenReturn(todoDtos)

        // When
        val result = todoService.getTodosByCategory(category1)

        // Then
        assertEquals(2, result.size)
        assertTrue(result.all { it.category == category1 })
        verify(todoRepository, times(1)).findByCategory(category1)
        verify(todoMapper, times(1)).toDtoList(todosResult)
    }
}