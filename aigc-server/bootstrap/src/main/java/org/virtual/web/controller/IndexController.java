package org.virtual.web.controller;

import org.virtual.common.core.config.ServerAppConfig;
import org.virtual.common.core.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class IndexController {

    private final ServerAppConfig appConfig;


    @GetMapping("/")
    public String index() {
        return StringUtils.format("欢迎使用 AIGC");
    }
}
