package org.virtual.ai.service;


import org.virtual.business.common.entity.ConfigBaseEntity;
import org.virtual.common.mybatis.core.page.PageQuery;
import org.virtual.common.mybatis.core.page.TableDataInfo;

public interface ConfigBaseService<C extends ConfigBaseEntity> {

    TableDataInfo<C> info(C bo, PageQuery pageQuery);

    C detail(C bo);

    C createConfig(C request);

    void updateConfig(C request);
    void delete(C request);

}
