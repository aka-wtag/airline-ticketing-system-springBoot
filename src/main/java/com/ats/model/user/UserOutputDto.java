package com.ats.model.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserOutputDto {
    private int userId;
    private String userFullName;
    private String userEmail;
    private String userContact;
}
