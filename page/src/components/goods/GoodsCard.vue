<template>
  <div @click="onGoodsClick">
    <div class="thumbnail">
      <img :src="imgSrc" alt="" />
      <h3>{{ goodsName }}</h3>
      <p>产地:{{ origin }}</p>
      <span class="goods-price">¥{{ price.toFixed(2) }}</span>
      <button class="btn btn-default">
        <a @click.prevent.stop="addCart"
          ><span class="glyphicon glyphicon-shopping-cart"></span>加入购物车</a
        >
      </button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    productId: {
      type: Number,
      default: "0",
    },
    imgSrc: {
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
      let params = {
        productId: this.productId,
        quantity: 1,
      };
      this.$api.cart.add(params).then((response) => {
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
  background-color: #fff;
  border: 2px solid transparent;
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
  margin-left: 90px;
  font-size: 18px;
  color: #119744;
}

.thumbnail button {
  margin-left: 20px;
}

.thumbnail button:hover {
  background-color: #119744;
}

.thumbnail button:hover a {
  color: #fff;
}
</style>
