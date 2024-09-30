package com.ecommerce.project.exceptions;

import com.ecommerce.project.model.Product;

import java.util.PrimitiveIterator;

public class ResourseAlreadyPresentException extends RuntimeException {


    public ResourseAlreadyPresentException(String resource, String resourceDetail) {
        super(String.format("%s with resource name %s already exists", resource, resourceDetail));

    }
}
