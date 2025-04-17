package com.tiv.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VlogBO {

    private String id;

    @NotBlank(message = "作者id不能为空")
    private String vloggerId;

    @NotBlank(message = "短视频地址不能为空")
    private String url;

    @NotBlank(message = "封面不能为空")
    private String cover;

    @NotBlank(message = "宽度不能为空")
    private Integer width;

    @NotBlank(message = "高度不能为空")
    private Integer height;

    private Integer likeCounts;
    private Integer commentCounts;

}
