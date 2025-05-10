package com.backend.backend.dao;


import com.backend.backend.validator.ProductValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ProductValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidProduct {
    String message() default "Invalid product data";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}