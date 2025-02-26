package com.tiv.common.result;

import com.tiv.common.enums.ResponseStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 自定义响应结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraceJSONResult {

    // 响应状态码
    private Integer code;

    // 是否成功
    private Boolean success;

    // 响应信息
    private String msg;

    // 响应数据
    private Object data;

    /**
     * 成功返回,带有数据的
     *
     * @param data
     * @return
     */
    public static GraceJSONResult ok(Object data) {
        return new GraceJSONResult(data);
    }

    /**
     * 成功返回,不带有数据的
     *
     * @return
     */
    public static GraceJSONResult ok() {
        return new GraceJSONResult(ResponseStatusEnum.SUCCESS);
    }

    /**
     * 错误返回
     *
     * @return
     */
    public static GraceJSONResult error() {
        return new GraceJSONResult(ResponseStatusEnum.FAILED);
    }

    /**
     * 错误返回,带有错误信息
     *
     * @param msg
     * @return
     */
    public static GraceJSONResult errorMsg(String msg) {
        return new GraceJSONResult(ResponseStatusEnum.FAILED, msg);
    }

    /**
     * 错误返回,map中包含了多条错误信息,可以用于表单验证,把错误统一的全部返回出去
     *
     * @param map
     * @return
     */
    public static GraceJSONResult errorMap(Map<Object, Object> map) {
        return new GraceJSONResult(ResponseStatusEnum.FAILED, map);
    }

    /**
     * 错误返回,token异常
     *
     * @return
     */
    public static GraceJSONResult errorTicket() {
        return new GraceJSONResult(ResponseStatusEnum.TICKET_INVALID);
    }

    /**
     * 自定义错误返回
     *
     * @param responseStatus
     * @return
     */
    public static GraceJSONResult errorCustom(ResponseStatusEnum responseStatus) {
        return new GraceJSONResult(responseStatus);
    }

    /**
     * 异常返回
     *
     * @param responseStatus
     * @return
     */
    public static GraceJSONResult exception(ResponseStatusEnum responseStatus) {
        return new GraceJSONResult(responseStatus);
    }

    public GraceJSONResult(ResponseStatusEnum responseStatus) {
        this.code = responseStatus.getCode();
        this.msg = responseStatus.getMsg();
        this.success = responseStatus.getSuccess();
    }

    public GraceJSONResult(ResponseStatusEnum responseStatus, Object data) {
        this.code = responseStatus.getCode();
        this.msg = responseStatus.getMsg();
        this.success = responseStatus.getSuccess();
        this.data = data;
    }

    public GraceJSONResult(ResponseStatusEnum responseStatus, String msg) {
        this.code = responseStatus.getCode();
        this.msg = msg;
        this.success = responseStatus.getSuccess();
    }

    public GraceJSONResult(Object data) {
        this.code = ResponseStatusEnum.SUCCESS.getCode();
        this.msg = ResponseStatusEnum.SUCCESS.getMsg();
        this.success = ResponseStatusEnum.SUCCESS.getSuccess();
        this.data = data;
    }
}
