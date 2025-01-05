package org.virtual.common.core.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 短信登录对象
 *
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SmsLoginBody extends LoginBody {

    /**
     * 手机号
     */
    @NotBlank(message = "{user.phonenumber.not.blank}")
    private String phonenumber;

    /**
     * 短信code
     */
    @NotBlank(message = "{sms.code.not.blank}")
    private String smsCode;

}
