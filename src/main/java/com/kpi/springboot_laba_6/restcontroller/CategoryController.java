package com.kpi.springboot_laba_6.restcontroller;

import com.kpi.springboot_laba_6.dto.CategoryDTO;
import com.kpi.springboot_laba_6.entity.Category;
import com.kpi.springboot_laba_6.exception.info.ErrorInfo;
import com.kpi.springboot_laba_6.form.CreateCategoryForm;
import com.kpi.springboot_laba_6.infopage.PageInfo;
import com.kpi.springboot_laba_6.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }



    @Operation(
            summary = "Receive all categories' info",
            description = "This operation allows you to get categories' info using pagination"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status: 200 -> Success",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageInfo.class))

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "HTTP Status: 404 -> Categories not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))

                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status: 500 -> Could not create the dtos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
                    )
            }
    )

    @GetMapping
    public ResponseEntity<PageInfo<CategoryDTO>> getAllCategories(@RequestParam int page,
                                                            @RequestParam(required = false) Integer parentId){
        PageInfo<CategoryDTO> categories = categoryService.getAllCategories(page, parentId);
        return ResponseEntity.ok(categories);
    }
    @Operation(
            summary = "Receive category's info",
            description = "This operation allows you to get category's info including products and subcategories, by providing category id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status: 200 -> Success",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "HTTP Status: 404 -> Category not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))

                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status: 500 -> Could not create the dto",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
                    )
            }
    )

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable int id){
        CategoryDTO categoryDTO = categoryService.getCategoryDTOById(id);
        return ResponseEntity.ok(categoryDTO);
    }


    @Operation(
            summary = "Delete category",
            description = "This operation allows you to delete category by providing its id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status: 200 -> Success",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "HTTP Status: 404 -> Category not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))

                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status: 500 -> Could not delete category",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable int id){
            Boolean deleted = categoryService.deleteCategory(id);
            return ResponseEntity.ok(deleted);
    }



    @Operation(
            summary = "Create new category",
            description = "This operation allows you to create a new category by providing its name and parent id if needed"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "HTTP Status: 201 -> Created",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))

                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "HTTP Status: 400 -> Invalid data provided",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "HTTP Status: 404 -> Invalid parent id provided",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))

                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status: 500 -> Could not create category",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody @Valid CreateCategoryForm categoryForm, BindingResult bindingResult){
            if(bindingResult.hasErrors()) throw new IllegalArgumentException();
            Category newCategory = new Category(categoryForm.getName(), categoryForm.getParentId());
            newCategory = categoryService.createCategory(newCategory);
            return ResponseEntity.created(URI.create("/categories/" + newCategory.getId())).body(newCategory);
    }



    @Operation(
            summary = "Update existing category",
            description = "This operation allows you to update an existing category by providing its id and new name"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status: 200 -> Updated",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "HTTP Status: 404 -> Category not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))

                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status: 500 -> Could not update category",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
                    )
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody String name){
            Category category = categoryService.updateCategoryName(id, name);
            return ResponseEntity.ok(category);
    }

}
