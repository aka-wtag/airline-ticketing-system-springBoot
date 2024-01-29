package com.ats.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
