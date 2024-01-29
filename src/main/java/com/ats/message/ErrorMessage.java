package com.ats.message;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class ErrorMessage {
    private String message;
}
