package com.example.auth.controller;

import com.example.auth.model.vo.SystemInfoVO;
import com.example.common.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "系统信息")
@CrossOrigin(originPatterns = "http://localhost:[*]", allowCredentials = "true")
@RestController
@RequestMapping("/auth/system")
public class SystemController {

    @Value("${system.info.title}")
    private String title;
    
    @Value("${system.info.description}")
    private String description;
    
    @Value("${system.info.version}")
    private String version;
    
    @Value("${system.info.copyright}")
    private String copyright;
    
    @Value("${system.info.sys-belone}")
    private String sysBelone;

    @Operation(summary = "获取系统信息")
    @GetMapping("/info")
    public R<SystemInfoVO> getSystemInfo() {
        SystemInfoVO systemInfo = new SystemInfoVO();
        systemInfo.setTitle(title);
        systemInfo.setDescription(description);
        systemInfo.setVersion(version);
        systemInfo.setCopyright(copyright);
        systemInfo.setSysBelone(sysBelone);
        
        return R.ok(systemInfo);
    }
} 