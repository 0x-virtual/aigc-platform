package org.virtual.ai.controller;

import org.virtual.business.common.entity.ConfigBaseEntity;
import org.virtual.common.core.domain.R;
import org.virtual.common.mybatis.core.page.PageQuery;
import org.virtual.common.mybatis.core.page.TableDataInfo;
import org.virtual.ai.service.ConfigBaseService;
import org.springframework.web.bind.annotation.*;

public class ConfigBaseController<C extends ConfigBaseEntity> {
    private final ConfigBaseService<C> service;

    public ConfigBaseController(ConfigBaseService<C> service) {
        this.service = service;
    }

    @GetMapping("/detail")
    public R<C> detail(C bo) {
        C detail = service.detail(bo);
        return R.ok(detail);
    }

    @GetMapping("/configs")
    public TableDataInfo<C> info(C bo, PageQuery pageQuery) {
        return service.info(bo, pageQuery);
    }

    @PostMapping("/configs")
    public R<C> createConfig(@RequestBody C request) {
        C config = service.createConfig(request);
        return R.ok(config);
    }

    @DeleteMapping("/configs" )
    public R delete(@RequestBody C request) {
        service.delete(request);
        return R.ok();
    }

    @PatchMapping("/configs")
    public R updateConfig(@RequestBody C request) {
        service.updateConfig(request);
        return R.ok();
    }

}
