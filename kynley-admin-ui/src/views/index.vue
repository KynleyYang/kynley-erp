<template>
  <div class="inventory-container">
    <!-- 主横幅 -->
    <div class="main-banner">
      <el-card shadow="never" class="transparent-card">
        <div class="banner-content">
          <div class="banner-text">
            <h1>进销存管理系统</h1>
            <p class="sub-title">高效 · 精准 · 智能的库存管理解决方案</p>
            <el-divider></el-divider>
            <p class="banner-description">
              提供采购、销售、库存全流程数字化管理，
              帮助企业降低运营成本，提高管理效率，实现库存最优化。
            </p>
          </div>
        </div>
      </el-card>
    </div>

    <!-- 核心功能 -->
    <div class="feature-section">
      <h2 class="section-title"><i class="el-icon-s-operation"></i> 系统核心功能</h2>
      <el-row :gutter="30">
        <el-col :xs="24" :sm="12" :md="8" :lg="8">
          <div class="feature-card">
            <div class="feature-icon" style="color: #409EFF;">
              <el-icon><ShoppingCart /></el-icon>
            </div>
            <h3>采购管理</h3>
            <p>完整的采购流程管理，从采购申请到入库验收，全程可追溯。</p>
          </div>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="8">
          <div class="feature-card">
            <div class="feature-icon" style="color: #67C23A;">
              <el-icon><SoldOut /></el-icon>
            </div>
            <h3>销售管理</h3>
            <p>销售订单、出库、退货一体化管理，实时掌握销售动态。</p>
          </div>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="8">
          <div class="feature-card">
            <div class="feature-icon" style="color: #E6A23C;">
              <el-icon><Box /></el-icon>
            </div>
            <h3>库存管理</h3>
            <p>实时库存监控，预警提醒，支持多仓库管理，库存盘点。</p>
          </div>
        </el-col>
      </el-row>
    </div>

    <!-- 系统优势 -->
    <div class="advantage-section">
      <h2 class="section-title"><i class="el-icon-medal"></i> 系统优势</h2>
      <el-row :gutter="20">
        <el-col :xs="12" :sm="8" :md="8" :lg="8">
          <div class="advantage-card">
            <div class="advantage-icon">
              <el-icon><TrendCharts /></el-icon>
            </div>
            <h4>数据可视化</h4>
            <p>经营数据一目了然</p>
          </div>
        </el-col>
        <el-col :xs="12" :sm="8" :md="8" :lg="8">
          <div class="advantage-card">
            <div class="advantage-icon">
              <el-icon><Lock /></el-icon>
            </div>
            <h4>安全可靠</h4>
            <p>数据多重备份加密</p>
          </div>
        </el-col>
        <el-col :xs="12" :sm="8" :md="8" :lg="8">
          <div class="advantage-card">
            <div class="advantage-icon">
              <el-icon><Setting /></el-icon>
            </div>
            <h4>灵活定制</h4>
            <p>按需定制功能模块</p>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import {
  Box,
  Lock,
  Setting,
  ShoppingCart,
  SoldOut,
  TrendCharts
} from "@element-plus/icons-vue";
</script>

<style lang="scss" scoped>
.inventory-container {
  padding: 20px;
  background-color: #f5f7fa;

  .transparent-card {
    background-color: transparent;
    border: none;
  }

  .main-banner {
    margin-bottom: 40px;

    .banner-content {
      display: flex;
      align-items: center;
      padding: 30px;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border-radius: 8px;
      color: white;

      .banner-text {
        flex: 1;
        padding-right: 40px;

        h1 {
          font-size: 36px;
          color: white;
          margin-bottom: 15px;
        }

        .sub-title {
          font-size: 18px;
          color: rgba(255,255,255,0.8);
          margin-bottom: 20px;
        }

        .banner-description {
          font-size: 16px;
          line-height: 1.8;
          color: rgba(255,255,255,0.8);
          margin-bottom: 30px;
        }

        .banner-actions {
          display: flex;
          gap: 20px;
        }
      }

      .banner-image {
        flex: 1;
        text-align: center;

        img {
          max-width: 100%;
          border-radius: 4px;
          box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        }
      }
    }
  }

  .section-title {
    text-align: center;
    margin: 40px 0 30px;
    font-size: 28px;
    color: #303133;

    i {
      margin-right: 10px;
    }
  }

  .feature-section {
    margin-bottom: 50px;

    .feature-card {
      background: white;
      padding: 30px 20px;
      border-radius: 8px;
      text-align: center;
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
      height: 100%;
      transition: transform 0.3s;

      &:hover {
        transform: translateY(-5px);
      }

      .feature-icon {
        font-size: 48px;
        margin-bottom: 20px;
      }

      h3 {
        font-size: 20px;
        margin-bottom: 15px;
        color: #303133;
      }

      p {
        color: #606266;
        line-height: 1.6;
      }
    }
  }

  .advantage-section {
    margin: 50px 0;

    .advantage-card {
      background: white;
      padding: 25px 15px;
      border-radius: 8px;
      text-align: center;
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
      height: 100%;
      margin-bottom: 20px;

      .advantage-icon {
        font-size: 36px;
        margin-bottom: 15px;
      }

      h4 {
        font-size: 18px;
        margin-bottom: 10px;
        color: #303133;
      }

      p {
        color: #909399;
        font-size: 14px;
      }
    }
  }

  .stats-section {
    margin: 50px 0;

    .stat-card {
      background: white;
      padding: 30px 15px;
      border-radius: 8px;
      text-align: center;
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
      height: 100%;

      .stat-number {
        font-size: 32px;
        font-weight: bold;
        color: #409EFF;
        margin-bottom: 10px;
      }

      .stat-label {
        color: #606266;
        font-size: 16px;
      }
    }
  }
}
</style>
