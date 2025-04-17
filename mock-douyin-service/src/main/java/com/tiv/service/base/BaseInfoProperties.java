package com.tiv.service.base;

import com.github.pagehelper.PageInfo;
import com.tiv.common.result.PagedResult;

import java.util.List;

public class BaseInfoProperties {

    public <T> PagedResult<T> buildPagedResult(List<T> list, Integer page) {
        PageInfo<T> pageList = new PageInfo<>(list);
        PagedResult<T> pagedResult = new PagedResult<>();
        pagedResult.setRows(list);
        pagedResult.setPage(page);
        pagedResult.setRecords(pageList.getTotal());
        pagedResult.setTotal(pageList.getPages());
        return pagedResult;
    }
}
