package org.virtual.ai.token;

import lombok.Data;

@Data
public class Token {
    private String accessToken;
    private Long expiresIn;
}
