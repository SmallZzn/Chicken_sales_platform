<template>
  <div>
    <!-- 搜索 -->
    <div class="float-start">
      <form class="d-flex form-inline me-5 pb-4">
        <input type="text" placeholder="输入...... " class="form-control" />
        <button class="btn btn-primary" type="button">Search</button>
      </form>
    </div>
    <!-- 添加员工 -->
    <input
      type="btn"
      class="btn btn-primary float-end w-0 mb-lg-1"
      data-bs-toggle="modal"
      data-bs-target="#addModal"
      value="添加员工"
    />
    <div class="modal" id="addModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h2 class="modal-title">添加员工</h2>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
            ></button>
          </div>
          <div class="modal-body">
            <form class="w-100 my-lg-5">
              <input
                type="text"
                class="form-control mb-3"
                placeholder="员工姓名"
              />
              <input
                type="text"
                class="form-control mb-3"
                placeholder="联系电话"
              />
              <input
                type="email"
                class="form-control mb-3"
                placeholder="电子邮箱"
              />
              <input
                type="text"
                class="form-control mb-3"
                placeholder="家庭住址"
              />
              <input
                type="submit"
                class="btn btn-primary w-50 btn-margin"
                value="添加员工"
              />
            </form>
          </div>
        </div>
      </div>
    </div>
    <!-- 列表 -->
    <div class="float-end text-center w-100">
      <table class="table table-borderless table-hover table-striped">
        <thead class="table-dark table-light">
          <tr>
            <th>ID</th>
            <th>姓名</th>
            <th>手机号</th>
            <th>电子邮箱</th>
            <th>家庭地址</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in list" :key="item.userId">
            <td>{{ item.userId }}</td>
            <td>{{ item.userName }}</td>
            <td>{{ item.phone }}</td>
            <td>{{ item.email }}</td>
            <td>{{ item.email }}</td>
            <td>
              <button
                type="button"
                class="border-0 bg-body text-primary"
                data-bs-toggle="modal"
                data-bs-target="#editModal"
              >
                编辑
              </button>
              <button type="button" class="border-0 bg-body text-primary">
                删除
              </button>
            </td>
          </tr>
        </tbody>
      </table>
      <!-- 页码 -->
      <ul class="pagination justify-content-center me-5">
        <li class="page-item">
          <a
            class="page-link"
            href="#"
            @click.prevent="page = --page > 0 ? page : 1"
            >上一页</a
          >
        </li>
        <li
          v-for="n in pages"
          :key="n"
          :class="{ 'page-item': true, active: page === n }"
        >
          <a class="page-link" href="#" @click.prevent="page = n">{{ n }}</a>
        </li>
        <li class="page-item">
          <a
            class="page-link"
            href="#"
            @click.prevent="page = ++page < pages ? page : pages"
            >下一页</a
          >
        </li>
      </ul>
    </div>
    <!-- 编辑信息 -->
    <div class="modal" id="editModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h2 class="modal-title">信息编辑</h2>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
            ></button>
          </div>
          <div class="modal-body">
            <form class="w-100 my-lg-5">
              <input
                type="text"
                class="form-control mb-3"
                placeholder="员工姓名"
              />
              <input
                type="text"
                class="form-control mb-3"
                placeholder="联系电话"
              />
              <input
                type="email"
                class="form-control mb-3"
                placeholder="电子邮箱"
              />
              <input
                type="text"
                class="form-control mb-3"
                placeholder="家庭住址"
              />
              <input type="submit" class="btn btn-primary w-50 btn-margin" />
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      list: [
        {
          userId: 2,
          userName: "bin",
          password: "222222",
          email: "bin@example.com",
          phone: "18523654256",
          type: 0,
          permissionList: [
            {
              permissionId: 14,
              permissionName: "新增种类",
            },
            {
              permissionId: 15,
              permissionName: "删除种类",
            },
          ],
        },
      ],
      page: 1, //当前页码
      total: 0, //列表数量总数
      pageSize: 10, //当前页码的数量
      pages: 1, //总页码数
    };
  },
  watch: {
    // 当页面变化时请求数据
    page: function (newVal) {
      console.log(newVal);
      this.requestAdminData({ page: this.page });
    },
  },
  methods: {
    requestAdminData(
      { keywords = null, page = 1, pageSize = this.pageSize, role = 0 },
      successMsg = "成功加载数据"
    ) {
      console.log(page, pageSize);
      this.$api.admin
        .get({
          keywords,
          page: page,
          pageSize,
          type: role,
        })
        .then((response) => {
          console.log(response);
          //状态200时
          const res = response.data;
          if (res.code === 200) {
            this.$message({
              message: successMsg,
              type: "success",
            });
            let { list, total, pages } = res.data;
            this.list = list;
            this.total = total;
            this.pages = pages;
            console.log(this.list, this.total);
          } else {
            this.$message.error(response.data.msg);
          }
        })
        .catch((error) => {
          console.log(error);
          this.$message.error("出错了");
        });
    },
  },
  mounted: function () {
    //发送请求，请求第一页
    this.requestAdminData({});
  },
};
</script>

<style></style>
