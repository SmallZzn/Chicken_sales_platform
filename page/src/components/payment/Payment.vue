<template>
  <div class="payment-body">
    <h2>支付订单</h2>

    <div class="box">
      <a href="#/user"
        ><span class="glyphicon glyphicon-home" style="color: #333"></span
      ></a>

      <span style="font-size: 16px"> 确认收货地址</span>
      <!-- 跳转到编辑地址页面 -->
      <router-link
        class="btn btn-default"
        style="margin-left: 330px"
        :to="{ name: 'UserMe', params: { pageIndex: 3 } }"
        >编辑地址
      </router-link>
      <hr />
      <div class="row address">
        <!-- 选择地址 -->
        <select-address
          style="height: 80px; overflow-y: scroll"
          @pickAddress="pickedAdressId = $event"
        ></select-address>
      </div>
      <hr />
      <!-- 商品 -->
      <div
        class="row"
        style="margin-bottom: 16px"
        v-for="goods in orderGoods"
        :key="goods.cartId"
      >
        <div class="col-md-3">
          <img :src="goods.img" alt="" class="goods-img" />
        </div>
        <div class="col-md-8" style="margin-top: 20px">
          {{ goods.productName }}<br />
          {{ goods.quantity }}只<br />
          总计:¥{{ goods.allprice }}
        </div>
      </div>
      <button
        type="button"
        class="btn btn-success"
        @click="pay"
        style="margin-left: 223px"
      >
        点击支付
      </button>
    </div>
  </div>
</template>

<script>
import SelectAddress from "../address/SelectAddress.vue";

export default {
  components: { SelectAddress },
  data() {
    return {
      pickedAdressId: null,
      orderGoods: [],
    };
  },
  computed: {
    orderCartIds: function () {
      let cartIds = [];
      this.orderGoods.forEach((goods) => {
        cartIds.push(goods.cartId);
      });
      return cartIds;
    },
  },
  mounted() {
    // 订单的商品
    this.orderGoods = this.$route.query.selectedGoods;
  },
  methods: {
    pay() {
      this.$api.orders
        .pay(this.orderCartIds, this.pickedAdressId)
        .then((response) => {
          const res = response.data;
          if (res.code === 200) {
            this.$message.success("支付订单成功");
            //显示成功
          } else {
            this.$message.error(res.msg);
          }
        });
    },
  },
};
</script>

<style scoped>
h2 {
  text-align: center;
}

.box {
  max-width: 600px;
  margin: 50px auto;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  padding: 30px;
}

.goods-img {
  width: 100px;
  height: 100px;
}

.address {
  margin-top: -10px;
  margin-left: 5px;
}
</style>
