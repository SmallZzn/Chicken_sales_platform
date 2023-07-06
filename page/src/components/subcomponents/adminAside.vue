<template>
  <div style="background-color: #545c64">
    <el-aside width="256px" class="el-aside">
      <el-menu
        :default-openeds="['1']"
        default-active="1-1"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
      >
        <el-submenu index="1" class="el-menu">
          <el-menu-item index="1-1" @click="showTable('product', 1)"
          ><i class="el-icon-goods"></i>产品管理</el-menu-item
          >
          <template slot="title"><i class="el-icon-menu"></i>管理</template>
          <el-menu-item index="1-2" @click="showTable('order', 2)"
            ><i class="el-icon-s-order"></i>订单管理</el-menu-item
          >
          <el-menu-item index="1-3" @click="showTable('user', 3)"
            ><i class="el-icon-s-custom"></i>用户管理</el-menu-item
          >
          <el-submenu index="1-4">
            <template slot="title"
              ><i class="el-icon-user-solid"></i>员工管理</template
            >
            <el-menu-item index="1-4-1" @click="showTable('editInfo', 4)"
              ><i class="el-icon-edit"></i>员工信息</el-menu-item
            >
            <el-menu-item index="1-4-2" @click="showTable('editPermis', 4)"
              ><i class="el-icon-unlock"></i>员工权限</el-menu-item
            >
          </el-submenu>
          <el-menu-item index="1-5" @click="showTable('category', 5)"
            ><i class="el-icon-s-grid"></i>种类管理</el-menu-item
          >
          <el-menu-item index="1-6" @click="showTable('saleInfo', 6)"
          ><i class="el-icon-s-data"></i>销售情况</el-menu-item
          >
        </el-submenu>
      </el-menu>
    </el-aside>
  </div>
</template>

<script>
export default {
  name: "adminMenus",
  methods: {
    showTable(display, num) {
      this.$emit("showTable", display, num);
    },
  },
};
</script>

<style scoped>
.el-aside {
  text-align: left;
}

.el-menu {
  height: 100%;
}
nav {
  flex: 0 0 200px;
  background-color: #65b04f;
  color: #fff;
}

nav a {
  display: block;
  padding: 1rem;
  color: #fff;
  text-decoration: none;
  transition: background-color 0.3s ease;
}

nav a:hover,
nav a.active {
  background-color: #4c8f3a;
}
</style>
