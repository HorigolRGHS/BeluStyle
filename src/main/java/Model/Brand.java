/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Timestamp;

/**
 *
 * @author Le Khac Huy - CE180311
 */
public class Brand {
      private int brandId;
    private String brandName;
    private String brandDescription;
    private String logoUrl;
    private String websiteUrl;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Brand() {
    }

    public Brand(int brandId, String brandName, String brandDescription, String logoUrl, String websiteUrl, Timestamp createdAt, Timestamp updatedAt) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.brandDescription = brandDescription;
        this.logoUrl = logoUrl;
        this.websiteUrl = websiteUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandDescription() {
        return brandDescription;
    }

    public void setBrandDescription(String brandDescription) {
        this.brandDescription = brandDescription;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Brand{" + "brandId=" + brandId + ", brandName=" + brandName + ", brandDescription=" + brandDescription + ", logoUrl=" + logoUrl + ", websiteUrl=" + websiteUrl + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
    
    
}
