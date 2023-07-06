<template>
  <div @click="onGoodsClick">
    <div class="thumbnail">
      <div style="width: 200px; height: 200px; margin: 10px auto">
        <img-show :imgName="imgName"></img-show>
      </div>
      <h3>{{ goodsName }}</h3>
      <p>产地:{{ origin }}</p>
      <div class="card-bottom" style="margin: 0 auto; width: 200px">
        <span class="goods-price">¥{{ price.toFixed(2) }}</span>
        <button class="btn btn-default" @click.prevent.stop="addCart">
          <span class="glyphicon glyphicon-shopping-cart"></span>
          <span> 加入购物车</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import ImgShow from "../ImgShow.vue";
export default {
  components: {
    ImgShow,
  },
  props: {
    productId: {
      type: Number,
      default: "0",
    },
    imgName: {
      type: String,
      default: "/images/goods/01.jpeg",
    },
    goodsName: {
      type: String,
      default: "商品名称",
    },
    origin: {
      type: String,
      default: "地址",
    },
    price: {
      type: Number,
      default: 0,
    },
  },
  methods: {
    addCart() {
      this.$api.cart.add(this.productId).then((response) => {
        // console.log(response);
        const res = response.data;
        if (res.code === 200) {
          this.$message.success("加入购物车成功");
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
    onGoodsClick() {
      let routeUrl = this.$router.resolve({
        name: "GoodsDetail",
        params: {
          id: this.productId,
        },
      });
      window.open(routeUrl.href, "_blank");
    },
  },
};
</script>

<style scoped>
.thumbnail {
  padding: 20px 0;
  background-color: #fff;
  border: 2px solid transparent;
  cursor: pointer;
}

.thumbnail a {
  text-decoration: none;
  color: black;
}

.thumbnail:hover {
  border: 2px solid #119744;
}

.thumbnail h3 {
  text-align: center;
}

.thumbnail p {
  text-align: center;
  font-size: 14px;
}

.goods-price {
  font-size: 18px;
  color: #119744;
}

.thumbnail button {
  margin-left: 20px;
  padding: 10px;
}

.thumbnail button:hover {
  background-color: #119744;
  color: #fff;
}
</style>
