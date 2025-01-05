package org.virtual.ai.service.impl;

import org.virtual.business.common.entity.ConfigAi;
import org.virtual.common.core.utils.SpringUtils;
import org.virtual.ai.mapper.ConfigAiMapper;
import org.springframework.stereotype.Service;

@Service
public class ConfigAiService extends ConfigBaseService<ConfigAi, ConfigAiMapper> {

    public ConfigAiService() {
        super(SpringUtils.getBean(ConfigAiMapper.class));
    }
}
