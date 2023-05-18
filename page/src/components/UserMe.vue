<template>
  <div class="userInfo">
    <!-- 头部 -->
    <header>
      <div class="container header-container">
        <div class="user-info">
          <img
            v-bind:src="getImgUrl(user.image)"
            alt="Profile picture"
            class="headImage"
          />
          <div class="user-details">
            <h3>{{ user.userName }}</h3>
            <p>{{ user.email }}</p>
          </div>
        </div>
        <div class="nav-toggle">
          <i class="fas fa-bars"></i>
        </div>
        <logout>
          <img
            src="../assets/images/logout.png"
            class="logout"
            title="退出登录"
          />
        </logout>
      </div>
    </header>

    <div class="container">
      <nav>
        <a
          href="#"
          v-bind:class="index === 1 ? 'active' : ''"
          @click="changeIndex(1)"
        >
          <i class="fas fa-user"></i>
          My Information
        </a>
        <a
          href="#"
          v-bind:class="index === 2 ? 'active' : ''"
          @click="selectReview(2)"
        >
          <i class="fas fa-star"></i>
          My Reviews
        </a>
        <a
          href="#"
          v-bind:class="index === 3 ? 'active' : ''"
          @click="changeIndex(3)"
        >
          <i class="fas fa-map-marker-alt"></i>
          My Addresses
        </a>
        <a
          href="#"
          v-bind:class="index === 4 ? 'active' : ''"
          @click="selectOrders(4)"
        >
          <i class="fas fa-shopping-cart"></i>
          My Orders
        </a>
      </nav>
      <div class="content">
        <!--我的信息-->
        <div v-show="index === 1">
          <div class="card">
            <div class="card-details">
              <table class="myInfo">
                <tr>
                  <td>
                    <h1>{{ user.userName }}</h1>
                  </td>
                </tr>
                <tr class="myInfoTr">
                  <td>性别：{{ user.sex === 1 ? "男" : "女" }}</td>
                </tr>
                <tr class="myInfoTr">
                  <td>电话: {{ user.phone }}</td>
                </tr>
                <tr class="myInfoTr">
                  <td>电子邮箱：{{ user.email }}</td>
                </tr>
                <tr class="myInfoTr">
                  <td>
                    <button
                      class="changeInfo"
                      @click="changeInfoVisible = true"
                    >
                      修改信息
                    </button>
                  </td>
                </tr>
              </table>
            </div>
          </div>
        </div>

        <!--我的评论-->
        <div v-show="index === 2">
          <!--TODO 修改 -->
          <div
            class="comment"
            v-for="review in reviewList"
            :key="review.reviewId"
          >
            <img
              v-bind:src="getImgUrl(review.productImage)"
              class="commentImg"
            />
            <a
              :href="'#/user/goods/' + review.productId"
              target="_blank"
              class="productName"
              >{{ review.productName }}</a
            >
            <p class="commentContent">{{ review.comment }}</p>
            <p class="commentDate">{{ review.createTime }}</p>
            <template>
              <el-button
                type="text"
                class="deleteReview"
                @click.prevent="deleteReview(review.reviewId)"
                >删除</el-button
              >
            </template>
          </div>
          <br />
          <el-pagination
            background
            layout="prev, pager, next"
            :total="reviewTotal"
            :current-page="reviewPage"
            :page-size="reviewPageSize"
            @current-change="changeReviewPage"
          >
          </el-pagination>
        </div>

        <!--我的地址-->
        <div v-show="index === 3">
          <button class="addAddress" @click="addAddress">添加地址</button>
          <table class="list">
            <thead>
              <tr>
                <th>收货人</th>
                <th>性别</th>
                <th>电话</th>
                <th>省</th>
                <th>市</th>
                <th>区</th>
                <th>详细地址</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="address in addressList" :key="address.id">
                <td class="info">{{ address.receiver }}</td>
                <td class="info">{{ address.sex === 1 ? "男" : "女" }}</td>
                <td class="info">{{ address.phone }}</td>
                <td class="info">{{ address.province }}</td>
                <td class="info">{{ address.city }}</td>
                <td class="info">{{ address.region }}</td>
                <td class="info">{{ address.region }}</td>
                <td class="info">
                  <a class="edit" @click.prevent="editAddress(address)">编辑</a>
                  <template>
                    <el-button
                      type="text"
                      class="delete"
                      @click.prevent="deleteAddress(address.addressId)"
                      >删除</el-button
                    >
                  </template>
                  <br />
                  <button
                    class="defaultButton"
                    v-show="address.isdefault === 0"
                    @click.prevent="setDefault(address)"
                  >
                    设为默认
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
          <br />
          <el-pagination
            background
            layout="prev, pager, next"
            :total="addressTotal"
            :current-page="addressPage"
            :page-size="addressPageSize"
            @current-change="changeAddressPage"
          >
          </el-pagination>
        </div>

        <!--我的订单-->
        <div v-show="index === 4">
          <table class="list">
            <tr class="info">
              <td>订单号</td>
              <td>用户名</td>
              <td>下单日期</td>
              <td>商品数量</td>
              <td>订单总价</td>
              <td>订单状态</td>
              <td>操作</td>
            </tr>
            <tr
              class="info"
              v-for="order in orderList"
              v-bind:key="order.orderId"
            >
              <td>{{ order.orderId }}</td>
              <td>{{ user.userName }}</td>
              <td>{{ order.orderdate }}</td>
              <td>{{ order.num }}</td>
              <td>{{ order.totalprice }}</td>
              <td>{{ order.status }}</td>
              <td>
                <a
                  :href="'#/user/order/' + order.orderId"
                  target="_blank"
                  class="edit"
                  >查看</a
                >
              </td>
            </tr>
          </table>
          <br />
          <el-pagination
            background
            layout="prev, pager, next"
            :total="orderTotal"
            :current-page="orderPage"
            :page-size="orderPageSize"
            @current-change="changeOrderPage"
          >
          </el-pagination>
        </div>

        <!--用户信息修改界面-->
        <el-dialog
          title="信息修改"
          :visible.sync="changeInfoVisible"
          width="30%"
          @close="cancelChangeInfo"
          center
        >
          <el-form
            label-width="100px"
            :rules="rules"
            :model="tempUser"
            ref="updateInfoFrom"
          >
            <el-upload
              action="http://localhost:9999/file/upload"
              :on-success="handleUploadSuccess"
              :before-upload="beforeUpload"
              :show-file-list="false"
            >
              <el-image
                :src="getImgUrl(tempUser.image)"
                class="changeHeadImage"
              ></el-image>
            </el-upload>
            <el-form-item label="用户名" prop="userName">
              <el-input v-model="tempUser.userName"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="sex">
              <el-radio-group v-model="tempUser.sex">
                <el-radio :label="1">男</el-radio>
                <el-radio :label="0">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="tempUser.password"></el-input>
            </el-form-item>
            <el-form-item label="电话" prop="phone">
              <el-input v-model="tempUser.phone"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="tempUser.email"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="cancelChangeInfo">取 消</el-button>
            <el-button type="primary" @click="changeInfo">确 定</el-button>
          </span>
        </el-dialog>

        <!--地址修改页面-->
        <el-dialog
          title="地址修改"
          :visible.sync="changeAddressVisible"
          width="30%"
          @close="cancelChangeAddress"
          center
        >
          <el-form
            label-width="100px"
            :rules="rules"
            :model="tempAddress"
            ref="updateAddressFrom"
          >
            <el-form-item label="收货人" prop="receiver">
              <el-input v-model="tempAddress.receiver"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="sex">
              <el-radio-group v-model="tempAddress.sex">
                <el-radio :label="1">男</el-radio>
                <el-radio :label="0">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="电话" prop="phone">
              <el-input v-model="tempAddress.phone"></el-input>
            </el-form-item>
            <el-form-item label="省" prop="">
              <el-input v-model="tempAddress.province"></el-input>
            </el-form-item>
            <el-form-item label="市" prop="">
              <el-input v-model="tempAddress.city"></el-input>
            </el-form-item>
            <el-form-item label="区" prop="">
              <el-input v-model="tempAddress.region"></el-input>
            </el-form-item>
            <el-form-item label="详细地址">
              <el-input v-model="tempAddress.detail"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="cancelChangeAddress">取 消</el-button>
            <el-button type="primary" @click="sureEditAddress()"
              >确 定</el-button
            >
          </span>
        </el-dialog>

        <!--地址添加页面-->
        <el-dialog
          title="地址添加"
          :visible.sync="addAddressVisible"
          width="30%"
          center
        >
          <el-form
            label-width="100px"
            :rules="rules"
            :model="tempAddress"
            ref="addAddressFrom"
          >
            <el-form-item label="收货人" prop="receiver">
              <el-input v-model="tempAddress.receiver"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="sex">
              <el-radio-group v-model="tempAddress.sex">
                <el-radio :label="1">男</el-radio>
                <el-radio :label="0">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="电话" prop="phone">
              <el-input v-model="tempAddress.phone"></el-input>
            </el-form-item>
            <el-form-item label="省" prop="">
              <el-input v-model="tempAddress.province"></el-input>
            </el-form-item>
            <el-form-item label="市" prop="">
              <el-input v-model="tempAddress.city"></el-input>
            </el-form-item>
            <el-form-item label="区" prop="">
              <el-input v-model="tempAddress.region"></el-input>
            </el-form-item>
            <el-form-item label="详细地址">
              <el-input v-model="tempAddress.detail"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="addAddressVisible = false">取 消</el-button>
            <el-button type="primary" @click="sureAddAddress">确 定</el-button>
          </span>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import request from "../utils/request";
