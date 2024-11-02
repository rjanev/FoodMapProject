package mk.ukim.finki.wp.backend.web;


import mk.ukim.finki.wp.backend.model.Local;
import mk.ukim.finki.wp.backend.model.Product;
import mk.ukim.finki.wp.backend.model.enumeration.ProductType;
import mk.ukim.finki.wp.backend.service.LocalService;
import mk.ukim.finki.wp.backend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final LocalService localService;

    public ProductController(ProductService productService, LocalService localService) {

        this.productService = productService;

        this.localService = localService;
    }

    @GetMapping
    private String findAll(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Product> products = this.productService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("bodyContent", "products");
        return "master-template";
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Product> findById(@PathVariable Long id) {
//        return this.productService.findById(id)
//                .map(product -> ResponseEntity.ok().body(product))
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
    @GetMapping("/local/{id}")
    private String findByLocal(@PathVariable Long id, Model model) {
        List<Product> products = this.productService.findByLocal(id);
        model.addAttribute("products", products);
        model.addAttribute("bodyContent", "products");
        return "master-template";
    }

    @GetMapping("/type/{type}")
    private String findByType(@PathVariable ProductType type, Model model) {
        List<Product> products = this.productService.findByType(type);
        model.addAttribute("products", products);
        model.addAttribute("bodyContent", "products");
        return "master-template";
    }
@GetMapping("/add-form")
public String addProductPage(Model model) {
    List<Local> locals = this.localService.findAll();

    model.addAttribute("locals", locals);
    model.addAttribute("types", ProductType.values());
    model.addAttribute("bodyContent", "add-product");
    return "master-template";
}

    @PostMapping("/add")
    public String save(@RequestParam String name,
                                        @RequestParam Double price,
                                        @RequestParam String description,
                                        @RequestParam ProductType type,
                                        @RequestParam Long localId) {
        this.productService.save(name,price,description,type,localId);
        return "redirect:/products";
    }

    @GetMapping("/edit-form/{id}")
    public String editProductPage(@PathVariable Long id, Model model) {
        if (this.productService.findById(id).isPresent()) {
            Product product = this.productService.findById(id).get();
            List<Local> locals = this.localService.findAll();

            model.addAttribute("locals", locals);
            model.addAttribute("types", ProductType.values());
            model.addAttribute("product", product);
            model.addAttribute("bodyContent", "edit-product");
            return "master-template";
        }

        return "redirect:/products?error=ProductNotFound";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                                        @RequestParam(required = false) String name,
                                        @RequestParam(required = false) Double price,
                                        @RequestParam(required = false) String description,
                                        @RequestParam(required = false) ProductType type,
                                        @RequestParam Long localId){
        this.productService.edit(id,name,price,description,type,localId);
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        this.productService.deleteById(id);
        return "redirect:/products";
    }
}
