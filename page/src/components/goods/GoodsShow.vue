<template>
  <div class="goods_show clearfix">
    <goods-card
      class="col-md-3"
      v-for="item in goodsList"
      :key="item.productId"
      :productId="item.productId"
      :goodsName="item.productName"
      :imgName="item.image"
      :origin="item.origin"
      :price="item.price"
    ></goods-card>
  </div>
</template>

<script>
import GoodsCard from "./GoodsCard.vue";
export default {
  components: {
    GoodsCard,
  },
  props: {
    list: {
      type: Array,
      default: () => {
        return [];
      },
    },
  },
  watch: {
    list: function (val) {
      this.goodsList = val;
    },
  },
  data() {
    return {
      goodsList: this.list, //列表数据
    };
  },
};
</script>

<style scoped>
.goods_show {
  min-height: 650px;
}
</style>