import Logout from "./user/Logout.vue";

export default {
  components: { Logout },
  data() {
    return {
      index: 1,
      //我的信息
      user: {
        userId: 2,
        userName: "bin",
        password: "222222",
        email: "bin@example.com",
        phone: "18523654256",
        sex: 1,
        image: "",
        type: 0,
      },
      //是否显示信息修改弹窗
      changeInfoVisible: false,
      //临时对象
      tempUser: {
        userId: 2,
        userName: "bin",
        password: "222222",
        email: "bin@example.com",
        phone: "18523654256",
        sex: 1,
        image: "",
        type: 0,
      },
      //我的评价
      reviewList: [
        {
          reviewId: 4,
          userId: 2,
          productId: 2,
          rating: 5,
          comment: "还行",
          createTime: 1680160008000,
          userName: "",
          productName: "黑羽肉鸡",
          userImage: "",
          productImage: "",
        },
      ],
      reviewTotal: 1,
      reviewPage: 1,
      reviewPageSize: 3,
      //我的地址
      addressList: [
        {
          addressId: 3,
          userId: 2,
          receiver: "滨哥",
          sex: 1,
          phone: "12363256213",
          province: "广东省",
          city: "汕尾市",
          region: "陆丰村",
          detail: "仲恺1",
          isdefault: 1,
        },
      ],
      //临时地址
      tempAddress: {
        addressId: 3,
        userId: 2,
        receiver: "滨哥",
        sex: 1,
        phone: "12363256213",
        province: "广东省",
        city: "汕尾市",
        region: "陆丰村",
        detail: "仲恺1",
        isdefault: 1,
      },
      addressTotal: 1,
      addressPage: 1,
      addressPageSize: 3,
      //是否显示地址修改弹窗
      changeAddressVisible: false,
      //是否显示地址添加弹窗
      addAddressVisible: false,
      //我的订单
      orderList: [
        {
          orderId: 13,
          userId: 2,
          orderdate: 1681315200000,
          totalprice: 610,
          status: "已付款",
          num: 13,
        },
      ],
      orderTotal: 1,
      orderPage: 1,
      orderPageSize: 5,
      // 表单验证
      rules: {
        userName: [
          { required: true, message: "请填写用户名", trigger: "blur" },
          {
            min: 3,
            message: "请正确填写用户名,长度不少于3个字符",
            trigger: "blur",
          },
          {
            max: 10,
            message: "请正确填写用户名，长度不超过10位",
            trigger: "blur",
          },
        ],
        sex: [{ required: true, message: "性别不能为空", trigger: "blur" }],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 5,
            max: 20,
            message: "密码长度在 5 到 20 个字符",
            trigger: "blur",
          },
        ],
        phone: [
          { required: true, message: "请填写手机号码" },
          {
            pattern: /^((\(\d{2,3}\))|(\d{3}\-))?1[3|5|8]\d{9}$/,
            trigger: "blur",
            message: "电话格式有误",
          }, // eslint-disable-line
        ],
        email: [
          { required: true, message: "请填写电子邮箱", trigger: "blur" },
          {
            trigger: ["blur", "change"],
            pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
            message: "请输入正确的邮箱",
          },
        ],
        receiver: [
          { required: true, message: "请填写收货人", trigger: "blur" },
          {
            min: 3,
            message: "请正确填写信息,长度不少于3个字符",
            trigger: "blur",
          },
          {
            max: 10,
            message: "请正确填写信息，长度不超过10位",
            trigger: "blur",
          },
        ],
      },
    };
  },
  created() {
    const loginId = localStorage.getItem("loginId");
    this.user.userId = loginId;
    this.showMyInfo();
    // 获取路由参数，改变显示的页面
    const pageIndex = this.$route.params.pageIndex;
    if (pageIndex !== undefined) {
      this.index = pageIndex;
    }
    console.log(this.$route.params);
  },
  mounted() {},
  methods: {
    //改变页面显示内容
    changeIndex(index) {
      this.index = index;
    },
    //信息
    //查找我的信息
    showMyInfo() {
      request
        .get("/user/selectUserInfo", {
          params: {
            page: this.addressPage,
            pageSize: this.addressPageSize,
            userId: this.user.userId,
          },
        })
        .then((res) => {
          this.addressList = res.data.addressList;
          this.user.userId = res.data.userId;
          this.user.userName = res.data.userName;
          this.user.password = res.data.password;
          this.user.email = res.data.email;
          this.user.phone = res.data.phone;
          this.user.sex = res.data.sex;
          this.user.type = res.data.type;
          this.user.image = res.data.image;
          this.addressTotal = res.data.total;
          this.copyUser(this.user, this.tempUser);
        })
        .catch(function (err) {
          alert(err);
        });
    },
    //下载图片
    getImgUrl(img) {
      return `http://localhost:9999/file/download?fileName=${img}`;
    },
    copyUser(user1, user2) {
      user2.userId = user1.userId;
      user2.userName = user1.userName;
      user2.password = user1.password;
      user2.email = user1.email;
      user2.phone = user1.phone;
      user2.sex = user1.sex;
      user2.type = user1.type;
      user2.image = user1.image;
    },
    //信息修改
    changeInfo() {
      // 表单验证通过才继续执行
      this.$refs.updateInfoFrom.validate((valid) => {
        if (valid) {
          this.changeInfoVisible = false;
          this.$confirm("是否确认修改信息?", "提示", {
            confirmButtonText: "是",
            cancelButtonText: "否",
            type: "warning",
            center: true,
          })
            .then((res) => {
              console.log(res);
              this.copyUser(this.tempUser, this.user);
              this.$refs.updateInfoFrom.resetFields();
              request.put("/user/updateUserInfo", this.user).then((res) => {
                if (res.data.code == 200) {
                  this.showMyInfo();
                  this.$message.success("信息修改成功");
                }
              });
            })
            .catch(() => {
              this.$refs.updateInfoFrom.resetFields();
              this.$message({
                type: "info",
                message: "已取消修改",
              });
            });
        }
      });
    },
    //取消信息修改
    cancelChangeInfo() {
      this.changeInfoVisible = false;
      this.$refs.updateInfoFrom.resetFields();
      this.copyUser(this.user, this.tempUser);
    },

    //评论
    //查看我的评论
    selectReview(index) {
      this.changeIndex(index);
      request
        .get("/review/selectMyReview", {
          params: {
            page: this.reviewPage,
            pageSize: this.reviewPageSize,
            userId: this.user.userId,
          },
        })
        .then((res) => {
          this.reviewList = res.data.list;
          this.reviewTotal = res.data.total;
        });
    },
    //删除评论
    deleteReview(id) {
      this.$confirm("是否确认删除该评论?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        center: true,
      })
        .then((res) => {
          console.log(res);
          this.$message(
            {
              type: "success",
              message: "删除成功!",
            },
            request
              .delete("/review/deleteReview", {
                params: {
                  reviewId: id,
                },
              })
              .then((res) => {
                console.log(res.data);
                this.selectReview(2);
              })
          );
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    //改变评论页数
    changeReviewPage(page) {
      this.reviewPage = page;
      this.selectReview(2);
    },

    //地址
    //复制地址
    copyAddress(address1, address2) {
      address2.addressId = address1.addressId;
      address2.userId = address1.userId;
      address2.receiver = address1.receiver;
      address2.sex = address1.sex;
      address2.phone = address1.phone;
      address2.province = address1.province;
      address2.city = address1.city;
      address2.region = address1.region;
      address2.detail = address1.detail;
      address2.isdefault = address1.isdefault;
    },
    //删除地址
    deleteAddress(id) {
      this.$confirm("是否确认删除该地址?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        center: true,
      })
        .then((res) => {
          console.log(res);
          this.$message(
            {
              type: "success",
              message: "删除成功!",
            },
            request
              .delete("/address/deleteAddress", {
                params: {
                  addressId: id,
                },
              })
              .then((res) => {
                console.log(res.data);
                this.showMyInfo();
              })
          );
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    //设为默认地址
    setDefault(address) {
      request.post("/address/defaultAddress", address).then((res) => {
        console.log(res.data);
        this.showMyInfo();
        this.$message.success("设置成功");
      });
    },
    //改变地址页数
    changeAddressPage(page) {
      this.addressPage = page;
      this.showMyInfo();
    },
    //编辑地址
    editAddress(address) {
      this.changeAddressVisible = true;
      this.copyAddress(address, this.tempAddress);
    },
    //取消修改地址
    cancelChangeAddress() {
      this.$refs.updateAddressFrom.resetFields();
      this.changeAddressVisible = false;
    },
    //确定编辑地址
    sureEditAddress() {
      this.$refs.updateAddressFrom.validate((valid) => {
        if (valid) {
          this.$confirm("是否确认修改地址?", "提示", {
            confirmButtonText: "是",
            cancelButtonText: "否",
            type: "warning",
            center: true,
          })
            .then((res) => {
              console.log(res);
              request
                .put("/address/updateAddress", this.tempAddress)
                .then((res) => {
                  if (res.code == 200) {
                    this.changeAddressVisible = false;
                    this.showMyInfo();
                    this.$message.success("地址修改成功");
                  }
                });
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消修改",
              });
            });
        }
      });
    },
    //清空临时地址
    clearTempAddress() {
      this.tempAddress.addressId = null;
      this.tempAddress.userId = null;
      this.tempAddress.receiver = null;
      this.tempAddress.sex = null;
      this.tempAddress.phone = null;
      this.tempAddress.province = null;
      this.tempAddress.city = null;
      this.tempAddress.region = null;
      this.tempAddress.detail = null;
      this.tempAddress.isdefault = null;
    },
    //添加地址
    addAddress() {
      this.addAddressVisible = true;
      this.clearTempAddress();
    },
    //确认添加地址
    sureAddAddress() {
      console.log(this.tempUser.sex);
      this.$refs.addAddressFrom.validate((valid) => {
        if (valid) {
          this.$confirm("是否确认添加地址?", "提示", {
            confirmButtonText: "是",
            cancelButtonText: "否",
            type: "warning",
            center: true,
          })
            .then((res) => {
              console.log(res);
              request
                .post("/address/addAddress", this.tempAddress)
                .then((res) => {
                  if (res.code == 200) {
                    this.addAddressVisible = false;
                    this.showMyInfo();
                    this.$message.success("地址添加成功");
                    this.clearTempAddress();
                  }
                });
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消修改",
              });
            });
        }
      });
    },
    //订单
    //查找我的订单
    selectOrders(index) {
      this.changeIndex(index);
      request
        .get("/order/selectAllOrder", {
          params: {
            page: this.orderPage,
            pageSize: this.orderPageSize,
            userId: this.user.userId,
          },
        })
        .then((res) => {
          this.orderList = res.data.list;
          this.orderTotal = res.data.total;
        });
    },
    //改变订单页数
    changeOrderPage(page) {
      this.orderPage = page;
      this.selectOrders(4);
    },
    //TODO 上传
    //图片上传
    beforeUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isPNG = file.type === "image/png";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG && !isPNG) {
        this.$message.error("上传图片只能是 JPG 或 PNG 格式!");
        return false;
      }
      if (!isLt2M) {
        this.$message.error("上传图片大小不能超过 2MB!");
        return false;
      }
      return true;
    },
    //图片上传成功后执行
    handleUploadSuccess(response) {
      this.tempUser.image = response.data;
      this.$message.success("上传成功!");
    },
  },
};
</script>

