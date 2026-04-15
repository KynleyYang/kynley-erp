package com.kynley.erp.domain;

import com.kynley.common.annotation.Excel;
import com.kynley.common.core.domain.TreeEntity;
import lombok.*;
import com.kynley.common.core.domain.BaseEntity;

/**
 * 商品分类对象 sort
 *
 * @author kynley
 * @date 2026-04-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sort extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** 分类ID */
    private Long sortId;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String classifyName;

    //父ID
    private Long parentId;
}
