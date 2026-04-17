package com.kynley.erp.service;

import java.util.List;
import com.kynley.erp.domain.Buy;

/**
 * 采购Service接口
 *
 * @author kynley
 * @date 2026-04-16
 */
public interface IBuyService
{
    /**
     * 查询采购
     *
     * @param buyId 采购主键
     * @return 采购
     */
    public Buy selectBuyByBuyId(String buyId);

    /**
     * 查询采购列表
     *
     * @param buy 采购
     * @return 采购集合
     */
    public List<Buy> selectBuyList(Buy buy);

    /**
     * 新增采购
     *
     * @param buy 采购
     * @return 结果
     */
    public int insertBuy(Buy buy);

    /**
     * 批量新增采购
     *
     * @param buys 采购List
     * @return 结果
     */
    public int batchInsertBuy(List<Buy> buys);

    /**
     * 修改采购
     *
     * @param buy 采购
     * @return 结果
     */
    public int updateBuy(Buy buy);

    /**
     * 批量删除采购
     *
     * @param buyIds 需要删除的采购主键集合
     * @return 结果
     */
    public int deleteBuyByBuyIds(String[] buyIds);

    /**
     * 删除采购信息
     *
     * @param buyId 采购主键
     * @return 结果
     */
    public int deleteBuyByBuyId(String buyId);
}
