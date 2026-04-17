package com.kynley.erp.domain;

import java.util.List;
import com.kynley.common.annotation.Excel;
import lombok.*;
import com.kynley.common.core.domain.BaseEntity;

/**
 * 采购对象 buy
 *
 * @author kynley
 * @date 2026-04-16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Buy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 采购ID */
    private String buyId;

    /** 订单总金额 */
    @Excel(name = "订单总金额")
    private Double totalAmount;

    /** 采购状态 */
    @Excel(name = "采购状态")
    private String status;

    /** 供应商 */
    @Excel(name = "供应商")
    private String supplierId;

    /** 创建人ID */
    @Excel(name = "创建人ID")
    private Long userId;

    /** 采购明细信息 */
    private List<BuyItems> buyItemsList;

    //创建人用户名
    private String userName;

    //供应商名称
    private String supplierName;
}
