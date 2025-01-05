package org.virtual.business.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.virtual.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("media_task")
public class MediaTask extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    private String title;

    private Integer status;

    private Long siteId;
    private Integer executeStatus;


}
