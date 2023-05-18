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
      <tr v-for="item in goodsList" :key="item.productId">
        <td>
          <input type="checkbox" v-model="item.state" @change="onSelect" />
          <img
            src="/images/goods/01.jpeg"
            alt=""
            @click="onGoodsClick(item.productId)"
            style="cursor: pointer"
          />
        </td>
        <td>{{ item.productName }}</td>
        <td class="danjia">￥{{ item.price.toFixed(2) }}</td>
        <td class="number">
          <span class="down" @click="reduceCartProductNum(item)">-</span>
          <span class="num">{{ item.quantity }}</span>
          <span class="add" @click="addCart(item)">+</span>
        </td>
        <td class="price">￥{{ (item.price * item.quantity).toFixed(2) }}</td>
      </tr>
    </table>
    <div class="box">
      <p>
        <input type="checkbox" :checked="isFull" @change="onSelectAll" />全选
      </p>
      <p><span class="remove" @click="onClickRemove">移除</span></p>
      <p>
        已选中商品 <span class="total-num">{{ tatol }}</span> 个
      </p>
      <p>
        合计费用 ￥<span class="total-price">{{ amount }}</span>
      </p>
      <button
        type="submit"
        class="btn btn-default btn-warning"
        @click="onSubmitClick"
      >
        提交订单
      </button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      goodsList: [
        {
          state: true,
          cartdetailId: 1,
          productId: 1,
          quantity: 12,
          allprice: 1200,
          cartId: 1,
          price: 100,
          productName: "坤",
        },
        {
          state: true,
          cartdetailId: 2,
          productId: 2,
          quantity: 11,
          allprice: 1221,
          cartId: 2,
          price: 111,
          productName: "红羽鸡",
        },
      ],
      isFull: true,
    };
  },
  computed: {
    amount() {
      let amount = 0;
      this.goodsList.forEach((val) => {
        if (val.state) {
          amount += val.price * val.quantity;
        }
      });
      return amount;
    },
    tatol() {
      let tatol = 0;
      this.goodsList.forEach((val) => {
        if (val.state) {
          tatol += val.quantity;
        }
      });
      return tatol;
    },
    selectedNum() {
      let num = 0;
      this.goodsList.forEach((val) => {
        if (val.state) {
          num++;
        }
      });
      return num;
    },
  },
  created: function () {
    this.requestCart();
  },
  methods: {
    // 请求数据
    requestCart() {
      this.$api.cart.get().then((response) => {
        // console.log(response);
        const res = response.data;
        if (res.code === 200) {
          // this.$message.success("成功")
          let goodsList = res.data.list;
          // 为每个商品添加选中状态
          goodsList.forEach((val) => {
            val.state = true;
          });
          this.goodsList = goodsList;
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
    // 当选择改变时，判断是否为全选
    onSelect() {
      this.isFull = this.selectedNum === this.goodsList.length;
    },
    // 选择所有商品
    onSelectAll() {
      this.isFull = !this.isFull;
      if (this.isFull) {
        this.goodsList.forEach((val) => {
          val.state = true;
        });
      } else {
        this.goodsList.forEach((val) => {
          val.state = false;
        });
      }
    },
    // 数量减一
    reduceCartProductNum(item) {
      item.quantity--;
      if (item.quantity === 0) {
        this.goodsList = this.goodsList.filter((val) => val != item);
      }
      const { cartdetailId, productId } = item;
      this.$api.cart.reduceNum(cartdetailId, productId);
    },
    // 数量加一
    addCart(item) {
      item.quantity++;
      const { cartdetailId, productId } = item;
      console.log(cartdetailId, productId);
      this.$api.cart.add(cartdetailId, productId);
    },
    // 移除购物车商品
    onClickRemove() {
      if (this.isFull) {
        this.onRemoveAll();
      } else {
        this.goodsList.forEach((val) => {
          if (val.state) {
            this.removeOne(val.cartdetailId, val.productId);
          }
        });
        //刷新数据
        this.requestCart();
      }
    },
    //移除单个商品
    removeOne(cartdetailId, productId) {
      this.$api.cart.reduceNum(cartdetailId, productId);
    },
    //提交订单
    onSubmitClick() {
      let selectedGoods = this.goodsList.filter((item) => item.state);
      this.$router.push({
        name: "Payment",
        query: {
          selectedGoods,
        },
      });
    },
    // 点击清空购物车时
    onRemoveAll() {
      this.$confirm("此操作将清空购物车, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.removeAll();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
        });
    },
    // 发送清空请求
    removeAll() {
      this.$api.cart.removeAll().then((response) => {
        // console.log(response);
        const res = response.data;
        if (res.code === 200) {
          this.$message.success("已清空");
          //刷新数据
          this.requestCart();
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    // 跳转到商品页面
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
  justify-content: flex-end;
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

.box p span.remove {
  cursor: pointer;
  color: #fff;
  font-size: 14px;
}

.box p span.remove:hover {
  color: #f4e5a9;
}

.box button a {
  color: #fff;
  text-decoration: none;
}
</style>
