package com.kynley.erp.service.impl;

import java.util.List;

import com.kynley.common.utils.uuid.IdUtils;
import com.kynley.erp.domain.GoodsToCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kynley.erp.mapper.GoodsMapper;
import com.kynley.erp.domain.Goods;
import com.kynley.erp.service.IGoodsService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

/**
 * 商品Service业务层处理
 *
 * @author kynley
 * @date 2026-04-15
 */
@Service
public class GoodsServiceImpl implements IGoodsService
{
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询商品
     *
     * @param goodsId 商品主键
     * @return 商品
     */
    @Override
    public Goods selectGoodsByGoodsId(String goodsId)
    {
        return goodsMapper.selectGoodsByGoodsId(goodsId);
    }

    /**
     * 查询商品列表
     *
     * @param goods 商品
     * @return 商品
     */
    @Override
    public List<Goods> selectGoodsList(Goods goods)
    {
        return goodsMapper.selectGoodsList(goods);
    }

    /**
     * 新增商品
     *
     * @param goods 商品
     * @return 结果
     */
    @Override
    public int insertGoods(Goods goods)
    {
        goods.setGoodsId(IdUtils.fastSimpleUUID());
        return goodsMapper.insertGoods(goods);
    }

    /**
     * 批量新增商品
     *
     * @param goodss 商品List
     * @return 结果
     */
    @Override
    public int batchInsertGoods(List<Goods> goodss)
    {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(goodss)) {
            try {
                for (int i = 0; i < goodss.size(); i++) {
                    int row = goodsMapper.insertGoods(goodss.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i >0 && i%100 == 0) || i == goodss.size() - 1;
                    if (bool){
                        sqlSession.commit();
                        sqlSession.clearCache();
                    }
                    count = i + 1;
                }
            }catch (Exception e){
                e.printStackTrace();
                // 没有提交的数据可以回滚
                sqlSession.rollback();
            }finally {
                sqlSession.close();
                return count;
            }
        }
        return count;
    }

    /**
     * 修改商品
     *
     * @param goods 商品
     * @return 结果
     */
    @Override
    public int updateGoods(Goods goods)
    {
        return goodsMapper.updateGoods(goods);
    }

    /**
     * 批量删除商品
     *
     * @param goodsIds 需要删除的商品主键
     * @return 结果
     */
    @Override
    public int deleteGoodsByGoodsIds(String[] goodsIds)
    {
        return goodsMapper.deleteGoodsByGoodsIds(goodsIds);
    }

    /**
     * 删除商品信息
     *
     * @param goodsId 商品主键
     * @return 结果
     */
    @Override
    public int deleteGoodsByGoodsId(String goodsId)
    {
        return goodsMapper.deleteGoodsByGoodsId(goodsId);
    }

    /**
     * 查询总商品数,库存正常数,库存预警数
     * @return
     */
    @Override
    public GoodsToCount selectGoodsOrNormalOrWarningToCount() {
        return goodsMapper.selectGoodsOrNormalOrWarningToCount();
    }

    /**
     * 查询库存预警商品列表
     * @param goods
     * @return
     */
    @Override
    public List<Goods> selectWarningGoodsList(Goods goods) {
        return goodsMapper.selectWarningGoodsList(goods);
    }
}
