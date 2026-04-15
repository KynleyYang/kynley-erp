package com.kynley.erp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kynley.common.annotation.Log;
import com.kynley.common.core.controller.BaseController;
import com.kynley.common.core.domain.AjaxResult;
import com.kynley.common.enums.BusinessType;
import java.io.InputStream;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import com.kynley.erp.domain.Sort;
import com.kynley.erp.service.ISortService;
import com.kynley.common.utils.poi.ExcelUtil;

/**
 * 商品分类Controller
 *
 * @author kynley
 * @date 2026-04-14
 */
@RestController
@RequestMapping("/erp/sort")
public class SortController extends BaseController
{
    @Autowired
    private ISortService sortService;

    /**
     * 查询商品分类列表
     */
    @PreAuthorize("@ss.hasPermi('erp:sort:list')")
    @GetMapping("/list")
    public AjaxResult list(Sort sort)
    {
        List<Sort> list = sortService.selectSortList(sort);
        return success(list);
    }

    /**
     * 导出商品分类列表
     */
    @PreAuthorize("@ss.hasPermi('erp:sort:export')")
    @Log(title = "商品分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Sort sort)
    {
        List<Sort> list = sortService.selectSortList(sort);
        ExcelUtil<Sort> util = new ExcelUtil<Sort>(Sort.class);
        util.exportExcel(response, list, "商品分类数据");
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<Sort> util = new ExcelUtil<Sort>(Sort.class);
        util.importTemplateExcel(response, "商品分类数据");
    }

    /**
     * 导入数据
     */
    @Log(title = "商品分类", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('erp:sort:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<Sort> util = new ExcelUtil<Sort>(Sort.class);
        InputStream inputStream = file.getInputStream();
        List<Sort> list = util.importExcel(inputStream );
        inputStream.close();
        int count = sortService.batchInsertSort(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取商品分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:sort:query')")
    @GetMapping(value = "/{sortId}")
    public AjaxResult getInfo(@PathVariable("sortId") Long sortId)
    {
        return success(sortService.selectSortBySortId(sortId));
    }

    /**
     * 新增商品分类
     */
    @PreAuthorize("@ss.hasPermi('erp:sort:add')")
    @Log(title = "商品分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Sort sort)
    {
        return toAjax(sortService.insertSort(sort));
    }

    /**
     * 修改商品分类
     */
    @PreAuthorize("@ss.hasPermi('erp:sort:edit')")
    @Log(title = "商品分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Sort sort)
    {
        return toAjax(sortService.updateSort(sort));
    }

    /**
     * 删除商品分类
     */
    @PreAuthorize("@ss.hasPermi('erp:sort:remove')")
    @Log(title = "商品分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{sortIds}")
    public AjaxResult remove(@PathVariable("sortIds") Long[] sortIds)
    {
        return toAjax(sortService.deleteSortBySortIds(sortIds));
    }

    /**
     * 查询分类列表(树选择使用)
     */
    @GetMapping("/treeList")
    public AjaxResult treeList(Sort sort) {
        List<Sort> list = sortService.selectSortList(sort);
        List<Map<String, Object>> treeData = buildTree(list);
        return success(treeData);
    }

    /**
     * 构建树形选择数据
     * @param list 扁平化的分类列表
     * @return 树形结构数据
     */
    private List<Map<String, Object>> buildTree(List<Sort> list) {
        //初始化树形结构的列表
        List<Map<String, Object>> tree = new ArrayList<>();
        //遍历所有分类项
        for (Sort sort : list) {
            //判断是否为顶级节点(parentId为null或者0)
            if (sort.getParentId() == null || sort.getParentId() == 0) {
                //创建当前节点
                Map<String, Object> node = new HashMap<>();
                //设置节点ID
                node.put("value", sort.getSortId());
                //设置节点的显示名称
                node.put("label", sort.getClassifyName());
                //递归获取所有子节点
                node.put("children", getChildren(sort.getSortId(), list));
                //将当前节点添加到树中
                tree.add(node);
            }
        }
        return tree;
    }

    /**
     * 递归获取指定父节点的所有子节点
     * @param parentId
     * @param list
     * @return
     */
    private List<Map<String, Object>> getChildren(Long parentId, List<Sort> list) {
        //初始化子节点列表
        List<Map<String, Object>> children = new ArrayList<>();
        //遍历所有分类项
        for (Sort sort : list) {
            //判断当前分类项的parentId是否等于传入的parentId
            if (parentId.equals(sort.getParentId())) {
                //创建子节点
                Map<String, Object> node = new HashMap<>();
                //设置节点ID
                node.put("value", sort.getSortId());
                //设置节点的显示名称
                node.put("label", sort.getClassifyName());
                //递归获取当前子节点的子节点(孙子节点)
                List<Map<String, Object>> subChildren = getChildren(sort.getSortId(), list);
                //如果存在子节点, 则添加到当前节点
                if (!subChildren.isEmpty()) {
                    node.put("children", subChildren);
                }
                //将当前子节点添加到子节点列表
                children.add(node);
            }
        }
        return children;
    }

}