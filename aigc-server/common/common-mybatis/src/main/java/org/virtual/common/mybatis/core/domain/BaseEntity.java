package org.virtual.common.mybatis.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity基类
 */
@Data
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Date createdAt;
    private Date updatedAt;
    /**
     * 请求参数
     */
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();

}
