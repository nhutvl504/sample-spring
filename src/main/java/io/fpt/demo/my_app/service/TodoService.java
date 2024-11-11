package io.fpt.demo.my_app.service;

import io.fpt.demo.my_app.domain.Todo;
import io.fpt.demo.my_app.model.TodoDTO;
import io.fpt.demo.my_app.repos.TodoRepository;
import io.fpt.demo.my_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(final TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoDTO> findAll() {
        final List<Todo> todos = todoRepository.findAll(Sort.by("id"));
        return todos.stream()
                .map(todo -> mapToDTO(todo, new TodoDTO()))
                .toList();
    }

    public TodoDTO get(final Long id) {
        return todoRepository.findById(id)
                .map(todo -> mapToDTO(todo, new TodoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TodoDTO todoDTO) {
        final Todo todo = new Todo();
        mapToEntity(todoDTO, todo);
        return todoRepository.save(todo).getId();
    }

    public void update(final Long id, final TodoDTO todoDTO) {
        final Todo todo = todoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(todoDTO, todo);
        todoRepository.save(todo);
    }

    public void delete(final Long id) {
        todoRepository.deleteById(id);
    }

    private TodoDTO mapToDTO(final Todo todo, final TodoDTO todoDTO) {
        todoDTO.setId(todo.getId());
        todoDTO.setName(todo.getName());
        return todoDTO;
    }

    private Todo mapToEntity(final TodoDTO todoDTO, final Todo todo) {
        todo.setName(todoDTO.getName());
        return todo;
    }

}
