package com.tiv.common.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PagedResult<T> {

    /**
     * 当前页数
     */
    private int page;

    /**
     * 总页数
     */
    private long total;

    /**
     * 总记录数
     */
    private long records;

    /**
     * 每行显示的内容
     */
    private List<T> rows;
}
