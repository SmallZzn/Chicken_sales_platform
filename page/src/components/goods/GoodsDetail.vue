<template>
  <div class="goods_detail">
    <div class="product">
      <img
        class="product-img"
        :src="getImgUrl(product.image)"
        :model="product"
        alt="Product Image"
      />
      <div class="product-info">
        <h1 class="product-name" >{{ product.productName }}</h1>
        <h2 class="product-price">价格：{{ product.price }}</h2>
        <h2 class="product-weight">重量: {{ product.weight }}KG</h2>
        <h2 class="product-origin">产地: {{ product.origin }}</h2>
        <h2 class="product-category">
          种类: <a>{{ product.category === 1 ? "肉鸡" : "蛋鸡" }}</a>
        </h2>
        <h2 class="product-inventory">库存: {{ product.inventory }}</h2>
        <h2 class="product-sales">销量: {{ product.sales }}</h2>
        <h2 class="product-description">{{ product.description }}</h2>
        <button class="add-to-cart" @click="addCart">Add to Cart</button>
      </div>
    </div>

    <div >
      <h1 style="font-weight: bolder">评价</h1>
      <div class="comment" v-for="review in reviewList" :key="review.reviewId">
        <div style="text-align: left">
          <img v-bind:src="getImgUrl(review.userImage)" class="commentImg" />
          <span class="commonUserName">{{review.userName}}</span>
        </div>
        <p class="commentContent">{{ review.comment }}</p>
        <p class="commentDate">{{ review.createTime }}</p>
      </div>
      <br />
      <el-pagination
        background
        layout="prev, pager, next"
        :total="reviewTotal"
        :current-page="reviewPage"
        :page-size="reviewPageSize"
        @current-change="changeReviewPage"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //产品信息
      product: {
        productId: 1,
        productName: "",
        description: "",
        price: 0,
        weight: 0,
        origin: "",
        category: 0,
        inventory: 0,
        image: "",
        sales: 0,
      },
      //商品评价
      reviewList: [
        {
          reviewId: 4,
          userId: 2,
          productId: 2,
          rating: 5,
          comment: "还行",
          createTime: 1680160008000,
          userName: "",
          productName: "黑羽肉鸡",
          userImage: "",
          productImage: "",
        },
      ],
      reviewTotal: 1,
      reviewPage: 1,
      reviewPageSize: 5,
    };
  },
  created() {
    const productId = this.$route.params.id;
    this.product.productId = productId;
    this.selectProductInfo(productId);
    this.selectProductReview();
  },
  methods: {
    //查找商品信息
    selectProductInfo(productId) {
      this.$api.products
        .getProductInfo(productId)
        .then((res) => {
          console.log(res.data.data);
          this.product.productId = res.data.data.productId;
          this.product.productName = res.data.data.productName;
          this.product.description = res.data.data.description;
          this.product.price = res.data.data.price;
          this.product.weight = res.data.data.weight;
          this.product.origin = res.data.data.origin;
          this.product.category = res.data.data.category;
          this.product.inventory = res.data.data.inventory;
          this.product.sales = res.data.data.sales;
          this.product.image = res.data.data.image;
        })
        .catch(function (err) {
          alert(err);
        });
    },
    //下载图片
    getImgUrl(img) {
      return `http://localhost:9999/file/download?fileName=${img}`;
    },
    //改变评论页数
    changeReviewPage(page) {
      this.reviewPage = page;
      this.selectProductReview();
    },
    //查看产品评价
    selectProductReview() {
      const params = {
        page: this.reviewPage,
        pageSize: this.reviewPageSize,
        productId: this.product.productId,
      };
      this.$api.review
        .selectProductReview(params)
        .then((res) => {
          console.log(res);
          this.reviewList = res.data.data.list;
          this.reviewTotal = res.data.data.total;
        })
        .catch(function (err) {
          alert(err);
        });
    },
    addCart() {
      // let params = {
      //   productId: ,
      //   quantity: 1,
      // };
      this.$api.cart.add(this.product.productId).then((response) => {
        // console.log(response);
        const res = response.data;
        if (res.code === 200) {
          this.$message.success("加入购物车成功");
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
  },
};
</script>

<style>
.goods_detail {
  font-family: Arial, sans-serif;
  background-color: #f2f2f2;
  text-align: center;
}

.product {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  margin: 50px auto;
  width: 90%;
  max-width: 1200px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.product-img {
  width: 30%;
  height: 450px;
  margin: 50px;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.product-info {
  width: 40%;
  margin: 50px;
}

.product-name {
  font-size: 32px;
  margin: 0 0 30px;
  font-weight: bolder;
}

.product-price {
  font-size: 24px;
  margin: 0 0 20px;
}

.product-weight {
  font-size: 18px;
  margin: 0 0 15px;
}

.product-origin {
  font-size: 18px;
  margin: 0 0 15px;
}

.product-category {
  font-size: 18px;
  margin: 0 0 15px;
}

.product-inventory {
  font-size: 18px;
  margin: 0 0 15px;
}

.product-sales {
  font-size: 18px;
  margin: 0 0 20px;
}

.product-description {
  font-size: 18px;
  margin: 0 0 15px;
}

.add-to-cart {
  display: inline-block;
  padding: 10px 30px;
  background-color: green;
  color: #fff;
  border: none;
  border-radius: 5px;
  font-size: 20px;
  cursor: pointer;
  margin-top: 30px;
  transition: background-color 0.3s ease-in-out;
}

.add-to-cart:hover {
  background-color: white;
  border: #4c8f3a solid 3px;
  color: green;
}

.comment-section {
  margin: 50px auto;
  width: 80%;
  max-width: 1200px;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

.comment {
  width: 80%;
  padding: 10px;
  border-radius: 10px;
  background-color: white;
  margin-bottom: 40px;
  margin-left: 150px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.commentImg {
  width: 100px;
  height: 100px;
}

.commentContent {
  font-size: 18px;
}

.commonUserName{
  font-size: 25px;
  font-weight: bold;
  color: #000;
  vertical-align: top;
  margin-left: 19px;
}

.commentDate {
  font-size: 18px;
  margin-left: 85%;
}

.comment-name {
  font-size: 20px;
  font-weight: bold;
  margin: 0 0 5px;
}

.comment-content {
  font-size: 16px;
  margin: 0;
}

.comment-date {
  font-size: 14px;
  color: #666;
  margin-top: 5px;
}

.comment-img {
  width: 30px;
  height: 30px;
  vertical-align: middle;
}
</style>
