package org.virtual.ai.controller;

import cn.hutool.core.lang.Pair;
import lombok.RequiredArgsConstructor;
import org.virtual.business.common.entity.ConfigSite;
import org.virtual.business.common.entity.MediaTaskGroup;
import org.virtual.common.core.domain.R;
import org.virtual.common.mybatis.core.page.PageQuery;
import org.virtual.common.mybatis.core.page.TableDataInfo;
import org.virtual.ai.domain.bo.CreateMediaTaskGroupRequest;
import org.virtual.ai.service.MediaTaskGroupService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/task-groups")
public class MediaController {

    private final MediaTaskGroupService articleService;

    @GetMapping("/list")
    public TableDataInfo<MediaTaskGroup> list(MediaTaskGroup bo, PageQuery pageQuery) {
        return articleService.info(bo, pageQuery);
    }

    @GetMapping("/detail")
    public R<Map<String, Object>> detail(MediaTaskGroup bo) {
        return R.ok(articleService.detail(bo));
    }

    @PostMapping("/create")
    public R<MediaTaskGroup> create(@RequestBody CreateMediaTaskGroupRequest request) {
        return R.ok(articleService.create(request));
    }

}
