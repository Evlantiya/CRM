package com.test.CRM.controllers.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ElementNotFoundException extends RuntimeException{

    public ElementNotFoundException(String id) {
        super("There are no elements with the specified id " + id);
    }
}
