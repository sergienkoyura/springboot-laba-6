package com.kpi.springboot_laba_6.restcontroller;

import com.kpi.springboot_laba_6.dto.ProductDTO;
import com.kpi.springboot_laba_6.entity.Product;
import com.kpi.springboot_laba_6.exception.info.ErrorInfo;
import com.kpi.springboot_laba_6.form.CreateProductForm;
import com.kpi.springboot_laba_6.form.UpdateProductForm;
import com.kpi.springboot_laba_6.infopage.PageInfo;
import com.kpi.springboot_laba_6.repository.CategoryRepository;
import com.kpi.springboot_laba_6.service.ProductService;
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
@RequestMapping("/api/v1/products")
public class ProductController {

    private ProductService productService;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @Operation(
            summary = "Receive all products' info",
            description = "This operation allows you to get products' info using pagination"
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
                            description = "HTTP Status: 404 -> Products not found",
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
    public ResponseEntity<PageInfo<ProductDTO>> getAllProducts(@RequestParam int page,
                                                               @RequestParam(required = false) Double maxPrice){
        PageInfo<ProductDTO> products = productService.getAllProducts(page, maxPrice);
        return ResponseEntity.ok(products);
    }

    @Operation(
            summary = "Receive product's info",
            description = "This operation allows you to get product's info by providing its id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status: 200 -> Success",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "HTTP Status: 404 -> Product not found",
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
    public ResponseEntity<ProductDTO> getProductById(@PathVariable int id){
            ProductDTO productDTO = productService.getProductById(id);
            return ResponseEntity.ok(productDTO);
    }


    @Operation(
            summary = "Delete product",
            description = "This operation allows you to delete product by providing its id"
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
                            description = "HTTP Status: 404 -> Product not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))

                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status: 500 -> Could not delete product",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable int id){
            Boolean deleted = productService.deleteProduct(id);
            return ResponseEntity.ok(deleted);
    }


    @Operation(
            summary = "Create new product",
            description = "This operation allows you to create a new product by providing its info"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "HTTP Status: 201 -> Created",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))

                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "HTTP Status: 400 -> Invalid data provided",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "HTTP Status: 404 -> Invalid category id provided",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))

                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status: 500 -> Could not create product",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid CreateProductForm createProductForm, BindingResult bindingResult){
        if(bindingResult.hasErrors())  throw new IllegalArgumentException();
        Product newProduct = new Product(createProductForm.getName(), createProductForm.getDescription(), createProductForm.getPrice(), categoryRepository.findById(createProductForm.getCategoryId()).get());
        newProduct = productService.createProduct(newProduct);
        return ResponseEntity.created(URI.create("/products/" + newProduct.getId())).body(newProduct);
    }


    @Operation(
            summary = "Update existing product",
            description = "This operation allows you to update an existing product by providing its id and new info"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status: 200 -> Updated",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "HTTP Status: 404 -> Product or provided parent category not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))

                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status: 500 -> Could not update product",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
                    )
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody UpdateProductForm updateProductForm){
        Product updatedProduct = productService.updateProduct(id, updateProductForm);
        return ResponseEntity.ok(updatedProduct);
    }

}
