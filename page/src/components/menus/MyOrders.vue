<template>
  <div>
    <table>
      <tr class="title">
        <td>用户名</td>
        <td>总价</td>
        <td>总数</td>
        <td>下单日期</td>
        <td>操作</td>
      </tr>
      <tr v-for="item in goodsList" :key="item.productId">
        <td>{{ item.userName }}</td>
        <td>￥{{ item.totalprice }}</td>
        <td>{{ item.num }}</td>
        <td>{{ item.orderdate }}</td>
        <td class="number">查看详情 删除 修改</td>
      </tr>
    </table>
    <div class="box">
      <button type="submit" class="btn btn-default btn-warning">
        <a href="order.html">移除订单</a>
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
          userName: "john",
          totalprice: 210.32,
          num: 5,
          orderdate: "  2023 - 1 - 2 ",
        },
        {
          userName: "Kate",
          totalprice: 153.2,
          num: 4,
          orderdate: " 2023 - 1 - 7  ",
        },
        {
          userName: "Linda",
          totalprice: 93.2,
          num: 3,
          orderdate: "  2023 - 1 - 30",
        },
        {
          userName: "Michal",
          totalprice: 22.5,
          num: 1,
          orderdate: " 2023 - 2 - 15 ",
        },
        {
          userName: "Nancy",
          totalprice: 423.6,
          num: 9,
          orderdate: "2023 - 1 - 23  ",
        },
        {
          userName: "Oliver",
          totalprice: 165.8,
          num: 4,
          orderdate: "2023 - 3 - 23  ",
        },
      ],
      isFull: false,
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
          this.goodsList = res.data.list;
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
    // 选择所有商品
    onSelectAll() {
      if (!this.isFull) {
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
    onClickSubmit() {},
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
