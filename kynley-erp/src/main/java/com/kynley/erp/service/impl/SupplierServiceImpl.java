package com.kynley.erp.service.impl;

import java.util.List;

import com.kynley.common.utils.DateUtils;
import com.kynley.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kynley.erp.mapper.SupplierMapper;
import com.kynley.erp.domain.Supplier;
import com.kynley.erp.service.ISupplierService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;

/**
 * 供应商Service业务层处理
 *
 * @author kynley
 * @date 2026-03-13
 */
@Service
public class SupplierServiceImpl implements ISupplierService {
    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询供应商
     *
     * @param supplierId 供应商主键
     * @return 供应商
     */
    @Override
    public Supplier selectSupplierBySupplierId(String supplierId) {
        return supplierMapper.selectSupplierBySupplierId(supplierId);
    }

    /**
     * 查询供应商列表
     *
     * @param supplier 供应商
     * @return 供应商
     */
    @Override
    public List<Supplier> selectSupplierList(Supplier supplier) {
        return supplierMapper.selectSupplierList(supplier);
    }

    /**
     * 新增供应商
     *
     * @param supplier 供应商
     * @return 结果
     */
    @Override
    public int insertSupplier(Supplier supplier) {
        supplier.setCreateTime(DateUtils.getNowDate());
//        生成一个uuid并插入到对象中
        supplier.setSupplierId(IdUtils.fastSimpleUUID());
        return supplierMapper.insertSupplier(supplier);
    }

    /**
     * 批量新增供应商
     *
     * @param suppliers 供应商List
     * @return 结果
     */
    @Override
    public int batchInsertSupplier(List<Supplier> suppliers) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(suppliers)) {
            try {
                for (int i = 0; i < suppliers.size(); i++) {
//                    给每条要插入的供应商添加uuid
                    for (Supplier supplier : suppliers) {
                        supplier.setSupplierId(IdUtils.fastSimpleUUID());
                    }
                    int row = supplierMapper.insertSupplier(suppliers.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i > 0 && i % 100 == 0) || i == suppliers.size() - 1;
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
     * 修改供应商
     *
     * @param supplier 供应商
     * @return 结果
     */
    @Override
    public int updateSupplier(Supplier supplier) {
        return supplierMapper.updateSupplier(supplier);
    }

    /**
     * 批量删除供应商
     *
     * @param supplierIds 需要删除的供应商主键
     * @return 结果
     */
    @Override
    public int deleteSupplierBySupplierIds(String[] supplierIds) {
        return supplierMapper.deleteSupplierBySupplierIds(supplierIds);
    }

    /**
     * 删除供应商信息
     *
     * @param supplierId 供应商主键
     * @return 结果
     */
    @Override
    public int deleteSupplierBySupplierId(String supplierId) {
        return supplierMapper.deleteSupplierBySupplierId(supplierId);
    }

    /**
     * 不分页查询供应商
     * @return
     */
    @Override
    public List<Supplier> selectAllSupplierList() {
        return supplierMapper.selectAllSupplierList();
    }
}
