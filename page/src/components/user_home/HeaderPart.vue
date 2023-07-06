<template>
  <!-- 头部二 -->
  <div class="header-part">
    <div class="container-fluid">
      <div class="row">
        <!-- logo -->
        <div class="col-md-2">
          <a href="#/user/home">
            <img src="/images/logo.png" alt="" class="logo" />
          </a>
        </div>

        <!-- 搜索框 -->
        <div class="col-lg-4 search">
          <div class="input-group">
            <input
              type="text"
              @keydown.enter="onSearchClick"
              v-model="searchStr"
              placeholder="搜索想要的商品吧"
              class="form-control"
            />
            <span class="input-group-btn">
              <button
                class="btn btn-default"
                type="button"
                @click="onSearchClick"
              >
                <span class="glyphicon glyphicon-search"></span>
              </button>
            </span>
          </div>
        </div>

        <!-- 购物车 -->
        <div class="col-md-2">
          <a href="#/user/cart" target="_blank" style="color: #000">
            <button class="btn-lg sp" title="购物车">
              <span class="glyphicon glyphicon-shopping-cart"> 00.00 </span>
            </button>
          </a>
        </div>

        <!-- 我的订单 -->
        <div class="col-md-2">
          <!-- 跳转到订单页面 -->
          <router-link
            :to="{ name: 'UserMe', params: { pageIndex: 4 } }"
            style="color: #000"
          >
            <button class="btn-lg sp order" title="我的订单">
              <span class="glyphicon glyphicon-list-alt"> 我的订单 </span>
            </button>
          </router-link>
        </div>

        <!-- 登陆 -->
        <div class="col-md-1 user_info">
          <a :href="isLogin ? '#/user/me' : '#/login'" target="_blank">
            <img v-if="!isLogin" src="/images/noLogin.png" class="user-img" />
            <img v-else :src="getImgUrl(avatarImgName)" class="user-img" />
            <span v-if="!isLogin" class="uname">未登陆</span>
            <span v-else class="uname">
              {{ loginName }}
            </span>
          </a>
        </div>
        <div class="col-md-1">
          <a v-if="!isLogin" href="#/login">
            <button type="button" class="btn btn-primary btn-lg user">
              <span class="glyphicon glyphicon-user"></span>登陆
            </button>
          </a>
          <logout v-else>
            <button type="button" class="btn btn-primary btn-lg user">
              <span class="glyphicon glyphicon-share"></span>退出
            </button>
          </logout>
        </div>
      </div>
    </div>
  </div>
  <!-- 头部二结束 -->
</template>

<script>
import request from "@/utils/request";
import Logout from "../user/Logout.vue";

export default {
  components: { Logout },
  data() {
    return {
      loginId: null,
      loginName: "",
      avatarImgName: null,
      isLogin: false,
      searchStr: null,
    };
  },
  created: function () {
    this.isLogin = true;
    //获得当前登陆用户id
    const loginId = localStorage.getItem("loginId");
    const loginName = localStorage.getItem("loginName");
    if (!loginId) {
      this.isLogin = false;
      // this.$message.error('请先登陆')
    } else {
      this.isLogin = true;
      this.loginId = loginId;
      this.loginName = loginName;
      this.avatarImgName = this.getAvatarImgName(loginId);
    }
  },
  methods: {
    onSearchClick() {
      this.$emit("search", this.searchStr);
    },
    //获取头像图片名称
    getAvatarImgName(userId) {
      request
        .get("/user/selectUserInfo", {
          params: {
            page: this.addressPage,
            pageSize: this.addressPageSize,
            userId,
          },
        })
        .then((res) => {
          console.log(res.data.data);
          this.avatarImgName = res.data.data.image;
        });
    },
    //下载图片
    getImgUrl(img) {
      return `http://localhost:9999/file/download?fileName=${img}`;
    },
  },
};
</script>

<style scoped>
/* 头部二 */
.header-part {
  background-color: #fff;
  /* position: fixed; */
}

.logo {
  width: 250px;
  height: 80px;
  margin-left: -10px;
}

.search {
  margin-top: 25px;
}

.search .form-control {
  border: 2px solid #119744;
  border-right: 0;
  border-radius: 8px;
  margin-left: 30px;
}

.search button {
  border: 2px solid #119744;
  border-left: 0;
  /* 使得边框不会挤开 */
  padding: 5px 12px;
  border-radius: 8px;
}

.search .input-group-btn:last-child > .btn {
  z-index: 3;
}

.search .input-group-btn:last-child > .btn:focus {
  border: 2px solid #119744;
  border-left: 0;
  padding: 5px 12px;
  border-radius: 8px;
  outline: 1px auto #119744;
}

.sp {
  margin-top: 20px;
  margin-left: 40px;
  font-size: 18px;
  border: 1px solid transparent;
  border-radius: 99999px;
}

.order {
  margin-top: 20px;
  margin-left: -20px;
  font-size: 17px;
  border: 1px solid transparent;
  border-radius: 99999px;
}

.sp a {
  text-decoration: none;
  color: black;
}

.sp:hover {
  color: #fff;
  background-color: #119744;
}

.sp:hover a {
  color: #fff;
}

.user_info a {
  color: black;
  text-decoration: none;
}

.user_info a:hover {
  color: #119744;
}

.user-img {
  width: 40px;
  height: 40px;
  margin-top: 20px;
  border-radius: 99px;
  margin-left: -50px;
}

.uname {
  display: block;
  font-size: 16px;
  margin-top: -30px;
  width: 80px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.user {
  margin-top: 18px;
  font-size: 16px;
  background-color: #119744;
}

.user a {
  color: white;
}

.user:hover {
  color: #119744;
  background-color: #fff;
}

.user:hover a {
  color: #119744;
}

/* 头部二结束 */
</style>
