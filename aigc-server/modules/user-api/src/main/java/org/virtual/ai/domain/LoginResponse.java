package org.virtual.ai.domain;

import lombok.Data;
import org.virtual.ai.domain.vo.UserVo;

@Data
public class LoginResponse {
    private Long expiresIn;
    String accessToken;
    UserVo user;
}
