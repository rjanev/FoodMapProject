package mk.ukim.finki.wp.backend.service.impl;


import mk.ukim.finki.wp.backend.model.Comment;
import mk.ukim.finki.wp.backend.model.Local;
import mk.ukim.finki.wp.backend.repository.CommentRepository;
import mk.ukim.finki.wp.backend.repository.LocalRepository;
import mk.ukim.finki.wp.backend.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final LocalRepository localRepository;

    public CommentServiceImpl(CommentRepository commentRepository, LocalRepository localRepository) {
        this.commentRepository = commentRepository;
        this.localRepository = localRepository;
    }

    @Override
    public List<Comment> findAll() {
        return this.commentRepository.findAll();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return this.commentRepository.findById(id);
    }

    @Override
    public List<Comment> findByLocal(Long localId) {
        Local local=localRepository.findById(localId).get();
        return this.commentRepository.findCommentsByLocal(local);
    }

    @Override
    public Optional<Comment> save(String commentText, Long localId) {
        Local local = this.localRepository.findById(localId).get();
        Comment comment = new Comment(commentText,local);
    //    local.getComments().add(comment);
    //    localRepository.save(local);
        return Optional.of(this.commentRepository.save(comment));
    }

    @Override
    public Optional<Comment> edit(Long id, String commentText, Long localId) {
        Comment comment=this.commentRepository.findById(id).get();
        comment.setComment(commentText);
        Local local = this.localRepository.findById(localId).get();
        comment.setLocal(local);
        return Optional.of(this.commentRepository.save(comment));
    }

    @Override
    public void delete(Long id) {
        Comment comment= this.commentRepository.findById(id).get();
        this.commentRepository.delete(comment);
    }
}
