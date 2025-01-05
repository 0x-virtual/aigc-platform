package org.virtual.ai.service.impl;

import org.virtual.business.common.entity.ConfigPromptEntity;
import org.virtual.common.core.utils.SpringUtils;
import org.virtual.ai.mapper.ConfigPromptMapper;
import org.springframework.stereotype.Service;

@Service
public class ConfigPromptService extends ConfigBaseService<ConfigPromptEntity, ConfigPromptMapper> {

    public ConfigPromptService() {
        super(SpringUtils.getBean(ConfigPromptMapper.class));
    }
}
