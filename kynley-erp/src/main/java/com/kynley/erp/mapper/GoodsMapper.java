package com.kynley.erp.mapper;

import java.util.List;
import com.kynley.erp.domain.Goods;
import com.kynley.erp.domain.GoodsToCount;

/**
 * 商品Mapper接口
 * 
 * @author kynley
 * @date 2026-04-15
 */
public interface GoodsMapper 
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
     * 修改商品
     * 
     * @param goods 商品
     * @return 结果
     */
    public int updateGoods(Goods goods);

    /**
     * 删除商品
     * 
     * @param goodsId 商品主键
     * @return 结果
     */
    public int deleteGoodsByGoodsId(String goodsId);

    /**
     * 批量删除商品
     * 
     * @param goodsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsByGoodsIds(String[] goodsIds);

    /**
     * 查询总商品数,库存正常数,库存预警数
     * @return
     */
    GoodsToCount selectGoodsOrNormalOrWarningToCount();

    /**
     * 查询库存预警商品列表
     * @param goods
     * @return
     */
    List<Goods> selectWarningGoodsList(Goods goods);
}
