package com.emc.belustyle.rest;

import com.emc.belustyle.dto.ProductDTO;
import com.emc.belustyle.dto.ResponseDTO;
import com.emc.belustyle.entity.ProductVariation;
import com.emc.belustyle.service.ProductVariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/product-variations")
public class ProductVariationRestController {
    private final ProductVariationService productVariationService;
    @Autowired
    public ProductVariationRestController(ProductVariationService productVariationService) {
        this.productVariationService = productVariationService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteProductVariation(@PathVariable int id) {
        ResponseDTO responseDTO = new ResponseDTO();
        if(productVariationService.deleteById(id)){
            responseDTO.setMessage("Variation deleted successfully");
            responseDTO.setStatusCode(200);
            return ResponseEntity.status(200).body(responseDTO);
        } else {
            responseDTO.setStatusCode(404);
            responseDTO.setMessage("Variation not found");
            return ResponseEntity.status(404).body(responseDTO);
        }
    }

    @GetMapping("/{id}")
    public ProductVariation getProductVariation(@PathVariable int id) {
        return productVariationService.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    @PutMapping("/{variationId}")
    public ResponseEntity<ProductVariation> updateProductVariation(
            @PathVariable int variationId,
            @RequestBody ProductDTO.ProductVariationDTO variationDTO) {
        System.out.println(variationDTO.getSizeId());

        ProductVariation updatedVariation = productVariationService.updateProductVariation(variationId, variationDTO);
        return ResponseEntity.ok(updatedVariation);
    }
}
