package mk.ukim.finki.wp.backend.repository;


import mk.ukim.finki.wp.backend.model.Comment;
import mk.ukim.finki.wp.backend.model.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findCommentsByLocal(Local local);
}
