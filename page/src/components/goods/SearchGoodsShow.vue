<template>
  <div class="search_goods">
    <!-- 排序 -->
    <div class="sort_goods">
      <ul class="nav nav-pills header-top-nav">
        <li>
          <a
            href="#"
            @click.prevent="sortId = 0"
            :class="{ sort_active: sortId === 0 }"
            >综合</a
          >
        </li>
        <li>
          <a
            href="#"
            @click.prevent="sortId = 1"
            :class="{ sort_active: sortId === 1 }"
            >销量升序</a
          >
        </li>
        <li>
          <a
            href="#"
            @click.prevent="sortId = 2"
            :class="{ sort_active: sortId === 2 }"
            >价格升序</a
          >
        </li>
        <li>
          <a
            href="#"
            @click.prevent="sortId = 3"
            :class="{ sort_active: sortId === 3 }"
            >价格降序</a
          >
        </li>
      </ul>
    </div>
    <!-- 商品部分 -->
    <div class="panel">
      <div class="panel-body">
        <div class="row">
          <goods-show :list="productsList"></goods-show>
        </div>
      </div>
    </div>
    <!-- 商品部分结束 -->
    <!-- 页码 -->
    <div style="text-align: center">
      <ul class="pagination">
        <li class="page-item">
          <a
            class="page-link"
            href="#"
            @click.prevent="page = --page > 0 ? page : 1"
            >上一页</a
          >
        </li>
        <li
          v-for="n in pages"
          :key="n"
          :class="{ 'page-item': true, active: page === n }"
        >
          <a class="page-link" href="#" @click.prevent="page = n">{{ n }}</a>
        </li>
        <li class="page-item">
          <a
            class="page-link"
            href="#"
            @click.prevent="page = ++page < pages ? page : pages"
            >下一页</a
          >
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import GoodsShow from "../goods/GoodsShow.vue";

export default {
  components: {
    GoodsShow,
  },
  data() {
    return {
      page: 1, //当前页码
      total: 0, //列表数量总数
      pageSize: 16, //当前页码的数量
      pages: 10, //总页码数
      productsList: [],
      sortId: 0, //0综合，1销量升序，2价格升序，3价格降序
      productName: null,
      origin: null,
      category: null
    };
  },
  computed: {
    params: function () {
      return {
        page: this.page,
        pageSize: this.pageSize,
        productName: this.productName,
        origin: null,
        category: null,
      };
    },
  },
  watch: {
    // 当路径改变时
    $route(to) {
      this.productName = to.params.productName;
      //发送请求，请求商品第一页
      this.requestGoodsData();
    },
    // 当排序方式改变时
    sortId: function () {
      this.requestGoodsData();
    },
    // 当页面变化时请求数据
    page: function (newVal) {
      // console.log(newVal)
      this.page = newVal;
      this.requestGoodsData();
    },
  },
  mounted: function () {
    this.productName = this.$route.params.productName;
    //发送请求，请求商品第一页
    this.requestGoodsData();
  },
  methods: {
    // 按排序请求数据
    requestGoodsData() {
      switch (this.sortId) {
        case 0:
          this.selectAllProduct();
          break;
        case 1:
          this.selectProductBySales();
          break;
        case 2:
          this.sortProductByPriceASC();
          break;
        case 3:
          this.sortProductByPriceDESC();
          break;
      }
    },
    //处理返回数据
    handleRes(response) {
      // console.log(response);
      const res = response.data;
      if (res.code === 200) {
        // this.$message.success(successMsg)
        let { list, total, pages } = res.data;
        this.productsList = list;
        this.total = total;
        this.pages = pages;
      } else {
        this.$message.error(response.data.msg);
      }
    },
    // 请求
    selectAllProduct() {
      this.$api.products.selectAllProduct(this.params).then((response) => {
        this.handleRes(response);
      });
    },
    // 按销量由高到低查询所有商品
    selectProductBySales() {
      this.$api.products.selectProductBySales(this.params).then((response) => {
        this.handleRes(response);
      });
    },
    // 按价格升序显示产品
    sortProductByPriceASC() {
      this.$api.products.sortProductByPriceASC(this.params).then((response) => {
        this.handleRes(response);
      });
    },
    // 按价格降序显示产品
    sortProductByPriceDESC() {
      this.$api.products
        .sortProductByPriceDESC(this.params)
        .then((response) => {
          this.handleRes(response);
        });
    },
  },
};
</script>

<!-- 引入各种样式 -->
<!-- <style src="../../assets/css/index.css" /> -->

<style scoped>
.sort_goods {
  width: 350px;
  margin: 15px auto;
}

.sort_goods a {
  font-weight: bolder;
  color: #119744ab;
}

.sort_goods a.sort_active {
  color: #119744;
  font-size: 15px;
}

.search_goods .pagination > li > a {
  color: #119744;
}

.search_goods .pagination > .active > a {
  background-color: #119744;
  color: #eee;
}
</style>
