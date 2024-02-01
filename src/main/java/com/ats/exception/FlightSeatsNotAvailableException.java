package com.ats.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightSeatsNotAvailableException extends RuntimeException{
    public FlightSeatsNotAvailableException(String errorMessage) {
        super(errorMessage);
    }
}
