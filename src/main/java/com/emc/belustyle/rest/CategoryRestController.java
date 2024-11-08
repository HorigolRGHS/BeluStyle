package com.emc.belustyle.rest;

import com.emc.belustyle.dto.CategoryDTO;
import com.emc.belustyle.dto.ResponseDTO;
import com.emc.belustyle.entity.Category;
import com.emc.belustyle.service.CategoryService;
import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

//    @PreAuthorize("permitAll()")
//    @JsonView(Views.ListView.class)
//    @GetMapping
//    public ResponseEntity<?> getCategories() {
//        ResponseDTO responseDTO = new ResponseDTO();
//        List<Category> list = categoryService.findAll();
//        if (list.isEmpty()) {
//            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());
//            responseDTO.setMessage("No categories found");
//            return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
//        } else {
//            return ResponseEntity.status(HttpStatus.OK).body(list);
//        }
//    }


    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategoriesWithQuantity();
        return ResponseEntity.ok(categories);
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable int id) {
        ResponseDTO responseDTO = new ResponseDTO();
        Category category = categoryService.findById(id);
        if (category == null) {
            responseDTO.setStatusCode(HttpStatus.NO_CONTENT.value());
            responseDTO.setMessage("No category found");
            return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(category);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteCategoryById(@PathVariable int id) {
        ResponseDTO responseDTO = new ResponseDTO();
        if(categoryService.deleteCategory(id)){
            responseDTO.setMessage("Category deleted successfully");
            responseDTO.setStatusCode(200);
            return ResponseEntity.status(200).body(responseDTO);
        } else {
            responseDTO.setStatusCode(404);
            responseDTO.setMessage("Category not found");
            return ResponseEntity.status(404).body(responseDTO);
        }
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PreAuthorize("permitAll()")
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {
        ResponseDTO responseDTO = new ResponseDTO();
        Category updatedCategory = categoryService.updateCategory(category);
        if (updatedCategory == null){
            responseDTO.setStatusCode(HttpStatus.NOT_FOUND.value());
            responseDTO.setMessage("No category found");
            return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
        }
    }

}
