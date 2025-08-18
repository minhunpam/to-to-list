package com.example.to_do_list.repository;

import com.example.to_do_list.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long>{
    // SELECT * FROM todos WHERE completed = ?
    List<Todo> findByCompleted(boolean completed);

    // SELECT * FROM todos ORDER BY due_date ASC
    List<Todo> findAllByOrderByDue_dateAsc();

    // Later can add more interface methods for custom queries
}
