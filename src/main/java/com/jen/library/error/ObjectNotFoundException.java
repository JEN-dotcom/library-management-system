package com.jen.library.error;

import java.util.NoSuchElementException;

public class ObjectNotFoundException extends NoSuchElementException {

    public ObjectNotFoundException(String message){
        super(message);
    }
}
