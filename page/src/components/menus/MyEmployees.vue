<template>
  <div>
    <input
      type="btn"
      class="btn btn-primary float-end w-0 mb-lg-1"
      data-bs-toggle="modal"
      data-bs-target="#addModal"
      value="添加员工"
    />
    <div ID="table">
      <div ID="staff">
        <div class="w-100" style="height: 500px; overflow: auto">
          <table class="table table-bordered table-hover">
            <thead>
              <tr>
                <th>ID</th>
                <th>姓名</th>
                <th>手机号</th>
                <th>电子邮箱</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in list" :key="item.userId" style="height: 100px">
                <td>{{ item.userId }}</td>
                <td>{{ item.userName }}</td>
                <td>{{ item.phone }}</td>
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
        </div>
        <ul
          class="pagination justify-content-center me-5"
          style="position: absolute; bottom: 0"
        >
          <li class="page-item"><a class="page-link" href="#">上一页</a></li>
          <li class="page-item active"><a class="page-link" href="#">1</a></li>
          <li class="page-item"><a class="page-link" href="#">2</a></li>
          <li class="page-item"><a class="page-link" href="#">3</a></li>
          <li class="page-item"><a class="page-link" href="#">4</a></li>
          <li class="page-item"><a class="page-link" href="#">5</a></li>
          <li class="page-item"><a class="page-link" href="#">下一页</a></li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      list: [],
      page: 1, //当前页码
      total: 0, //列表数量总数
      pageSize: 10, //当前页码的数量
    };
  },
  methods: {
    requestAdminData(
      { keywords = null, page = 1, pageSize = this.pageSize, role = 1 },
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
            let { list, total } = res.data;
            this.list = list;
            this.total = total;
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
