package com.kynley.erp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsToCount {
    private Double goodsCount; //总商品数
    private Double normalCount; //库存正常数
    private Double warningCount; //库存预警数
}