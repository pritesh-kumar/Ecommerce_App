package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.NoRecordsAddedYetException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

//    List<Category> categories = new ArrayList<>();
//    private Long nextId = 1L;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
//        List<Category> categoryList = categoryRepository.findAll();
        List<Category> categoryList = categoryPage.getContent();
        if(categoryList.isEmpty()){
            throw new NoRecordsAddedYetException("No records have been added yet");
        }
        List<CategoryDTO> categoryDTOS = categoryList.stream()
                .map(category -> modelMapper.map(category,CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedCategory != null) {
            throw new APIException("Category with '"+ category.getCategoryName() + "' name already exists");
        }
//        category.setCategoryId(nextId++);
//        categories.add(category);
        Category lastSavedCategory = categoryRepository.save(category);
        return modelMapper.map(lastSavedCategory,CategoryDTO.class);

    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
//        List<Category> categories = categoryRepository.findAll();

//        Category category = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category with Id " + categoryId + " not found"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

//        Category category =
//                categories.stream()
//                        .filter(category1 -> category1.getCategoryId().equals(categoryId))
//                        .findFirst()
//                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category Id " + categoryId + " not found"));
//        if(category == null) {
//            return "Category not found";
//        }
        CategoryDTO deletedCategoryDTO = modelMapper.map(category,CategoryDTO.class);
        categoryRepository.delete(category);
        return deletedCategoryDTO;
//        return "Category with CategoryId " + categoryId + " successfully deleted!";
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
//        List<Category> categories = categoryRepository.findAll();

//        Category existingCategory = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found"));

        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id",categoryId));

        Category category = modelMapper.map(categoryDTO, Category.class);
        existingCategory.setCategoryName(category.getCategoryName());
        Category updatedCategory = categoryRepository.save(existingCategory);
//        Optional<Category> optional = categories.stream().filter(category1 -> category1.getCategoryId().equals(categoryId)).findFirst();
//        if(optional.isPresent()) {
//            Category existingCategory = optional.get();
//            existingCategory.setCategoryName(category.getCategoryName());
//            categoryRepository.save(existingCategory);
//        }else{
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category Id " + categoryId + " not found");
//        }
        return modelMapper.map(updatedCategory,CategoryDTO.class);
//        return "Category with CategoryId " + categoryId + " successfully updated!";
    }
}
