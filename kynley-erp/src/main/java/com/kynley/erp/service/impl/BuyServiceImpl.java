package com.kynley.erp.service.impl;

import java.util.List;

import com.kynley.common.utils.DateUtils;
import com.kynley.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.kynley.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.kynley.erp.domain.BuyItems;
import com.kynley.erp.mapper.BuyMapper;
import com.kynley.erp.domain.Buy;
import com.kynley.erp.service.IBuyService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

import static com.kynley.common.utils.SecurityUtils.getUserId;

/**
 * 采购Service业务层处理
 *
 * @author kynley
 * @date 2025-06-25
 */
@Service
public class BuyServiceImpl implements IBuyService {
    @Autowired
    private BuyMapper buyMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询采购
     *
     * @param buyId 采购主键
     * @return 采购
     */
    @Override
    public Buy selectBuyByBuyId(String buyId) {
        return buyMapper.selectBuyByBuyId(buyId);
    }

    /**
     * 查询采购列表
     *
     * @param buy 采购
     * @return 采购
     */
    @Override
    public List<Buy> selectBuyList(Buy buy) {
        return buyMapper.selectBuyList(buy);
    }

    /**
     * 新增采购
     *
     * @param buy 采购
     * @return 结果
     */
    @Transactional
    @Override
    public int insertBuy(Buy buy) {
        buy.setCreateTime(DateUtils.getNowDate());
        buy.setUserId(getUserId());

        //获取一个UUID
        String uuid = IdUtils.fastSimpleUUID();
        //截取前6位并且转换为大写
        String shortCode = uuid.substring(0, 6).toUpperCase();
        //添加前缀并插入
        buy.setBuyId("BUY-" + shortCode);

        //获取提交的采购明细信息
        List<BuyItems> buyItemsList = buy.getBuyItemsList();
        //初始化总金额为0
        double totalSum = 0.0;
        //循环遍历拿到明细中的每一条信息
        for (BuyItems buyItems : buyItemsList) {
            //累加每个明细的金额
            totalSum += buyItems.getTotal() * buyItems.getUnitPrice();
        }
        //将总金额插入至对象中
        buy.setTotalAmount(totalSum);

        int rows = buyMapper.insertBuy(buy);
        insertBuyItems(buy);
        return rows;
    }

    /**
     * 批量新增采购
     *
     * @param buys 采购List
     * @return 结果
     */
    @Override
    public int batchInsertBuy(List<Buy> buys) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(buys)) {
            try {
                for (int i = 0; i < buys.size(); i++) {
                    int row = buyMapper.insertBuy(buys.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i > 0 && i % 100 == 0) || i == buys.size() - 1;
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
     * 修改采购
     *
     * @param buy 采购
     * @return 结果
     */
    @Transactional
    @Override
    public int updateBuy(Buy buy) {
        buyMapper.deleteBuyItemsByBuyId(buy.getBuyId());

        //获取提交的采购明细信息
        List<BuyItems> buyItemsList = buy.getBuyItemsList();
        //初始化总金额为0
        double totalSum = 0.0;
        //循环遍历拿到明细中的每一条信息
        for (BuyItems buyItems : buyItemsList) {
            //累加每个明细的金额
            totalSum += buyItems.getTotal() * buyItems.getUnitPrice();
        }
        //将总金额插入至对象中
        buy.setTotalAmount(totalSum);

        insertBuyItems(buy);
        return buyMapper.updateBuy(buy);
    }

    /**
     * 批量删除采购
     *
     * @param buyIds 需要删除的采购主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteBuyByBuyIds(String[] buyIds) {
        buyMapper.deleteBuyItemsByBuyIds(buyIds);
        return buyMapper.deleteBuyByBuyIds(buyIds);
    }

    /**
     * 删除采购信息
     *
     * @param buyId 采购主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteBuyByBuyId(String buyId) {
        buyMapper.deleteBuyItemsByBuyId(buyId);
        return buyMapper.deleteBuyByBuyId(buyId);
    }

    /**
     * 新增采购明细信息
     *
     * @param buy 采购对象
     */
    public void insertBuyItems(Buy buy) {
        List<BuyItems> buyItemsList = buy.getBuyItemsList();
        String buyId = buy.getBuyId();
        if (StringUtils.isNotNull(buyItemsList)) {
            List<BuyItems> list = new ArrayList<BuyItems>();
            for (BuyItems buyItems : buyItemsList) {
                buyItems.setBuyId(buyId);
                list.add(buyItems);
            }
            if (list.size() > 0) {
                buyMapper.batchBuyItems(list);
            }
        }
    }
}
