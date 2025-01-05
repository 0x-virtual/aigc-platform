package org.virtual.ai.controller;

import org.virtual.business.common.entity.ConfigSite;
import org.virtual.common.core.utils.SpringUtils;
import org.virtual.ai.service.impl.ConfigSiteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config/site")
public class ConfigSiteController extends ConfigBaseController<ConfigSite> {

    ConfigSiteController() {
        super(SpringUtils.getBean(ConfigSiteService.class));
    }

}
