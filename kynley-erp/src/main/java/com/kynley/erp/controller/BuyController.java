package com.kynley.erp.controller;

import java.util.List;

import com.kynley.erp.domain.BuyItems;
import com.kynley.erp.domain.Goods;
import com.kynley.erp.service.IGoodsService;
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
import com.kynley.erp.domain.Buy;
import com.kynley.erp.service.IBuyService;
import com.kynley.common.utils.poi.ExcelUtil;
import com.kynley.common.core.page.TableDataInfo;

/**
 * 采购Controller
 *
 * @author kynley
 * @date 2026-04-16
 */
@RestController
@RequestMapping("/erp/buy")
public class BuyController extends BaseController {
    @Autowired
    private IBuyService buyService;

    @Autowired
    private IGoodsService goodsService;

    /**
     * 查询采购列表
     */
    @PreAuthorize("@ss.hasPermi('erp:buy:list')")
    @GetMapping("/list")
    public TableDataInfo list(Buy buy) {
        startPage();
        List<Buy> list = buyService.selectBuyList(buy);
        return getDataTable(list);
    }

    /**
     * 导出采购列表
     */
    @PreAuthorize("@ss.hasPermi('erp:buy:export')")
    @Log(title = "采购", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Buy buy) {
        List<Buy> list = buyService.selectBuyList(buy);
        ExcelUtil<Buy> util = new ExcelUtil<Buy>(Buy.class);
        util.exportExcel(response, list, "采购数据");
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Buy> util = new ExcelUtil<Buy>(Buy.class);
        util.importTemplateExcel(response, "采购数据");
    }

    /**
     * 导入数据
     */
    @Log(title = "采购", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('erp:buy:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Buy> util = new ExcelUtil<Buy>(Buy.class);
        InputStream inputStream = file.getInputStream();
        List<Buy> list = util.importExcel(inputStream);
        inputStream.close();
        int count = buyService.batchInsertBuy(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取采购详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:buy:query')")
    @GetMapping(value = "/{buyId}")
    public AjaxResult getInfo(@PathVariable("buyId") String buyId) {
        return success(buyService.selectBuyByBuyId(buyId));
    }

    /**
     * 新增采购
     */
    @PreAuthorize("@ss.hasPermi('erp:buy:add')")
    @Log(title = "采购", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Buy buy) {
        return toAjax(buyService.insertBuy(buy));
    }

    /**
     * 修改采购
     */
    @PreAuthorize("@ss.hasPermi('erp:buy:edit')")
    @Log(title = "采购", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Buy buy) {
        return toAjax(buyService.updateBuy(buy));
    }

    /**
     * 删除采购
     */
    @PreAuthorize("@ss.hasPermi('erp:buy:remove')")
    @Log(title = "采购", businessType = BusinessType.DELETE)
    @DeleteMapping("/{buyIds}")
    public AjaxResult remove(@PathVariable String[] buyIds) {
        return toAjax(buyService.deleteBuyByBuyIds(buyIds));
    }

    /**
     * 采购到货
     */
    @PutMapping("/reach")
    public AjaxResult reach(@RequestBody Buy buy) {
        //获取采购明细
        List<BuyItems> buyItemsList = buy.getBuyItemsList();
        //循环遍历拿到明细的每一条信息
        for (BuyItems buyItems : buyItemsList) {
            //拿到商品ID
            String goodsId = buyItems.getGoodsId();
            //拿到数量
            Double total = buyItems.getTotal();
            //创建商品对象
            Goods goods = new Goods();
            //插入商品ID
            goods.setGoodsId(goodsId);

            //查询该商品在该采购单到货前的库存
            Double stock = goodsService.selectGoodsByGoodsId(goodsId).getStock();
            //插入商品数量(采购单到货前的库存 + 要采购的数量)
            goods.setStock(stock + total);

            //更新商品的库存
            goodsService.updateGoods(goods);
        }
        //更新采购单状态
        return toAjax(buyService.updateBuy(buy));
    }
}