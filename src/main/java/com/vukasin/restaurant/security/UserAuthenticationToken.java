package com.vukasin.restaurant.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticationToken {

    private String token;
    private Long expiresIn;

}