<style scoped>
/* Reset Styles */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* General Styles */
.userInfo {
  font-family: "Segoe UI", sans-serif;
  font-size: 16px;
  line-height: 1.5;
  color: #333;
  background-color: #f5f5f5;
}

header {
  position: relative;
  background-color: #fff;
  padding: 1rem;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.header-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.user-info {
  display: flex;
  align-items: center;
}

.headImage {
  width: 70px;
  height: 70px;
  object-fit: cover;
  border-radius: 50%;
  margin-right: 1rem;
  border: 2px solid #65b04f;
}

.changeHeadImage {
  width: 90px;
  height: 90px;
  object-fit: cover;
  border-radius: 50%;
  margin-left: 150px;
  /*58px*/
  margin-bottom: 10px;
  border: 2px solid #007bff;
}

.user-details h3 {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 0.3rem;
}

.user-details p {
  font-size: 0.9rem;
  color: #777;
}

.logout {
  height: 30px;
  width: 30px;
  position: absolute;
  right: 20px;
  bottom: 15px;
}

.container {
  display: flex;
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

.content {
  flex: 1;
  padding: 1rem;
}

.card {
  background-color: #fff;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  padding: 1rem;
  margin-top: 1rem;
}

/*我的信息*/
.myName {
  margin-top: 20px;
  margin-left: 40%;
  font-size: 35px;
}

.myInfo {
  text-align: center;
  margin-left: 35%;
  line-height: 48px;
}

.myInfoTr {
  font-size: 20px;
}

.changeInfo {
  background-color: white;
  color: #4c8f3a;
  border: 1px solid #4c8f3a;
  font-size: 20px;
  width: 40%;
  border-radius: 4px;
  margin-top: 35px;
}

.changeInfo:hover {
  background-color: #eeeeee;
}

/*我的评论*/
.comment {
  width: 99.5%;
  padding: 10px;
  border-radius: 10px;
  background-color: white;
  margin-bottom: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  position: relative;
}

.commentImg {
  width: 100px;
  height: 100px;
  margin-right: 90%;
}

.productName {
  position: absolute;
  left: 140px;
  font-size: 25px;
  font-weight: bold;
}

.commentContent {
  font-size: 18px;
}

.commentDate {
  font-size: 18px;
  margin-left: 85%;
}

.deleteReview {
  position: absolute;
  top: 10px;
  right: 20px;
  color: red;
  font-size: 18px;
}

/*我的地址*/
/* 地址列表样式 */
.list {
  /*margin: 10px;*/
  width: 100%;
  background-color: #ffffff;
}

.list tr th {
  background-color: #fff;
}

.list table {
  width: 100%;
  border-collapse: collapse;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
}

.list th,
.list td {
  border: none;
  padding: 20px;
  font-size: 16px;
}

.list th {
  background-color: #f2f2f2;
  font-weight: bold;
}

.info {
  text-align: center;
  height: 80px;
}

.addAddress {
  margin-top: 10px;
  margin-bottom: 20px;
  margin-left: 1130px;
  font-size: 15px;
  width: 8%;
  height: 10%;
  border-radius: 4px;
  border: 1px solid #007bff;
}

.addAddress:hover {
  color: #ffffff;
  background-color: #007bff;
}

/* 我的订单 */
.detail a {
  text-decoration: none;
  color: black;
}

.detail:hover a {
  color: white;
}

.edit {
  font-size: 14px;
  color: #007bff;
}

.delete {
  font-size: 14px;
  color: red;
  margin-left: 5px;
}

.defaultButton {
  margin-top: 10px;
  font-size: 13px;
  border-radius: 4px;
  border: 2px solid coral;
  background-color: white;
  color: coral;
}

.defaultButton:hover {
  background-color: coral;
  color: white;
}

@media screen and (max-width: 767px) {
  .list table {
    border-radius: 0;
    box-shadow: none;
  }

  .list th,
  .list td {
    font-size: 14px;
    padding: 10px;
  }

  .list td.name {
    font-size: 16px;
  }

  .list td.actions button {
    padding: 5px 10px;
    font-size: 14px;
  }
}
</style>
