<template>
  <!-- 商品部分 -->
  <div>
    <div class="panel-heading">
      <h1>商品热卖🔥</h1>
    </div>
    <div class="panel-body">
      <div class="row">
        <goods-show :list="productsList"></goods-show>
      </div>
    </div>
  </div>
  <!-- 商品部分结束 -->
</template>

<script>
import GoodsShow from "./GoodsShow.vue";

export default {
  components: {
    GoodsShow,
  },
  data() {
    return {
      page: 1,
      pageSize: 16,
      productName: null,
      origin: null,
      category: null,
      productsList: [],
    };
  },
  created: function () {
    //发送请求，请求商品第一页
    this.requestGoodsData({});
  },

  methods: {
    // 请求
    requestGoodsData({
      page = this.page,
      pageSize = this.pageSize,
      productName,
      origin,
      category,
    }) {
      let params = {
        page,
        pageSize,
        productName,
        origin,
        category,
      };
      this.$api.products.selectProductBySales(params).then((response) => {
        // console.log(response);
        const res = response.data;
        if (res.code === 200) {
          // this.$message.success(successMsg)
          let { list } = res.data;
          this.productsList = list;
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
  },
};
</script>

<style scoped>
.user_container {
  background-color: #f3f5f7;
}
</style>
