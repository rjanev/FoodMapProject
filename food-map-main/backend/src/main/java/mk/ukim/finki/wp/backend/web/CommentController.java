package mk.ukim.finki.wp.backend.web;


import mk.ukim.finki.wp.backend.model.Comment;
import mk.ukim.finki.wp.backend.model.Local;
import mk.ukim.finki.wp.backend.model.enumeration.LocalType;
import mk.ukim.finki.wp.backend.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comments")

public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    private String findAll(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Comment> comments = this.commentService.findAll();
        model.addAttribute("comments", comments);
        model.addAttribute("bodyContent", "comments");
        return "master-template";
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Comment> findById(@PathVariable Long id) {
//        return this.commentService.findById(id)
//                .map(product -> ResponseEntity.ok().body(product))
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

    @GetMapping("/local/{id}")
    private String findByLocal(@PathVariable Long id, Model model) {
        List<Comment> comments = this.commentService.findByLocal(id);
        model.addAttribute("comments", comments);
        model.addAttribute("bodyContent", "comments");
        return "master-template";
    }




//    @PostMapping("/edit/{id}")
//    public ResponseEntity<Comment> edit(@PathVariable Long id,
//                                        @RequestParam(required = false) String commentText,
//                                        @RequestParam Long localId){
//        return this.commentService.edit(id,commentText,localId)
//                .map(product -> ResponseEntity.ok().body(product))
//                .orElseGet(() -> ResponseEntity.badRequest().build());
//    }    so mora edit na komentaro
}
