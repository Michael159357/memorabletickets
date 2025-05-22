package org.example.pioneer.dto;

import lombok.Data;

@Data
public class UserProfileDTO {
    private Long id;
    private String username;
    private String email;
    private String lastName;
}
