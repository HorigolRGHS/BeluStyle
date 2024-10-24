package com.emc.belustyle.rest;

import com.emc.belustyle.dto.BrandDTO;
import com.emc.belustyle.dto.SearchBrandDTO;
import com.emc.belustyle.entity.Brand;
import com.emc.belustyle.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandRestController {

    @Autowired
    private BrandService brandService;

    public BrandRestController(BrandService brandService) {
        this.brandService = brandService;
    }

    //Delete brand by id
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{brandId}")
    public ResponseEntity<?> deleteBrand(@PathVariable int brandId) {
        boolean isDeleted = brandService.deleteBrand(brandId);
        if (isDeleted) {
            return ResponseEntity.ok("Brand with ID " + brandId + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Brand with ID " + brandId + " not found.");
        }
    }

    //View brand list
    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<BrandDTO>> getAllBrands() {
        List<BrandDTO> brands = brandService.getAllBrandsWithQuantity();
        return ResponseEntity.ok(brands);
    }

    //View brand by id
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    @GetMapping("/{brandId}")
    public ResponseEntity<Brand> getBrandById(@PathVariable int brandId) {
        Brand brand = brandService.findById(brandId);
        if (brand != null) {
            return ResponseEntity.ok(brand);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Search brand
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    @GetMapping("/search")
    public ResponseEntity<List<SearchBrandDTO>> getAllBrand() {
        List<SearchBrandDTO> result = brandService.getAllBrands();
        return ResponseEntity.ok(result);
    }

    //Create brand
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public Brand createBrand(@RequestBody Brand brand) {
        brand.setBrandId(0);
        return brandService.createBrand(brand);
    }

    //Update brand
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    @PutMapping
    public ResponseEntity<?> updateBrand(@RequestBody Brand updatedBrand) {
        Brand existingBrand = brandService.findById(updatedBrand.getBrandId());
        if (existingBrand == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Brand with ID " + updatedBrand.getBrandId() + " not found.");
        }
        Brand savedBrand = brandService.updateBrand(updatedBrand);
        return ResponseEntity.ok(savedBrand);
    }

}
