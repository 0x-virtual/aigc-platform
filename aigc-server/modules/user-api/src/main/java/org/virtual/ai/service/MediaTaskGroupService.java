package org.virtual.ai.service;


import cn.hutool.core.lang.Pair;
import org.virtual.business.common.entity.ConfigSite;
import org.virtual.business.common.entity.MediaTaskGroup;
import org.virtual.common.mybatis.core.page.PageQuery;
import org.virtual.common.mybatis.core.page.TableDataInfo;
import org.virtual.ai.domain.bo.CreateMediaTaskGroupRequest;

import java.util.Map;

public interface MediaTaskGroupService {

    TableDataInfo<MediaTaskGroup> info(MediaTaskGroup bo, PageQuery pageQuery);

    Map<String, Object> detail(MediaTaskGroup bo);

    MediaTaskGroup create(CreateMediaTaskGroupRequest entity);


}
