<template>
  <div>
    <table>
      <tr class="title">
        <td>商品</td>
        <td>品种</td>
        <td>单价</td>
        <td>数量</td>
        <td>小计</td>
      </tr>
      <tr v-for="item in goodsList" :key="item.orderdetialId">
        <td @click="onGoodsClick(item.productId)" style="cursor: pointer">
          <img src="/images/goods/01.jpeg" alt="" />
        </td>
        <td>{{ item.productName }}</td>
        <td class="danjia">
          ￥{{ (item.totalMoney / item.quantity).toFixed(2) }}
        </td>
        <td class="number">
          {{ item.quantity }}
        </td>
        <td class="price">￥{{ item.totalMoney.toFixed(2) }}</td>
      </tr>
    </table>
    <div class="box">
      <p>
        订单号： <span class="orderid">{{ goodsList[0].orderId }}</span>
      </p>
      <p>
        合计费用 ￥<span class="total-price">{{ amount }}</span>
      </p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      goodsList: [
        {
          orderdetialId: 15,
          quantity: 8,
          totalMoney: 360,
          orderId: 13,
          productId: 1,
          productName: "白羽肉鸡",
        },
        {
          orderdetialId: 16,
          quantity: 5,
          totalMoney: 250,
          orderId: 13,
          productId: 2,
          productName: "黑羽肉鸡",
        },
      ],
    };
  },
  computed: {
    amount() {
      let amount = 0;
      this.goodsList.forEach((val) => {
        amount += val.totalMoney;
      });
      return amount;
    },
  },
  created: function () {
    this.requestCart();
  },
  methods: {
    // 请求数据
    requestCart() {
      const params = {
        orderId: this.$route.params.orderId,
        page: 1,
        pageSize: 9999,
      };
      this.$api.orders.selectAllOrder(params).then((response) => {
        // console.log(response);
        const res = response.data;
        if (res.code === 200) {
          // this.$message.success("成功")
          this.goodsList = res.data.list;
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
    onGoodsClick(productId) {
      let routeUrl = this.$router.resolve({
        name: "GoodsDetail",
        params: {
          id: productId,
        },
      });
      window.open(routeUrl.href, "_blank");
    },
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  user-select: none;
}

table {
  border-collapse: collapse;
  width: 1000px;
  margin: 100px auto 0;
}

table td {
  border: 1px solid #119744;
}

table tr td {
  width: 250px;
  height: 100px;
  text-align: center;
}

table tr.title td {
  height: 50px;
  background: #119744;
  color: #fff;
  font-weight: bold;
  font-size: 14px;
}

table tr td img {
  width: 100px;
}

table tr td input[type="checkbox"],
input[type="radio"] {
  margin: 4px 15px 0 0;
}

table tr td.number span {
  display: inline-block;
  font-size: 14px;
  text-align: center;
}

table tr td.number span.down,
table tr td.number span.add {
  width: 30px;
  height: 30px;
  background: #ddd;
  font-weight: bold;
  font-size: 14px;
  line-height: 30px;
  cursor: pointer;
}

table tr td.number span.num {
  width: 50px;
  height: 30px;
  line-height: 30px;
  border-top: 1px solid #ddd;
  border-bottom: 1px solid #ddd;
  color: #119744;
}

.table img {
  width: 50px;
  height: 40px;
}

.box {
  display: flex;
  justify-content: space-between;
  width: 1000px;
  height: 50px;
  background: #119744;
  margin: 10px auto;
}

.box p {
  width: 250px;
  height: 80px;
  padding-left: 25px;
  margin-top: 17px;
  line-height: 15px;
  color: #fff;
  font-size: 14px;
}

.box p span {
  color: #f4e5a9;
  font-size: 16px;
  font-weight: bold;
}
</style>
