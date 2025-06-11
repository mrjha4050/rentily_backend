package com.backend.backend.validator;

import com.backend.backend.dao.ValidProduct;
import com.backend.backend.models.Product;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProductValidator implements ConstraintValidator<ValidProduct, Product> {
    @Override
    public boolean isValid(Product product , ConstraintValidatorContext context) {
        boolean isValid = true;

        if(!product.getType().matches("SELL|RENT")){
            context.buildConstraintViolationWithTemplate("type must be sell , rent").addPropertyNode("type").addConstraintViolation();
            isValid = false;
        }

        if (!product.getCategory().matches("Books|Electronics|Other")) {
            context.buildConstraintViolationWithTemplate("Category must be related to books, Electronics, Other").addPropertyNode("category").addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}
