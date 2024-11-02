package mk.ukim.finki.wp.backend.web;


import mk.ukim.finki.wp.backend.model.Comment;
import mk.ukim.finki.wp.backend.model.Local;
import mk.ukim.finki.wp.backend.model.Product;
import mk.ukim.finki.wp.backend.model.enumeration.LocalType;
import mk.ukim.finki.wp.backend.model.enumeration.ProductType;
import mk.ukim.finki.wp.backend.service.CommentService;
import mk.ukim.finki.wp.backend.service.LocalService;
import mk.ukim.finki.wp.backend.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/locals")
public class LocalController {
    private final LocalService localService;
    private final CommentService commentService;
    private final ProductService productService;

    public LocalController(LocalService localService, CommentService commentService,ProductService productService) {
        this.localService = localService;
        this.commentService = commentService;
        this.productService = productService;
    }
    @GetMapping
    private String findAll(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Local> locals = this.localService.findAll();
        model.addAttribute("locals", locals);
        model.addAttribute("types", LocalType.values());
        model.addAttribute("bodyContent", "probaLocals");
        return "master-template";
    }


    @GetMapping("/{id}")
    public String findById(@PathVariable Long id,  Model model) {
        Local local = this.localService.findById(id).get();
        List<Comment> comments = this.commentService.findByLocal(id);
        List<Product> products = this.productService.findByLocal(id);
        List<Product> foodProducts = new ArrayList<>();
        List<Product> drinkProducts = new ArrayList<>();
        for (var item : products) {
            if (item.getType().equals(ProductType.FOOD)){
                foodProducts.add(item);
            }
            else {
                drinkProducts.add(item);
            }
        }


        model.addAttribute("local", local);
        model.addAttribute("comments", comments);
        model.addAttribute("foodProducts", foodProducts);
        model.addAttribute("drinkProducts", drinkProducts);
        model.addAttribute("bodyContent", "local");
        return "master-template";

    }

    @GetMapping("/add-form")
    public String addLocalPage(Model model) {
        model.addAttribute("types", LocalType.values());
        model.addAttribute("bodyContent", "add-local");
        return "master-template";
    }
    @PostMapping("/add")
    public String save(@RequestParam String name,
                                    @RequestParam String address,
                                    @RequestParam LocalType type,
                                    @RequestParam String workingHours,
                                    @RequestParam Double distance,
                                    @RequestParam(required = false) String image,
                                    @RequestParam String link) {
        this.localService.save(name,address,type, workingHours, distance,image,link);
        return "redirect:/locals";
    }
    @GetMapping("/edit-form/{id}")
    public String editLocalPage(@PathVariable Long id, Model model) {
        if (this.localService.findById(id).isPresent()) {
            Local local = this.localService.findById(id).get();

            model.addAttribute("types", LocalType.values());
            model.addAttribute("local", local);
            model.addAttribute("bodyContent", "edit-local");
            return "master-template";
        }

        return "redirect:/locals?error=LocalNotFound";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String address,
                                    @RequestParam(required = false) LocalType type,
                                    @RequestParam(required = false) String workingHours,
                                    @RequestParam(required = false) Double distance,
                                    @RequestParam(required = false) String image,
                                    @RequestParam(required = false) String link
                                    ){
        this.localService.edit(id,name,address,type, workingHours, distance, image,link);
        return "redirect:/locals";
    }
    @PostMapping("/like")
    public String like(@RequestParam  Long id) {
        this.localService.like(id);
        return "redirect:/locals/"+id;
    }
    @PostMapping("/dislike")
    public String dislike(@RequestParam Long id) {
        this.localService.dislike(id);
        return "redirect:/locals/"+id;
    }



    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        this.localService.deleteById(id);
        return "redirect:/locals";
    }

    @GetMapping("/type/{type}")
    private String findByType(@PathVariable LocalType type, @RequestParam(required = false) String error, Model model) {

        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Local> locals = this.localService.findByType(type);
        model.addAttribute("locals", locals);
        model.addAttribute("types", LocalType.values());
        model.addAttribute("bodyContent", "probaLocals");
        return "master-template";
    }

    @PostMapping("/search")
    public String findByName(@RequestParam String searchByName, Model model) {
        Optional<Local> optionalLocal = this.localService.findByName(searchByName);
        List<Comment> comments = this.commentService.findByLocal(optionalLocal.get().getId());
        List<Product> products = this.productService.findByLocal(optionalLocal.get().getId());
        List<Product> foodProducts = new ArrayList<>();
        List<Product> drinkProducts = new ArrayList<>();
        for (var item : products) {
            if (item.getType().equals(ProductType.FOOD)){
                foodProducts.add(item);
            }
            else {
                drinkProducts.add(item);
            }
        }
        if (optionalLocal.isPresent()) {
            Local local = optionalLocal.get();
            model.addAttribute("local", local);
            model.addAttribute("comments", comments);
            model.addAttribute("foodProducts", foodProducts);
            model.addAttribute("drinkProducts", drinkProducts);
            model.addAttribute("bodyContent", "local");
            return "master-template";
        }
        return "redirect:/locals";
    }

    @PostMapping("/filter")
    private String filter(@RequestParam(required = false) Double distance,
                               @RequestParam(required = false) LocalType type, Model model) {
        List<Local> locals= localService.filter(distance, type);
        model.addAttribute("types", LocalType.values());
        model.addAttribute("locals", locals);
        model.addAttribute("bodyContent", "probaLocals");
        return "master-template";
    }

    @GetMapping("/sort")
    private String sorted(Model model) {
        List<Local> locals= localService.sorted();
        model.addAttribute("locals", locals);
        model.addAttribute("bodyContent", "probaLocals");
        return "master-template";
    }

    @PostMapping("/addComment")
    public String addCom (@RequestParam String commentText,
                       @RequestParam Long localId, Model model) {
        this.commentService.save(commentText,localId);
        Local local = this.localService.findById(localId).get();
        List<Comment> comments = this.commentService.findByLocal(localId);
        List<Product> products = this.productService.findByLocal(localId);
        List<Product> foodProducts = new ArrayList<>();
        List<Product> drinkProducts = new ArrayList<>();
        for (var item : products) {
            if (item.getType().equals(ProductType.FOOD)){
                foodProducts.add(item);
            }
            else {
                drinkProducts.add(item);
            }
        }
        model.addAttribute("local", local);
        model.addAttribute("comments", comments);
        model.addAttribute("foodProducts", foodProducts);
        model.addAttribute("drinkProducts", drinkProducts);
        model.addAttribute("bodyContent", "local");
        return "redirect:/locals/"+localId;
    }

    @PostMapping("/deleteComment/{id}")
    public String deleteComment(@PathVariable Long id, @RequestParam Long locId, Model model) {
        this.commentService.delete(id);
        Local local = this.localService.findById(locId).get();
        List<Comment> comments = this.commentService.findByLocal(locId);
        model.addAttribute("local", local);
        model.addAttribute("comments", comments);
        model.addAttribute("bodyContent", "local");
        return "redirect:/locals/"+locId;
    }

}
