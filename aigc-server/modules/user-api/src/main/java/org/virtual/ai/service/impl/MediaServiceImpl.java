package org.virtual.ai.service.impl;

import cn.hutool.core.lang.Pair;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.virtual.business.common.entity.ConfigPromptEntity;
import org.virtual.business.common.entity.ConfigSite;
import org.virtual.business.common.entity.MediaTask;
import org.virtual.business.common.entity.MediaTaskGroup;
import org.virtual.common.kafka.KafkaProducer;
import org.virtual.common.mybatis.core.page.PageQuery;
import org.virtual.common.mybatis.core.page.TableDataInfo;
import org.virtual.ai.domain.bo.CreateMediaTaskGroupRequest;
import org.virtual.ai.mapper.MediaTaskGroupMapper;
import org.virtual.ai.mapper.ConfigPromptMapper;
import org.virtual.ai.mapper.ConfigSiteMapper;
import org.virtual.ai.mapper.MediaTaskMapper;
import org.virtual.ai.service.MediaTaskGroupService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class MediaServiceImpl implements MediaTaskGroupService {

    private final MediaTaskGroupMapper taskGroupMapper;
    private final MediaTaskMapper mediaTaskMapper;
    private final ConfigPromptMapper configPromptMapper;
    private final ConfigSiteMapper configSiteMapper;
    private final KafkaProducer kafkaProducer;


    private LambdaQueryWrapper<MediaTaskGroup> buildQueryWrapper(MediaTaskGroup bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MediaTaskGroup> lqw = Wrappers.lambdaQuery();
        return lqw;
    }

    @Override
    public TableDataInfo<MediaTaskGroup> info(MediaTaskGroup bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MediaTaskGroup> lqw = buildQueryWrapper(bo);
        Page<MediaTaskGroup> result = taskGroupMapper.selectPage(pageQuery.build(), lqw);
        TableDataInfo<MediaTaskGroup> tableData = TableDataInfo.build(result);
        Map map = new HashMap<>();
        if (result.getRecords().size() > 0) {
            LambdaQueryWrapper<ConfigSite> siteQuery = Wrappers.lambdaQuery();
            siteQuery.in(ConfigSite::getId, result.getRecords().stream().map(x -> x.getPublishSiteId()).collect(Collectors.toList()));
            List<ConfigSite> configSites = configSiteMapper.selectList(siteQuery);
            map.put("sites", configSites);
            tableData.setReference(map);
        }

        return tableData;
    }

    @Override
    public Map<String, Object> detail(MediaTaskGroup bo) {
        MediaTaskGroup result = taskGroupMapper.selectById(bo.getId());
        ConfigSite site = configSiteMapper.selectById(result.getPublishSiteId());
        var map = new HashMap<String, Object>();
        map.put("site", site);
        map.put("taskGroup", result);
        return map;
    }


    @Override
    public MediaTaskGroup create(CreateMediaTaskGroupRequest request) {


        ConfigPromptEntity configPromptEntity = configPromptMapper.selectById(request.getPromptId());
        ConfigSite configSite = configSiteMapper.selectById(request.getSiteId());
        MediaTaskGroup entity = new MediaTaskGroup();
        String[] titles = request.getTitles().split("\n");

        entity.setPrompt(configPromptEntity.getPrompt());
        entity.setStatus(0);
        entity.setTaskTotal(titles.length);
        entity.setTitles(request.getTitles());
        entity.setPublishSiteId(request.getSiteId());
        taskGroupMapper.insert(entity);


        List<MediaTask> mediaTasks = new ArrayList<>();
        for (String title : titles) {
            MediaTask mediaTask = new MediaTask();
            mediaTask.setTitle(title);
            mediaTask.setStatus(0);
            mediaTask.setSiteId(request.getSiteId());
            mediaTasks.add(mediaTask);
        }
        mediaTaskMapper.insertBatch(mediaTasks);

        sendKafka(mediaTasks, entity);
        return entity;
    }

    public void sendKafka(List<MediaTask> mediaTasks, MediaTaskGroup mediaTaskGroup) {
        for (MediaTask mediaTask : mediaTasks) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("task", mediaTask);
            jsonObject.put("group", mediaTaskGroup);
            kafkaProducer.sendTask(jsonObject);
        }
    }
}
