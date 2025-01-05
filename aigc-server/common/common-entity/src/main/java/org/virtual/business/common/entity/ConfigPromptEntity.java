package org.virtual.business.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("config_prompt")
public class ConfigPromptEntity extends ConfigBaseEntity {

    private String remark;
    private String prompt;

}
