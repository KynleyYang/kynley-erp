package com.kynley.erp.service;

import java.util.List;
import com.kynley.erp.domain.Supplier;

/**
 * 供应商Service接口
 *
 * @author kynley
 * @date 2026-03-13
 */
public interface ISupplierService
{
    /**
     * 查询供应商
     *
     * @param supplierId 供应商主键
     * @return 供应商
     */
    public Supplier selectSupplierBySupplierId(String supplierId);

    /**
     * 查询供应商列表
     *
     * @param supplier 供应商
     * @return 供应商集合
     */
    public List<Supplier> selectSupplierList(Supplier supplier);

    /**
     * 新增供应商
     *
     * @param supplier 供应商
     * @return 结果
     */
    public int insertSupplier(Supplier supplier);

    /**
     * 批量新增供应商
     *
     * @param suppliers 供应商List
     * @return 结果
     */
    public int batchInsertSupplier(List<Supplier> suppliers);

    /**
     * 修改供应商
     *
     * @param supplier 供应商
     * @return 结果
     */
    public int updateSupplier(Supplier supplier);

    /**
     * 批量删除供应商
     *
     * @param supplierIds 需要删除的供应商主键集合
     * @return 结果
     */
    public int deleteSupplierBySupplierIds(String[] supplierIds);

    /**
     * 删除供应商信息
     *
     * @param supplierId 供应商主键
     * @return 结果
     */
    public int deleteSupplierBySupplierId(String supplierId);
}
