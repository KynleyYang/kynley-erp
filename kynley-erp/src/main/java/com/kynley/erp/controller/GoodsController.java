package com.kynley.erp.controller;

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
import org.springframework.web.multipart.MultipartFile;
import com.kynley.erp.domain.Goods;
import com.kynley.erp.service.IGoodsService;
import com.kynley.common.utils.poi.ExcelUtil;
import com.kynley.common.core.page.TableDataInfo;

/**
 * 商品Controller
 *
 * @author kynley
 * @date 2026-04-15
 */
@RestController
@RequestMapping("/erp/goods")
public class GoodsController extends BaseController
{
    @Autowired
    private IGoodsService goodsService;

    /**
     * 查询商品列表
     */
    @PreAuthorize("@ss.hasPermi('erp:goods:list')")
    @GetMapping("/list")
    public TableDataInfo list(Goods goods)
    {
        startPage();
        List<Goods> list = goodsService.selectGoodsList(goods);
        return getDataTable(list);
    }

    /**
     * 导出商品列表
     */
    @PreAuthorize("@ss.hasPermi('erp:goods:export')")
    @Log(title = "商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Goods goods)
    {
        List<Goods> list = goodsService.selectGoodsList(goods);
        ExcelUtil<Goods> util = new ExcelUtil<Goods>(Goods.class);
        util.exportExcel(response, list, "商品数据");
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<Goods> util = new ExcelUtil<Goods>(Goods.class);
        util.importTemplateExcel(response, "商品数据");
    }

    /**
     * 导入数据
     */
    @Log(title = "商品", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('erp:goods:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<Goods> util = new ExcelUtil<Goods>(Goods.class);
        InputStream inputStream = file.getInputStream();
        List<Goods> list = util.importExcel(inputStream );
        inputStream.close();
        int count = goodsService.batchInsertGoods(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取商品详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:goods:query')")
    @GetMapping(value = "/{goodsId}")
    public AjaxResult getInfo(@PathVariable("goodsId") String goodsId)
    {
        return success(goodsService.selectGoodsByGoodsId(goodsId));
    }

    /**
     * 新增商品
     */
    @PreAuthorize("@ss.hasPermi('erp:goods:add')")
    @Log(title = "商品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Goods goods)
    {
        return toAjax(goodsService.insertGoods(goods));
    }

    /**
     * 修改商品
     */
    @PreAuthorize("@ss.hasPermi('erp:goods:edit')")
    @Log(title = "商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Goods goods)
    {
        return toAjax(goodsService.updateGoods(goods));
    }

    /**
     * 删除商品
     */
    @PreAuthorize("@ss.hasPermi('erp:goods:remove')")
    @Log(title = "商品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{goodsIds}")
    public AjaxResult remove(@PathVariable("goodsIds") String[] goodsIds)
    {
        return toAjax(goodsService.deleteGoodsByGoodsIds(goodsIds));
    }

    /**
     * 查询总商品数,库存正常数,库存预警数
     */
    @GetMapping("/selectGoodsOrNormalOrWarningToCount")
    public AjaxResult selectGoodsOrNormalOrWarningToCount() {
        return success(goodsService.selectGoodsOrNormalOrWarningToCount());
    }

    /**
     * 查询库存预警商品列表
     */
    @GetMapping("/selectWarningGoodsList")
    public TableDataInfo selectWarningGoodsList(Goods goods) {
        startPage();
        List<Goods> list = goodsService.selectWarningGoodsList(goods);
        return getDataTable(list);
    }
}
