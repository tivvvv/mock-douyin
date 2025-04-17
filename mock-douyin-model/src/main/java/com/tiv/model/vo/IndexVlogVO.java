package com.tiv.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IndexVlogVO {
    private String vlogId;
    private String vloggerId;
    private String vloggerFace;
    private String vloggerName;
    private String content;
    private String url;
    private String cover;
    private Integer width;
    private Integer height;
    private Integer likeCounts;
    private Integer commentsCounts;
    private Integer isPrivate;
    private boolean isPlay = false;
}