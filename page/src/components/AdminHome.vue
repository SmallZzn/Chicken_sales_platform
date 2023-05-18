<template>
  <div class="home-container">
    <aside
      class="w-auto h-100 m-lg-0 d-flex flex-column p-4 text-white float-start bg-dark"
    >
      <hr />
      <ul class="nav nav-pills flex-column mb-auto p-2 text-body">
        <li>
          <button type="button" class="nav-link text-white active">
            员工管理
          </button>
        </li>
        <li>
          <button type="button" class="nav-link text-white">商品管理</button>
        </li>
      </ul>
      <hr />
      <div>
        <a
          href="#"
          class="d-flex align-items-center text-white text-decoration-none"
        >
          <img
            src="https://github.com/mdo.png"
            alt=""
            width="32"
            height="32"
            class="rounded-circle me-2"
          />
          <strong>管理员</strong>
        </a>
      </div>
    </aside>
    <article>
      <header
        class="navbar navbar-expand-sm text-dark p-2 ms-0 bg-white border border-2"
      >
        <div class="navbar-brand me-auto fs-4">管理页面</div>
        <div class="dropdown float-end me-5">
          <button
            type="button"
            class="btn btn-primary dropdown-toggle"
            data-bs-toggle="dropdown"
          >
            下拉
          </button>
          <div class="dropdown-menu">
            <a class="dropdown-item" href="#">链接 1</a>
            <a class="dropdown-item" href="#">链接 2</a>
            <a class="dropdown-item" href="#">链接 3</a>
          </div>
        </div>
        <form class="float-end">
          <input type="submit" class="btn btn-primary" value="退出" />
        </form>
      </header>
      <div class="myContainer mt-lg-3 container border border-1">
        <router-view></router-view>
      </div>
    </article>
  </div>
</template>

<script>
import MyEmployees from "./menus/MyEmployees.vue";

export default {
  name: "MyHome",
  // 注册组件
  components: {
    MyEmployees,
  },
  data() {
    return {
      isActive: false,
    };
  },
  methods: {
    onLogout() {
      this.$api.user.logout().then((response) => {
        // console.log(response);
        const res = response.data;
        if (res.code === 200) {
          this.$message.success(response.data.msg);
          //移除
          localStorage.removeItem("loginId");
          localStorage.removeItem("loginName");
          // 刷新页面
          this.$router.push("/login");
        } else {
          this.$message.error(response.data.msg);
        }
      });
    },
  },
};
</script>

<!-- 引入administrator样式 -->
<style src="../assets/css/administrator.css" scoped />

<!-- 引入bootstrap.css -->
<style src="../assets/css/bootstrap.css" />

<style lang="less" scoped>
.home-container {
  height: 100%;
  display: flex;
  flex-direction: column;

  .home-main-box {
    height: 100%;
    display: flex;

    .home-main-body {
      padding: 15px;
      flex: 1;
    }
  }
}

.nav-link a {
  text-decoration: none;
}
</style>
