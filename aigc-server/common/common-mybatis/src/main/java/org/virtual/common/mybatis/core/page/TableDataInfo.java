package org.virtual.common.mybatis.core.page;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表格分页数据对象
 *

 */
@Data
@NoArgsConstructor
public class TableDataInfo<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 列表数据
     */
    private List<T> value;
    private Object data;
    private Object reference;

    /**
     * 消息状态码
     */
    private int code;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 分页
     *
     * @param list  列表数据
     * @param total 总记录数
     */
    public TableDataInfo(List<T> list, long total) {
        this.value = list;
        this.total = total;
    }

    /**
     * 根据分页对象构建表格分页数据对象
     */
    public static <T> TableDataInfo<T> build(IPage<T> page) {
        TableDataInfo<T> rspData = new TableDataInfo<>();
        rspData.setCode(HttpStatus.HTTP_OK);
        rspData.setMsg("查询成功");
        Map map = new HashMap();
        map.put("total", page.getTotal());
        map.put("value", page.getRecords());
        rspData.setData(map);
        return rspData;
    }

    /**
     * 根据数据列表构建表格分页数据对象
     */
    public static <T> TableDataInfo<T> build(List<T> list) {
        TableDataInfo<T> rspData = new TableDataInfo<>();
        rspData.setCode(HttpStatus.HTTP_OK);
        rspData.setMsg("查询成功");
        rspData.setValue(list);
        rspData.setTotal(list.size());
        return rspData;
    }

    /**
     * 构建表格分页数据对象
     */
    public static <T> TableDataInfo<T> build() {
        TableDataInfo<T> rspData = new TableDataInfo<>();
        rspData.setCode(HttpStatus.HTTP_OK);
        rspData.setMsg("查询成功");
        return rspData;
    }

}
