package org.virtual.ai.domain.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class CreateConfigAiRequest {

    @NotBlank(message = "key 不能为空")
    private String apiKey;
    private Long id;

    @NotBlank(message = "model 不能为空")
    private String model;

    @NotBlank(message = "provider 不能为空")
    private String provider;

    @NotBlank(message = "baseUrl 不能为空")
    private String baseUrl;

}
