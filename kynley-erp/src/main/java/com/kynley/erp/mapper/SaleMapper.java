package com.kynley.erp.mapper;

import java.util.List;
import com.kynley.erp.domain.Sale;
import com.kynley.erp.domain.SaleItems;

/**
 * 销售Mapper接口
 * 
 * @author kynley
 * @date 2026-04-18
 */
public interface SaleMapper 
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
     * 修改销售
     * 
     * @param sale 销售
     * @return 结果
     */
    public int updateSale(Sale sale);

    /**
     * 删除销售
     * 
     * @param saleId 销售主键
     * @return 结果
     */
    public int deleteSaleBySaleId(String saleId);

    /**
     * 批量删除销售
     * 
     * @param saleIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSaleBySaleIds(String[] saleIds);

    /**
     * 批量删除销售明细
     * 
     * @param saleIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSaleItemsBySaleIds(String[] saleIds);
    
    /**
     * 批量新增销售明细
     * 
     * @param saleItemsList 销售明细列表
     * @return 结果
     */
    public int batchSaleItems(List<SaleItems> saleItemsList);
    

    /**
     * 通过销售主键删除销售明细信息
     * 
     * @param saleId 销售ID
     * @return 结果
     */
    public int deleteSaleItemsBySaleId(String saleId);
}
