package com.emc.belustyle.service;

import com.emc.belustyle.entity.Category;
import com.emc.belustyle.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }

    @Transactional
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Category updatedCategory) {
        Optional<Category> existingCategory = categoryRepository.findById(updatedCategory.getCategoryId());
        if (existingCategory.isPresent()) {
            Category category = existingCategory.get();
            category.setCategoryName(updatedCategory.getCategoryName());
            category.setCategoryDescription(updatedCategory.getCategoryDescription());
            category.setImageUrl(updatedCategory.getImageUrl());
            return categoryRepository.save(category);
        }
        return null;
    }
}
