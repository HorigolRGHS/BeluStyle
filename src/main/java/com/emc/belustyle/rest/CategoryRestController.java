package com.emc.belustyle.rest;

import com.emc.belustyle.entity.Category;
import com.emc.belustyle.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public List<Category> getCategories() {
        return categoryService.findAll();
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable int id) {
        return categoryService.findById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteCategoryById(int id) {
         categoryService.deleteCategory(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    @PutMapping
    public Category updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

}
