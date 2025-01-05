package org.virtual.ai.token;

import lombok.Data;
import org.virtual.business.common.entity.User;

@Data
public class UserState {
    private String token;
    private User user;
    private Long loginTime;
    private Long expireTime;
}
