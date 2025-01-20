package com.example.auth.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "系统信息")
public class SystemInfoVO {
    
    private String title;
    
    private String description;
    
    private String version;
    
    private String copyright;

    private String sysBelone;
}