package com.kynley.erp.service.impl;

import java.util.List;
import com.kynley.common.utils.DateUtils;
import com.kynley.common.utils.uuid.IdUtils;
import com.kynley.erp.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.kynley.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.kynley.erp.domain.SaleItems;
import com.kynley.erp.mapper.SaleMapper;
import com.kynley.erp.domain.Sale;
import com.kynley.erp.service.ISaleService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

import static com.kynley.common.utils.SecurityUtils.getUserId;

/**
 * 销售Service业务层处理
 *
 * @author kynley
 * @date 2026-04-18
 */
@Service
public class SaleServiceImpl implements ISaleService {
    @Autowired
    private SaleMapper saleMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private IGoodsService goodsService;

    /**
     * 查询销售
     *
     * @param saleId 销售主键
     * @return 销售
     */
    @Override
    public Sale selectSaleBySaleId(String saleId) {
        return saleMapper.selectSaleBySaleId(saleId);
    }

    /**
     * 查询销售列表
     *
     * @param sale 销售
     * @return 销售
     */
    @Override
    public List<Sale> selectSaleList(Sale sale) {
        return saleMapper.selectSaleList(sale);
    }

    /**
     * 新增销售
     *
     * @param sale 销售
     * @return 结果
     */
    @Transactional
    @Override
    public int insertSale(Sale sale) {
        sale.setCreateTime(DateUtils.getNowDate());
        sale.setSaleId(IdUtils.fastSimpleUUID());
        sale.setUserId(getUserId());

        //获取提交的销售明细信息
        List<SaleItems> saleItemsList = sale.getSaleItemsList();
        //初始化总金额为0
        double totalSum = 0.0;
        //循环遍历拿到明细中的每一条信息
        for (SaleItems saleItems : saleItemsList) {
            //拿到每一个商品ID
            String goodsId = saleItems.getGoodsId();
            //根据ID查询每个商品的销售价
            Double salesPrice = goodsService.selectGoodsByGoodsId(goodsId).getSalesPrice();
            //累加每个明细的金额
            totalSum += salesPrice * saleItems.getTotal();
            //将总金额插入至对象中
            sale.setTotalAmount(totalSum);
        }

        int rows = saleMapper.insertSale(sale);
        insertSaleItems(sale);
        return rows;
    }

    /**
     * 批量新增销售
     *
     * @param sales 销售List
     * @return 结果
     */
    @Override
    public int batchInsertSale(List<Sale> sales) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(sales)) {
            try {
                for (int i = 0; i < sales.size(); i++) {
                    int row = saleMapper.insertSale(sales.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i > 0 && i % 100 == 0) || i == sales.size() - 1;
                    if (bool) {
                        sqlSession.commit();
                        sqlSession.clearCache();
                    }
                    count = i + 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 没有提交的数据可以回滚
                sqlSession.rollback();
            } finally {
                sqlSession.close();
                return count;
            }
        }
        return count;
    }

    /**
     * 修改销售
     *
     * @param sale 销售
     * @return 结果
     */
    @Transactional
    @Override
    public int updateSale(Sale sale) {
        saleMapper.deleteSaleItemsBySaleId(sale.getSaleId());
        //获取提交的销售明细信息
        List<SaleItems> saleItemsList = sale.getSaleItemsList();
        //初始化总金额为0
        double totalSum = 0.0;
        //循环遍历拿到明细中的每一条信息
        for (SaleItems saleItems : saleItemsList) {
            //拿到每一个商品ID
            String goodsId = saleItems.getGoodsId();
            //根据ID查询每个商品的销售价
            Double salesPrice = goodsService.selectGoodsByGoodsId(goodsId).getSalesPrice();
            //累加每个明细的金额
            totalSum += salesPrice * saleItems.getTotal();
            //将总金额插入至对象中
            sale.setTotalAmount(totalSum);
        }
        insertSaleItems(sale);
        return saleMapper.updateSale(sale);
    }

    /**
     * 批量删除销售
     *
     * @param saleIds 需要删除的销售主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteSaleBySaleIds(String[] saleIds) {
        saleMapper.deleteSaleItemsBySaleIds(saleIds);
        return saleMapper.deleteSaleBySaleIds(saleIds);
    }

    /**
     * 删除销售信息
     *
     * @param saleId 销售主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteSaleBySaleId(String saleId) {
        saleMapper.deleteSaleItemsBySaleId(saleId);
        return saleMapper.deleteSaleBySaleId(saleId);
    }

    /**
     * 新增销售明细信息
     *
     * @param sale 销售对象
     */
    public void insertSaleItems(Sale sale) {
        List<SaleItems> saleItemsList = sale.getSaleItemsList();
        String saleId = sale.getSaleId();
        if (StringUtils.isNotNull(saleItemsList)) {
            List<SaleItems> list = new ArrayList<SaleItems>();
            for (SaleItems saleItems : saleItemsList) {
                saleItems.setSaleId(saleId);
                list.add(saleItems);
            }
            if (list.size() > 0) {
                saleMapper.batchSaleItems(list);
            }
        }
    }
}