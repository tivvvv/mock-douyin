package com.tiv.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginBO {

    @NotBlank(message = "邮箱不能为空")
    String email;

    @NotBlank(message = "验证码不能为空")
    @Length(min = 6, max = 6, message = "验证码格式不正确")
    String code;
}
