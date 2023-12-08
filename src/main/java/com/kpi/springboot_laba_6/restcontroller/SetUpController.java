package com.kpi.springboot_laba_6.restcontroller;

import com.kpi.springboot_laba_6.entity.Category;
import com.kpi.springboot_laba_6.entity.Product;
import com.kpi.springboot_laba_6.exception.DataFormattingException;
import com.kpi.springboot_laba_6.exception.info.ErrorInfo;
import com.kpi.springboot_laba_6.service.AppMVCService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class SetUpController {

    private AppMVCService appService;

    @Autowired
    public SetUpController(AppMVCService appService) {
        this.appService = appService;
    }

    @Operation(
            summary = "Set up data",
            description = "This operation is used to set up initial data for the application. It should be executed before starting any work, as it ensures that the required stubs are created."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status: 200 -> Success",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))

                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status: 500 -> Could not create the stubs",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))
                    )
            }
    )
    @GetMapping("/setup")
    public ResponseEntity<Map<String, String>> setUpProgram(){
        Map<String, String> response = new HashMap<>();
        if(!appService.findAllCategories().isEmpty()){
            response.put("message", "The data is already set");
            return ResponseEntity.ok(response);
        }
        try {
            Category cameras = new Category("Cameras", 0);
            Category headphones = new Category("Headphones", 0);
            Category wirelessHeadphones = new Category( "Wireless headphones", 2);
            Category wireHeadphones = new Category("Wire headphones", 2);

            Product product1 = new Product(
                    "Skullcandy Jib True 2 In-Ear Wireless Earbuds",
                    "32 Hr Battery, Microphone, Works with iPhone Android and Bluetooth Devices - Black",
                    120.5d,
                    appService.findCategoryById(3));
            System.out.println(product1);
            Product product2 = new Product(
                    "Samsung Galaxy Buds Live, Earbuds",
                    "Samsung Galaxy Buds Live, Earbuds w/Active Noise Cancelling (Mystic Black) (Renewed)",
                    104.3d,
                    appService.findCategoryById(3));

            Product product3 = new Product(
                    "Maxell - 190319 Stereo Headphones",
                    "Maxell - 190319 Stereo Headphones - 3.5mm Cord with 6-Foot Length - Soft Padded Ear Cushions",
                    150.5d,
                    appService.findCategoryById(4));

            Product product4 = new Product(
                    "DaKuan 3 Packs Earphone with Remote Microphone",
                    "DaKuan 3 Packs Earphone with Remote Microphone, in Ear Stereo Sound Noise Isolating Tangle Free for Smartphones",
                    99.9d,
                    appService.findCategoryById(4));

            Product product5 = new Product(
                    "Monoprice - 116150 Modern Retro Over Ear Headphones",
                    "Monoprice - 116150 Modern Retro Over Ear Headphones with Ultra-Comfortable Ear Pads Perfect for Mobile Devices, HiFi, and Audio/Video Production Black",
                    60.5d,
                    appService.findCategoryById(2));

            Product product6 = new Product(
                    "Polaroid IS048 Digital Camera",
                    "Small Lightweight Waterproof Instant Sharing 16 MP Digital Portable Handheld Action Camera (Black)",
                    202.5d,
                    appService.findCategoryById(1));

            appService.createCategory(cameras);
            appService.createCategory(headphones);
            appService.createCategory(wireHeadphones);
            appService.createCategory(wirelessHeadphones);

            appService.createProduct(product1);
            appService.createProduct(product2);
            appService.createProduct(product3);
            appService.createProduct(product4);
            appService.createProduct(product5);
            appService.createProduct(product6);
        }catch (RuntimeException e){
            throw new DataFormattingException("Could not create a starter set");
        }
        response.put("message", "The set up was successful");
        return ResponseEntity.ok(response);
    }
}
