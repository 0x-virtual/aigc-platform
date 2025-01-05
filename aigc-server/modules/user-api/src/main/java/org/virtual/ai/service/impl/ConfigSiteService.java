package org.virtual.ai.service.impl;

import org.virtual.business.common.entity.ConfigSite;
import org.virtual.common.core.utils.SpringUtils;
import org.virtual.ai.mapper.ConfigSiteMapper;
import org.springframework.stereotype.Service;

@Service
public class ConfigSiteService extends ConfigBaseService<ConfigSite, ConfigSiteMapper> {

    public ConfigSiteService() {
        super(SpringUtils.getBean(ConfigSiteMapper.class));
    }
}
