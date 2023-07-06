<template>
  <div class="login">
    <div class="register_container">
      <div class="row justify-content-center">
        <div class="text-center">
          <a href="#/user" title="首页"
            ><img src="images/logo.png" alt="" class="lo-logo"
          /></a>
        </div>

        <div class="text-center box">
          <span class="text-center lo-welcome">立即注册!</span>
          <p class="lo-uname">只需几分钟</p>

          <div class="lo-form">
            <div class="form-group">
              <input
                v-model="userName"
                type="text"
                class="form-control"
                placeholder="用户名"
              />
            </div>
            <div class="form-group">
              <input
                v-model="password"
                type="password"
                class="form-control"
                placeholder="输入密码"
              />
            </div>
            <div class="form-group">
              <input
                v-model="rePassword"
                type="password"
                class="form-control"
                placeholder="确认密码"
              />
            </div>
            <div class="form-button">
              <div v-loading="loading">
                <button @click="onLogonClick">
                  <span>注册</span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row justify-content-center">
        <div class="text-center box">
          <span
            >已有账号？<a href="#/login" style="text-decoration: none"
              >立即去登陆</a
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
      rePassword: "",
      loading: false,
    };
  },
  methods: {
    onLogonClick() {
      // 判断密码是否输入一致
      if (this.password == this.rePassword) {
        this.register();
      } else {
        this.$message({
          message: "两次输入密码不一致！",
          type: "error",
        });
      }
    },
    register() {
      //显示加载
      this.loading = true;
      this.$api.user
        .register({
          userName: this.userName,
          password: this.password,
        })
        .then((response) => {
          //隐藏加载
          this.loading = false;
          // console.log(response);
          let res = response.data;
          if (res.code === 200) {
            this.$message({
              message: "注册成功！",
              type: "success",
            });
            //跳转到登陆界面
            this.$router.push("/login");
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

.register_container {
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
