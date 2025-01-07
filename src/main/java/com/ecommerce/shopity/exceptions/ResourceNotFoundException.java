package com.ecommerce.shopity.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    String  resourceName;
    String  fieldName;
    String fieldValue;
    Long fieldId  ;

    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldId) {
        super(String.format("%s not found with %s : %d", resourceName, fieldName, fieldId));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldId = fieldId;
    }

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
