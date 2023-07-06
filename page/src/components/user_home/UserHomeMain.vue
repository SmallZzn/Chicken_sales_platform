<template>
  <div>
    <!-- banner部分 -->
    <div class="banner">
      <div class="banner-btn">
        <button
          type="button"
          class="btn btn-primary btn-lg banner-btn1"
          @click="onPurchase"
        >
          <span class="glyphicon glyphicon-shopping-cart"></span>采购
        </button>
        <button type="button" class="btn btn-primary btn-lg banner-btn2">
          <span class="glyphicon glyphicon-flash"></span>折扣
        </button>
      </div>
    </div>
    <!-- banner部分结束-->

    <!-- 轮播图部分 -->
    <div class="lunbotu">
      <div class="lb-list">
        <div>
          <img src="/images/lunbotu/01.jpg" alt="" />
          <div class="mask"></div>
        </div>
        <div>
          <img src="/images/lunbotu/02.jpg" alt="" />
          <div class="mask"></div>
        </div>
        <div>
          <img src="/images/lunbotu/03.jpg" alt="" />
          <div class="mask"></div>
        </div>
        <div>
          <img src="/images/lunbotu/04.jpg" alt="" />
          <div class="mask"></div>
        </div>
        <div>
          <img src="/images/lunbotu/05.jpg" alt="" />
          <div class="mask"></div>
        </div>
        <div>
          <img src="/images/lunbotu/06.jpg" alt="" />
          <div class="mask"></div>
        </div>
      </div>
    </div>
    <!-- 轮播图部分结束-->

    <!-- 商品部分 -->
    <div class="panel">
      <goods-home-show></goods-home-show>
    </div>
    <!-- 商品部分结束 -->
  </div>
</template>

<script>
import Visual from "./Visual.vue";
import GoodsCard from "../goods/GoodsCard.vue";
import GoodsHomeShow from "../goods/GoodsHomeShow.vue";

export default {
  components: {
    Visual,
    GoodsCard,
    GoodsHomeShow,
  },

  mounted: function () {
    let list = document.querySelector(".lb-list");
    let box = document.querySelector(".lunbotu");
    //复制一份
    list.innerHTML += list.innerHTML;
    let left = 0;

    let timer;

    function move() {
      clearInterval(timer);

      timer = setInterval(function () {
        left -= 2;
        if (left === -(6 * 200 + 5 * 10)) {
          left = 0;
        }
        list.style.left = left + "px";
      }, 20);
    }

    move();

    box.onmouseenter = function () {
      // console.log("clear")
      clearInterval(timer);
    };

    box.onmouseleave = function () {
      // console.log("move")
      move();
    };
  },
  methods: {
    onLogout() {
      //token
      localStorage.removeItem("token1");
      this.$https.post("/logout").then((response) => {
        let res = response.data;
        if (res.code === 200) {
          this.$message({
            message: res.msg,
            type: "success",
          });
        } else {
          this.$message.error(res.msg);
        }
        //跳转到login
        this.$router.push("/login");
      });
    },
    onPurchase() {
      this.$router.push({
        name: "SearchGoods",
      });
    },
  },
};
</script>

<style scoped>
/* banner部分 */
.banner {
  position: relative;
  height: 700px;
  background: url(@/assets/images/banner.jpeg) no-repeat;
  background-size: cover;
  background-position: center;
}
.banner-btn {
  position: absolute;
  right: 150px;
  bottom: 50px;
}

.banner-btn1,
.banner-btn2 {
  font-size: 16px;
  background-color: rgb(148, 187, 5);
  border: 1px solid rgb(148, 187, 5);
  border-radius: 10px;
}

.banner-btn2 {
  margin-left: 30px;
}

.banner-btn1:hover {
  background-color: #fff;
}

.banner-btn1:hover {
  color: rgb(148, 187, 5);
}

.banner-btn2:hover {
  background-color: #fff;
  color: rgb(148, 187, 5);
}
/* banner部分结束 */

/* 轮播图 */
.lunbotu {
  position: relative;
  display: flex;
  margin: 0 auto;
  width: 1300px;
  height: 100px;
  margin-top: 20px;
  overflow: hidden;
  background-color: #f5f5f5;
}

.lunbotu .lb-list img {
  width: 200px;
  height: 100px;
  border-radius: 20px;
}

.lunbotu > .lb-list {
  position: absolute;
  left: 0;
  display: flex;
  align-items: center;
  justify-content: center;
}

.lb-list > div {
  width: 200px;
  height: 100px;
  margin-right: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
}

/* 轮播图部分结束 */
</style>
