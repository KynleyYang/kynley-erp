package com.kynley.erp.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kynley.erp.mapper.SortMapper;
import com.kynley.erp.domain.Sort;
import com.kynley.erp.service.ISortService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

/**
 * 商品分类Service业务层处理
 *
 * @author kynley
 * @date 2026-04-14
 */
@Service
public class SortServiceImpl implements ISortService
{
    @Autowired
    private SortMapper sortMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询商品分类
     *
     * @param sortId 商品分类主键
     * @return 商品分类
     */
    @Override
    public Sort selectSortBySortId(Long sortId)
    {
        return sortMapper.selectSortBySortId(sortId);
    }

    /**
     * 查询商品分类列表
     *
     * @param sort 商品分类
     * @return 商品分类
     */
    @Override
    public List<Sort> selectSortList(Sort sort)
    {
        return sortMapper.selectSortList(sort);
    }

    /**
     * 新增商品分类
     *
     * @param sort 商品分类
     * @return 结果
     */
    @Override
    public int insertSort(Sort sort)
    {
        return sortMapper.insertSort(sort);
    }

    /**
     * 批量新增商品分类
     *
     * @param sorts 商品分类List
     * @return 结果
     */
    @Override
    public int batchInsertSort(List<Sort> sorts)
    {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(sorts)) {
            try {
                for (int i = 0; i < sorts.size(); i++) {
                    int row = sortMapper.insertSort(sorts.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i >0 && i%100 == 0) || i == sorts.size() - 1;
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
     * 修改商品分类
     *
     * @param sort 商品分类
     * @return 结果
     */
    @Override
    public int updateSort(Sort sort)
    {
        return sortMapper.updateSort(sort);
    }

    /**
     * 批量删除商品分类
     *
     * @param sortIds 需要删除的商品分类主键
     * @return 结果
     */
    @Override
    public int deleteSortBySortIds(Long[] sortIds)
    {
        return sortMapper.deleteSortBySortIds(sortIds);
    }

    /**
     * 删除商品分类信息
     *
     * @param sortId 商品分类主键
     * @return 结果
     */
    @Override
    public int deleteSortBySortId(Long sortId)
    {
        return sortMapper.deleteSortBySortId(sortId);
    }
}
