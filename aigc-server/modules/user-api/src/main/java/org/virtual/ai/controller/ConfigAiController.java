package org.virtual.ai.controller;

import org.virtual.business.common.entity.ConfigAi;
import org.virtual.common.core.utils.SpringUtils;
import org.virtual.ai.service.impl.ConfigAiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config/ai")
public class ConfigAiController extends ConfigBaseController<ConfigAi> {
    ConfigAiController() {
        super(SpringUtils.getBean(ConfigAiService.class));
    }

}
