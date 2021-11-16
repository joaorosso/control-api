package api.control.controller;

import api.control.entity.Category;
import api.control.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @GetMapping
    public List<Category> get() {
        return categoryRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        categoryRepository.deleteById(id);
    }
}
