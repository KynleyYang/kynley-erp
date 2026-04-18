package com.kynley.erp.domain;

import java.util.List;
import com.kynley.common.annotation.Excel;
import lombok.*;
import com.kynley.common.core.domain.BaseEntity;

/**
 * 销售对象 sale
 *
 * @author kynley
 * @date 2025-04-18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sale extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 销售ID */
    private String saleId;

    /** 订单总金额 */
    @Excel(name = "订单总金额")
    private Double totalAmount;

    /** 销售状态 */
    @Excel(name = "销售状态")
    private String status;

    /** 创建人ID */
    @Excel(name = "创建人ID")
    private Long userId;

    /** 销售明细信息 */
    private List<SaleItems> saleItemsList;

    //创建人用户名
    private String userName;


}
