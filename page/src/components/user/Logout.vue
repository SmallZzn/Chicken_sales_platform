<template>
  <div @click="Logout" style="display: inline-block">
    <slot></slot>
    <!-- <button @click="Logout">退出</button> -->
  </div>
</template>

<script>
export default {
  methods: {
    Logout() {
      this.$api.user
        .logout()
        .then((response) => {
          // console.log(response);
          const res = response.data;
          if (res.code === 200) {
            this.$message.success(response.data.msg);
          } else {
            this.$message.error(response.data.msg);
          }
        })
        .catch((error) => {
          this.$message.error("网络出错！");
        });
      //移除
      localStorage.removeItem("loginId");
      localStorage.removeItem("loginName");
      // 返回首页
      this.$router.push("/user");
    },
  },
};
</script>

<style></style>
