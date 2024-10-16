package com.emc.belustyle.service;

import com.emc.belustyle.dto.CategoryDTO;
import com.emc.belustyle.entity.Category;
import com.emc.belustyle.entity.Product;
import com.emc.belustyle.repo.CategoryRepository;
import com.emc.belustyle.repo.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public List<CategoryDTO> getAllCategoriesWithQuantity() {
        List<Object[]> results = categoryRepository.findAllCategoriesWithQuantity();
        List<CategoryDTO> categories = new ArrayList<>();

        for (Object[] result : results) {
            CategoryDTO categoryDto = new CategoryDTO();
            categoryDto.setCategoryId((Integer) result[0]);
            categoryDto.setCategoryName((String) result[1]);
            categoryDto.setCategoryDescription((String) result[2]);
            categoryDto.setImageUrl((String) result[3]);
            categoryDto.setCreatedAt((Date) result[4]);
            categoryDto.setUpdatedAt((Date) result[5]);
            categoryDto.setTotalQuantity(((Number) result[6]).longValue()); // Cast to Long

            categories.add(categoryDto);
        }

        return categories;
    }

    public Category findById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean deleteCategory(int id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
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
