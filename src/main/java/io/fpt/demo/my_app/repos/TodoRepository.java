package io.fpt.demo.my_app.repos;

import io.fpt.demo.my_app.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TodoRepository extends JpaRepository<Todo, Long> {
}
