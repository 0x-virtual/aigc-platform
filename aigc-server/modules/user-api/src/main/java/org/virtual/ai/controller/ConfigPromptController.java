package org.virtual.ai.controller;

import org.virtual.business.common.entity.ConfigPromptEntity;
import org.virtual.common.core.utils.SpringUtils;
import org.virtual.ai.service.impl.ConfigPromptService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config/prompt")
public class ConfigPromptController extends ConfigBaseController<ConfigPromptEntity> {

    ConfigPromptController() {
        super(SpringUtils.getBean(ConfigPromptService.class));
    }

}
