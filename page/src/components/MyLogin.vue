<template>
  <div class="login">
    <div class="login_container">
      <div class="row justify-content-center">
        <div class="text-center">
          <a href="#/user" title="首页"
            ><img src="/images/logo.png" alt="" class="lo-logo"
          /></a>
        </div>

        <div class="text-center box">
          <span class="text-center lo-welcome">欢迎!</span>
          <p class="lo-uname">
            用户名登陆 /
            <a href="#/user" style="color: #119744; text-decoration: none"
              >首页</a
            >
          </p>

          <div class="lo-form">
            <div class="form-group">
              <input
                type="text"
                v-model="userName"
                class="form-control"
                placeholder="用户名"
              />
            </div>
            <div class="form-group">
              <input
                type="password"
                v-model="password"
                class="form-control"
                placeholder="输入密码"
              />
            </div>
            <div class="form-button">
              <div v-loading="loading">
                <button @click="onLoginClick">
                  <span>登录</span>
                </button>
              </div>
              <p>
                忘记密码?
                <a href="reset.html" style="color: #119744">重置密码</a>
              </p>
            </div>
          </div>
        </div>
      </div>

      <div class="row justify-content-center">
        <div class="text-center box">
          <span
            >没有账号
            <a href="#/register" style="text-decoration: none"
              >点击注册</a
            ></span
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userName: "",
      password: "",
      loading: false,
    };
  },
  methods: {
    onLoginClick() {
      //显示加载
      this.loading = true;
      this.$api.user
        .login(this.userName, this.password)
        .then((response) => {
          console.log(response);
          //隐藏加载
          this.loading = false;
          let res = response.data;
          if (res.code === 200) {
            this.$message({
              message: "成功登陆",
              type: "success",
            });
            //保存当前登陆用户的ID
            localStorage.setItem("loginId", res.data.userId);
            //保存当前登陆用户的用户名
            localStorage.setItem("loginName", res.data.userName);
            if (res.data.type === 1) {
              this.$router.push("/admin");
            } else {
              this.$router.push("/user");
            }
          } else {
            this.$message({
              message: res.msg,
              type: "error",
            });
          }
        })
        .catch(() => {
          this.loading = false;
        });
    },
  },
};
</script>

<style scoped>
body {
  background-color: #f5f5f5;
}

.login {
  padding-top: 30px;
}

.login_container {
  width: 400px;
  margin: 0 auto;
}

.lo-logo {
  width: 250px;
  height: 80px;
}

.box {
  margin-top: 30px;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  background-color: #fff;
  border: 1px solid #e8e8e8;
}

.lo-welcome {
  font-size: 28px;
  color: #119744;
  font-weight: 700;
}

.lo-uname {
  font-size: 16px;
}

.lo-form input {
  margin: 0 auto;
  width: 300px;
  height: 45px;
  background-color: #f5f5f5;
}

.lo-form button {
  width: 300px;
  height: 45px;
  background-color: #119744;
  border: 1px solid #119744;
  border-radius: 10px;
  color: #fff;
}

.lo-form button span a {
  color: #fff;
  text-decoration: none;
}

.box2 {
  margin-top: 30px;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  background-color: #fff;
  border: 1px solid #e8e8e8;
}
</style>
