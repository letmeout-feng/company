package com.internal.common.request;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页参数
 *
 * @author
 * @date 2020年02月14日16:19:36
 */
@Data
@NoArgsConstructor
@Schema(name = "PageParams", description = "分页参数")
public class PageParams<T> {

    @NotNull(message = "查询对象model不能为空")
    @Schema(description = "查询参数", required = true)
    private T model;

    @Schema(description = "页面大小", example = "10")
    private int size = 10;

    @Schema(description = "当前页", example = "1")
    private int current = 1;

    @Schema(description = "排序,默认createTime", allowableValues = "id,createTime,updateTime", example = "id")
    private String sort;

    @Schema(description = "排序规则, 默认descending", allowableValues = "descending,ascending", example = "descending")
    private String order;

    public PageParams(int current, int size) {
        this.size = size;
        this.current = current;
    }

    /**
     * 构建分页对象
     *
     * @return 分页对象
     */
    @JsonIgnore
    public <E> IPage<E> buildPage() {
        PageParams params = this;
        return new Page(params.getCurrent(), params.getSize());
    }

}
