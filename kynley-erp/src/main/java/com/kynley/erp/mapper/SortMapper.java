package com.kynley.erp.mapper;

import java.util.List;
import com.kynley.erp.domain.Sort;

/**
 * 商品分类Mapper接口
 * 
 * @author kynley
 * @date 2026-04-14
 */
public interface SortMapper 
{
    /**
     * 查询商品分类
     * 
     * @param sortId 商品分类主键
     * @return 商品分类
     */
    public Sort selectSortBySortId(Long sortId);

    /**
     * 查询商品分类列表
     * 
     * @param sort 商品分类
     * @return 商品分类集合
     */
    public List<Sort> selectSortList(Sort sort);

    /**
     * 新增商品分类
     * 
     * @param sort 商品分类
     * @return 结果
     */
    public int insertSort(Sort sort);

    /**
     * 修改商品分类
     * 
     * @param sort 商品分类
     * @return 结果
     */
    public int updateSort(Sort sort);

    /**
     * 删除商品分类
     * 
     * @param sortId 商品分类主键
     * @return 结果
     */
    public int deleteSortBySortId(Long sortId);

    /**
     * 批量删除商品分类
     * 
     * @param sortIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSortBySortIds(Long[] sortIds);
}
