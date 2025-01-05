package org.virtual.business.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.virtual.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class ConfigBaseEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    private Integer enabled;

}
