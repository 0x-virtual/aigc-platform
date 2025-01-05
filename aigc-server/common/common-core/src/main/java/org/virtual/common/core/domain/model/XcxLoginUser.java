package org.virtual.common.core.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * 小程序登录用户身份权限
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class XcxLoginUser extends LoginUser {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * openid
     */
    private String openid;

}
