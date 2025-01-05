package org.virtual.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.virtual.business.common.entity.ConfigBaseEntity;
import org.virtual.common.mybatis.core.page.PageQuery;
import org.virtual.common.mybatis.core.page.TableDataInfo;
import org.virtual.ai.mapper.ConfigBaseMapper;

import java.util.Map;


public class ConfigBaseService<C extends ConfigBaseEntity, M extends ConfigBaseMapper<C>> implements org.virtual.ai.service.ConfigBaseService<C> {

    private final ConfigBaseMapper<C> configMapper;

    public ConfigBaseService(ConfigBaseMapper<C> configAiMapper) {
        this.configMapper = configAiMapper;
    }

    private LambdaQueryWrapper<C> buildQueryWrapper(C request) {
        Map<String, Object> params = request.getParams();
        LambdaQueryWrapper<C> lqw = Wrappers.lambdaQuery();
        //lqw.like(StringUtils.isNotBlank(bo.getTestKey()), TestDemo::getTestKey, bo.getTestKey());
        //lqw.eq(StringUtils.isNotBlank(bo.getValue()), TestDemo::getValue, bo.getValue());
        //lqw.between(params.get("beginCreateTime") != null && params.get("endCreateTime") != null,
        //    TestDemo::getCreateTime, params.get("beginCreateTime"), params.get("endCreateTime"));
        //lqw.orderByAsc(TestDemo::getId);
        return lqw;
    }


    @Override
    public C detail(C bo) {
        LambdaQueryWrapper<C> lqw = buildQueryWrapper(bo);
        C result = configMapper.selectById(bo.getId());
        return result;
    }


    @Override
    public C createConfig(C entity) {
        configMapper.insert(entity);
        return entity;
    }

    @Override
    public void updateConfig(C request) {
        configMapper.updateById(request);
    }

    @Override
    public void delete(C request) {
        configMapper.deleteById(request);
    }

    @Override
    public TableDataInfo<C> info(C bo, PageQuery pageQuery) {
        LambdaQueryWrapper<C> lqw = buildQueryWrapper(bo);
        Page<C> result = configMapper.selectPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

}
