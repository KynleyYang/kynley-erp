package com.kynley.erp.service;

import java.util.List;
import com.kynley.erp.domain.Sale;

/**
 * 销售Service接口
 *
 * @author kynley
 * @date 2026-04-18
 */
public interface ISaleService
{
    /**
     * 查询销售
     *
     * @param saleId 销售主键
     * @return 销售
     */
    public Sale selectSaleBySaleId(String saleId);

    /**
     * 查询销售列表
     *
     * @param sale 销售
     * @return 销售集合
     */
    public List<Sale> selectSaleList(Sale sale);

    /**
     * 新增销售
     *
     * @param sale 销售
     * @return 结果
     */
    public int insertSale(Sale sale);

    /**
     * 批量新增销售
     *
     * @param sales 销售List
     * @return 结果
     */
    public int batchInsertSale(List<Sale> sales);

    /**
     * 修改销售
     *
     * @param sale 销售
     * @return 结果
     */
    public int updateSale(Sale sale);

    /**
     * 批量删除销售
     *
     * @param saleIds 需要删除的销售主键集合
     * @return 结果
     */
    public int deleteSaleBySaleIds(String[] saleIds);

    /**
     * 删除销售信息
     *
     * @param saleId 销售主键
     * @return 结果
     */
    public int deleteSaleBySaleId(String saleId);
}
