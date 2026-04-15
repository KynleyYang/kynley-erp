package com.kynley.erp.service;

import java.util.List;
import com.kynley.erp.domain.Goods;

/**
 * 商品Service接口
 *
 * @author kynley
 * @date 2026-04-15
 */
public interface IGoodsService
{
    /**
     * 查询商品
     *
     * @param goodsId 商品主键
     * @return 商品
     */
    public Goods selectGoodsByGoodsId(String goodsId);

    /**
     * 查询商品列表
     *
     * @param goods 商品
     * @return 商品集合
     */
    public List<Goods> selectGoodsList(Goods goods);

    /**
     * 新增商品
     *
     * @param goods 商品
     * @return 结果
     */
    public int insertGoods(Goods goods);

    /**
     * 批量新增商品
     *
     * @param goodss 商品List
     * @return 结果
     */
    public int batchInsertGoods(List<Goods> goodss);

    /**
     * 修改商品
     *
     * @param goods 商品
     * @return 结果
     */
    public int updateGoods(Goods goods);

    /**
     * 批量删除商品
     *
     * @param goodsIds 需要删除的商品主键集合
     * @return 结果
     */
    public int deleteGoodsByGoodsIds(String[] goodsIds);

    /**
     * 删除商品信息
     *
     * @param goodsId 商品主键
     * @return 结果
     */
    public int deleteGoodsByGoodsId(String goodsId);
}
