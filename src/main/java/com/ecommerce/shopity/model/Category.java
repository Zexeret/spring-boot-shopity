package com.ecommerce.shopity.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.annotation.PersistenceConstructor;

@Entity(name = "categories")
public class Category {

    @Id
    private Long categoryID;
    private String categoryName;


    public Category(Long categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public Category() {}

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryID=" + categoryID +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
