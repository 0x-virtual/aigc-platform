package org.virtual.ai.domain.bo;

import lombok.Data;


@Data
public class CreateMediaTaskGroupRequest {

    private Long siteId;

    private Long promptId;

    private String titles;

}
