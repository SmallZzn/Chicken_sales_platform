<template>
  <div style="min-width: 1300px">
    <!-- 头部 -->
    <header-top></header-top>
    <header-part @search="onSearch"></header-part>
    <!-- 头部结束 -->

    <div style="margin-bottom: 50px">
      <router-view></router-view>
    </div>

    <!-- visual部分 -->
    <visual></visual>
    <!-- visual部分结束 -->

    <!-- footer 部分 -->
    <home-footer></home-footer>
    <!-- footer部分结束 -->
  </div>
</template>

<script>
import Logout from "./user/Logout.vue";
import HeaderTop from "./user_home/HeaderTop.vue";
import HeaderPart from "./user_home/HeaderPart.vue";
import Visual from "./user_home/Visual.vue";
import HomeFooter from "./user_home/HomeFooter.vue";

export default {
  components: {
    Logout,
    HeaderTop,
    HeaderPart,
    Visual,
    HomeFooter,
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
    onSearch(value) {
      // console.log(value)
      // 显示搜索结果
      this.$router.push(
        {
          name: "SearchGoods",
          params: {
            productName: value,
          },
        },
        () => {},
        () => {}
      );
    },
  },
};
</script>

<!-- 引入各种样式 -->
<style src="../assets/css/index.css" />

<style scoped>
.user_container {
  background-color: #f3f5f7;
}
</style>
