package com.kynley.erp.controller;

import java.util.List;

import com.kynley.erp.domain.Goods;
import com.kynley.erp.domain.SaleItems;
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
import com.kynley.erp.domain.Sale;
import com.kynley.erp.service.ISaleService;
import com.kynley.common.utils.poi.ExcelUtil;
import com.kynley.common.core.page.TableDataInfo;

/**
 * 销售Controller
 *
 * @author kynley
 * @date 2026-04-18
 */
@RestController
@RequestMapping("/erp/sale")
public class SaleController extends BaseController {
    @Autowired
    private ISaleService saleService;

    @Autowired
    private IGoodsService goodsService;

    /**
     * 查询销售列表
     */
    @PreAuthorize("@ss.hasPermi('erp:sale:list')")
    @GetMapping("/list")
    public TableDataInfo list(Sale sale) {
        startPage();
        List<Sale> list = saleService.selectSaleList(sale);
        return getDataTable(list);
    }

    /**
     * 导出销售列表
     */
    @PreAuthorize("@ss.hasPermi('erp:sale:export')")
    @Log(title = "销售", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Sale sale) {
        List<Sale> list = saleService.selectSaleList(sale);
        ExcelUtil<Sale> util = new ExcelUtil<Sale>(Sale.class);
        util.exportExcel(response, list, "销售数据");
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Sale> util = new ExcelUtil<Sale>(Sale.class);
        util.importTemplateExcel(response, "销售数据");
    }

    /**
     * 导入数据
     */
    @Log(title = "销售", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('erp:sale:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Sale> util = new ExcelUtil<Sale>(Sale.class);
        InputStream inputStream = file.getInputStream();
        List<Sale> list = util.importExcel(inputStream);
        inputStream.close();
        int count = saleService.batchInsertSale(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取销售详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:sale:query')")
    @GetMapping(value = "/{saleId}")
    public AjaxResult getInfo(@PathVariable("saleId") String saleId) {
        return success(saleService.selectSaleBySaleId(saleId));
    }

    /**
     * 新增销售
     */
    @PreAuthorize("@ss.hasPermi('erp:sale:add')")
    @Log(title = "销售", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Sale sale) {
        return toAjax(saleService.insertSale(sale));
    }

    /**
     * 修改销售
     */
    @PreAuthorize("@ss.hasPermi('erp:sale:edit')")
    @Log(title = "销售", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Sale sale) {
        return toAjax(saleService.updateSale(sale));
    }

    /**
     * 删除销售
     */
    @PreAuthorize("@ss.hasPermi('erp:sale:remove')")
    @Log(title = "销售", businessType = BusinessType.DELETE)
    @DeleteMapping("/{saleIds}")
    public AjaxResult remove(@PathVariable("saleIds") String[] saleIds) {
        return toAjax(saleService.deleteSaleBySaleIds(saleIds));
    }

    /**
     * 发货
     */
    @PutMapping("/handleUp")
    public AjaxResult handleUp(@RequestBody Sale sale) {
        //拿到销售明细
        List<SaleItems> saleItemsList = sale.getSaleItemsList();
        //循环遍历拿到每一条销售明细信息
        for (SaleItems saleItems : saleItemsList) {
            //拿到每一个商品的ID
            String goodsId = saleItems.getGoodsId();
            //拿到每一个商品的销售数量
            Double total = saleItems.getTotal();
            //拿到每一个商品的库存
            Double stock = goodsService.selectGoodsByGoodsId(goodsId).getStock();
            //拿到每一个商品的名称
            String goodsName = goodsService.selectGoodsByGoodsId(goodsId).getGoodsName();

            //判断库存是否足够
            if (total > stock) {
                return error(goodsName + "库存不足, 请尽快进货");
            } else {
                //new一个商品对象
                Goods goods = new Goods();
                goods.setGoodsId(goodsId);
                goods.setStock(stock - total);
                goodsService.updateGoods(goods);
            }
        }
        //更新销售状态
        sale.setStatus("已发货");
        return toAjax(saleService.updateSale(sale));
    }
}
