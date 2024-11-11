package io.fpt.demo.my_app.rest;

import io.fpt.demo.my_app.model.TodoDTO;
import io.fpt.demo.my_app.service.TodoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/todos", produces = MediaType.APPLICATION_JSON_VALUE)
public class TodoResource {

    private final TodoService todoService;

    public TodoResource(final TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<TodoDTO>> getAllTodos() {
        return ResponseEntity.ok(todoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getTodo(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(todoService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createTodo(@RequestBody @Valid final TodoDTO todoDTO) {
        final Long createdId = todoService.create(todoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateTodo(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final TodoDTO todoDTO) {
        todoService.update(id, todoDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable(name = "id") final Long id) {
        todoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
