package com.tiv.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VlogBO {
    private String id;
    private String vloggerId;
    private String url;
    private String cover;
    private Integer width;
    private Integer height;
    private Integer likeCounts;
    private Integer commentCounts;

}
