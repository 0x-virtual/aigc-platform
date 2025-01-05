package org.virtual.ai.domain.bo;

import org.virtual.business.common.entity.User;
import org.virtual.common.mybatis.core.domain.BaseEntity;
import org.virtual.common.core.validate.AddGroup;
import org.virtual.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 用户业务对象 user
 *

 * @date 2024-12-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = User.class, reverseConvertGenerate = false)
public class UserBo extends BaseEntity {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 头像
     */
    @NotBlank(message = "头像不能为空", groups = { AddGroup.class, EditGroup.class })
    private String avatar;

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String nickName;

    /**
     * 用户邮箱
     */
    @NotBlank(message = "用户邮箱不能为空", groups = { AddGroup.class, EditGroup.class })
    private String email;

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String phone;


}
