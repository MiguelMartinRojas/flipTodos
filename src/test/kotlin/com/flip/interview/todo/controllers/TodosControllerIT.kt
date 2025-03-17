package com.flip.interview.todo.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.flip.interview.todo.entities.Todo
import com.flip.interview.todo.models.TodoDto
import com.flip.interview.todo.repositories.TodoRepository
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
class TodoControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var todoRepository: TodoRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper


    // Test for getting all Todos (without category)
    @Test
    fun `should return all todos when no category is specified`() {
        // Given
        val todo1 = Todo(
            title = "Todo 1",
            category = "Work",
            description = "Description 1",
            done = false
        )
        val todo2 = Todo(
            title = "Todo 2",
            category = "Home",
            description = "Description 2",
            done = true
        )

        todoRepository.saveAll(listOf(todo1, todo2))

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/todos"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Todo 1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].category").value("Home"))
    }

    // Test for getting todos by category
    @Test
    fun `should return todos filtered by category`() {
        // Given
        val todo1 = Todo(
            title = "Todo 1",
            category = "Work",
            description = "Description 1",
            done = false
        )
        val todo2 = Todo(
            title = "Todo 2",
            category = "Work",
            description = "Description 2",
            done = true
        )
        val todo3 = Todo(
            title = "Todo 3",
            category = "Home",
            description = "Description 3",
            done = false
        )

        todoRepository.saveAll(listOf(todo1, todo2, todo3))

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/todos")
            .param("category", "Work"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2)) // Only "Work" category
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Todo 1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].category").value("Work"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].category").value("Work"))
    }

    // Test for creating a new todo
    @Test
    fun `should create a new todo`() {
        // Given
        val todoDto = TodoDto(
            title = "New Todo",
            category = "Work",
            description = "New description",
            done = false
        )

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(todoDto)))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("New Todo"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("Work"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(false))
    }

    // Test for marking todos as done
    @Test
    fun `should mark todos as done`() {
        // Given
        val todo1 = Todo(
            title = "Todo 1",
            category = "Work",
            description = "Description 1",
            done = false
        )
        val todo2 = Todo(
            title = "Todo 2",
            category = "Work",
            description = "Description 2",
            done = false
        )
        val savedTodos = todoRepository.saveAll(listOf(todo1, todo2))

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/todos/done")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(savedTodos.map { it.id })))
            .andExpect(MockMvcResultMatchers.status().isOk)

        val updatedTodos = todoRepository.findAllById(savedTodos.map { it.id })
        assertTrue(updatedTodos.all { it.done })
    }

    // Test for getting a todo by ID
    @Test
    fun `should return a todo by id`() {
        // Given
        val todo = Todo(
            title = "Todo 1",
            category = "Work",
            description = "Description 1",
            done = false
        )
        val savedTodo = todoRepository.save(todo)

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/todos/${savedTodo.id}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Todo 1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("Work"))
    }
}