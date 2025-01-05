package org.virtual.ai.domain.vo;

import org.virtual.business.common.entity.User;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 用户视图对象 user
 *

 * @date 2024-12-14
 */
@Data
@AutoMapper(target = User.class)
public class UserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;


}
