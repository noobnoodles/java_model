package com.example.auth.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BanDTO {
    private String userid;
    private Boolean status;
    
    /**
     * 封禁时长（单位：小时）
     * 为null时表示永久封禁
     */
    private Integer duration;
    
    /**
     * 封禁起始时间
     * 不传则默认为当前时间
     */
    private LocalDateTime startTime;
}
