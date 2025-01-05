package org.virtual.business.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.virtual.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("media_task_group")
public class MediaTaskGroup extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    private String prompt;
    private String titles;

    private Integer status;

    private Long publishSiteId;
    private Integer taskTotal;
    private Integer taskSuccessCount;
    private Integer taskFailureCount;


}
