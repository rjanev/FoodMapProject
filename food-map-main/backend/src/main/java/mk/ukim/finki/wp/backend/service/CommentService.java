package mk.ukim.finki.wp.backend.service;



import mk.ukim.finki.wp.backend.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<Comment> findAll();
    Optional<Comment> findById(Long id);

    List<Comment> findByLocal(Long localId);
    Optional<Comment> save(String comment, Long localId);
    Optional<Comment> edit(Long id, String comment, Long localId);

    void delete (Long id);
}
