<template xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
  <div>
    <div class="header-part">
      <div class="container-fluid">
        <div class="row">
          <!-- logo -->
          <div class="col-md-2">
            <a href="#/user/home">
              <img src="/images/logo.png" alt="" class="logo"/>
            </a>
          </div>
          <!-- 标题 -->
          <div class="col-md-7" style="text-align: center;">
            <span style="font-size: 30px; font-weight: bolder;  line-height: 80px">后 台 管 理</span>
          </div>

          <!-- 登陆 -->
          <div class="col-md-2 user_info">
            <img :src="getImgUrl(avatarImgName)" class="user-img"/>
            <span class="uname">&nbsp&nbsp{{ adminName }}</span>
          </div>
          <div class="col-md-1">
            <logout>
              <button type="button" class="btn btn-primary btn-sm user">
                <span class="glyphicon glyphicon-share"></span>退出
              </button>
            </logout>
          </div>
        </div>
      </div>
    </div>

    <el-container class="el-container">
      <admin-aside @showTable="getShow"></admin-aside>
      <el-main v-show="hasPer === false">
        <el-empty description="你没有该权限！请联系管理员！"></el-empty>
      </el-main>
      <el-main class="box-main" v-show="display === 'order' && hasPer === true">
        <el-button
            type="danger"
            @click="orderSelectionDelete"
            style="float: right; margin-bottom: 20px"
        >删除所选订单
        </el-button>
        <el-table
            :data="order.orderList"
            @selection-change="orderSelectionChange"
        >
          <el-table-column type="selection"></el-table-column>
          <el-table-column
              prop="orderId"
              label="订单号"
              width="140"
              align="center"
          >
          </el-table-column>
          <el-table-column
              prop="userId"
              label="用户ID"
              width="120"
              align="center"
          >
          </el-table-column>
          <el-table-column
              prop="orderdate"
              label="订单时间"
              width="250"
              align="center"
          >
          </el-table-column>
          <el-table-column
              prop="totalprice"
              label="合计"
              width="120"
              align="center"
          >
          </el-table-column>
          <el-table-column
              prop="status"
              label="付款状态"
              width="120"
              align="center"
          >
          </el-table-column>
          <el-table-column prop="num" label="数量" width="120" align="center">
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button size="mini" @click="toOrderDetails(scope.row.orderId)">
                订单详情
              </el-button
              >
              <el-button
                  size="mini"
                  type="danger"
                  @click="orderDelete(scope.row.orderId)"
              >
                删除
              </el-button>
              <el-button
                  size="mini"
                  type="primary"
                  v-if="scope.row.status==='已付款'"
                  @click="send(scope.row)"
              >
                发货
              </el-button>
              <el-button
                  size="mini"
                  type="success"
                  v-if="scope.row.status==='已发货'"
                  @click="arrive(scope.row)"
              >
                送达
              </el-button>
              <el-button
                  size="mini"
                  type="warning"
                  v-if="scope.row.status==='申请退款'"
                  @click="refund(scope.row)"
              >
                确认
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
            class="el-pagination"
            background
            layout="prev, pager, next"
            :total="order.total"
            :current-page="order.orderPage"
            :page-size="order.orderPageSize"
            @prev-click="orderPageChange"
            @next-click="orderPageChange"
            @current-change="orderPageChange"
        >
        </el-pagination>
      </el-main>

      <el-main
          class="box-main"
          v-show="display === 'product' && hasPer === true"
      >
        <div>
          <el-input
              placeholder="根据名称"
              style="width: 200px; margin-bottom: 20px"
              v-model="product.productName"
              @input="getProductList"
          ></el-input>
          <el-input
              placeholder="根据产地"
              style="width: 200px"
              v-model="product.productOrigin"
              @input="getProductList"
          ></el-input>
<!--          <el-input-->
<!--              placeholder="根据种类"-->
<!--              style="width: 200px"-->
<!--              v-model="product.productCategory"-->
<!--              @input="getProductList"-->
<!--          ></el-input>-->
          <el-button v-if="isAllProduct" type="danger" class="lackButt" @click="selectLackProductButton">
            缺货产品
          </el-button>
          <el-button v-if="!isAllProduct" type="success" class="lackButt" @click="getProductList">
            所有产品
          </el-button>
          <el-button type="primary" class="addButt" @click="addProductButton">
            添加产品
          </el-button>
        </div>
        <el-table :data="product.productList">
          <el-table-column
              prop="productId"
              label="图片"
              width="140"
              align="center"
          >
            <template slot-scope="scope">
              <el-image
                  style="width: 100px; height: 100px"
                  :src="getImgUrl(scope.row.image)"
                  fit="contain"
              >
              </el-image>
            </template>
          </el-table-column>
          <el-table-column
              prop="productName"
              label="产品名称"
              width="140"
              align="center"
          >
          </el-table-column>
          <el-table-column
              prop="description"
              label="简介"
              width="120"
              align="center"
          >
          </el-table-column>
          <el-table-column prop="price" label="价格" width="120" align="center">
          </el-table-column>
          <el-table-column
              prop="origin"
              label="产地"
              width="120"
              align="center"
          >
          </el-table-column>
          <el-table-column
              prop="weight"
              label="重量"
              width="120"
              align="center"
          >
          </el-table-column>
          <el-table-column
              prop="inventory"
              label="库存"
              width="120"
              align="center"
          >
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button size="mini" @click="getUpdateProduct(scope.row)"
              >编辑
              </el-button>
              <el-button
                  size="mini"
                  type="danger"
                  @click="productDelete(scope.row.productId)"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
            class="el-pagination"
            background
            layout="prev, pager, next"
            :total="product.productTotal"
            :current-page="product.productPage"
            :page-size="product.productPageSize"
            @prev-click="productPageChange"
            @next-click="productPageChange"
            @current-change="productPageChange"
        >
        </el-pagination>
      </el-main>

      <el-main
          class="box-main"
          v-show="display === 'editInfo' && hasPer === true"
      >
        <div>
          <el-input
              placeholder="搜索员工..."
              style="width: 200px; margin-bottom: 20px"
              v-model="admins.userName"
              @input="getAdminList"
          ></el-input>
          <el-button type="primary" class="addButt" @click="addAdminInfo">
            添加员工
          </el-button>
        </div>
        <el-table :data="admins.list">
          <el-table-column prop="image" label="图片" width="140" align="center">
            <template slot-scope="scope">
              <el-image
                  style="width: 100px; height: 100px"
                  :src="getImgUrl(scope.row.image)"
                  fit="contain"
              >
              </el-image>
            </template>
          </el-table-column>
          <el-table-column
              prop="userId"
              label="员工ID"
              width="140"
              align="center"
          >
          </el-table-column>
          <el-table-column
              prop="userName"
              label="员工姓名"
              width="120"
              align="center"
          >
          </el-table-column>
<!--          <el-table-column prop="sex" label="性别" width="120" align="center">-->
<!--          </el-table-column>-->
          <el-table-column prop="sex" label="性别" width="120" align="center">
            <template slot-scope="scope" style="margin-left: 20px">
              <span style="margin-left: 10px">
                <td>{{ scope.row.sex === 1 ? "男" : "女" }}</td>
              </span>
            </template>
          </el-table-column>
          <el-table-column
              prop="password"
              label="密码"
              width="120"
              align="center"
          >
          </el-table-column>
          <el-table-column
              prop="phone"
              label="员工电话"
              width="120"
              align="center"
          >
          </el-table-column>
          <el-table-column label="操作" align="center" width="300%">
            <template slot-scope="scope">
              <el-button size="mini" @click="getAdmin(scope.row)"
              >修改信息
              </el-button>
              <el-button
                  size="mini"
                  type="danger"
                  @click="deleteAdmin(scope.row)"
              >删除员工
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
            class="el-pagination"
            background
            layout="prev, pager, next"
            :total="admins.total"
            :current-page="admins.adminsPage"
            :page-size="admins.adminsPageSize"
            @prev-click="adminsPageChange"
            @next-click="adminsPageChange"
            @current-change="adminsPageChange"
        >
        </el-pagination>
      </el-main>

      <el-main
          class="box-main"
          v-show="display === 'editPermis' && hasPer === true"
      >
        <div>
          <el-input
              placeholder="搜索员工..."
              style="width: 200px; margin-bottom: 20px"
              v-model="admins.userName"
              @input="getAdminList"
          ></el-input>
          <el-button type="primary" class="addButt" @click="addAdminInfo">
            添加员工
          </el-button>
        </div>
        <el-table :data="admins.list">
          <el-table-column
              prop="userId"
              label="员工ID"
              width="300%"
              align="center"
          >
          </el-table-column>
          <el-table-column
              prop="userName"
              label="员工姓名"
              width="300%"
              align="center"
          >
          </el-table-column>
          <el-table-column label="操作" align="center" width="300%">
            <template slot-scope="scope">
              <el-button size="mini" @click="getPermission(scope.row)"
              >修改权限
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
            class="el-pagination"
            background
            layout="prev, pager, next"
            :total="admins.total"
            :current-page="admins.adminsPage"
            :page-size="admins.adminsPageSize"
            @prev-click="adminsPageChange"
            @next-click="adminsPageChange"
            @current-change="adminsPageChange"
        >
        </el-pagination>
      </el-main>

      <el-main class="box-main" v-show="display === 'user' && hasPer === true">
        <el-input
            placeholder="搜索用户..."
            style="width: 200px; margin-bottom: 20px"
            v-model="user.userName"
            @input="getUserList"
        ></el-input>
        <el-table :data="user.userList">
          <el-table-column
              prop="userId"
              label="用户ID"
              width="300%"
              align="center"
          >
          </el-table-column>
          <el-table-column
              prop="userName"
              label="用户姓名"
              width="300%"
              align="center"
          >
          </el-table-column>
          <el-table-column label="操作" align="center" width="300%">
            <template slot-scope="scope">
              <el-button size="mini" @click="getAddressList(scope.row)"
              >查看地址
              </el-button>
              <el-button
                  size="mini"
                  type="danger"
                  @click="deleteUser(scope.row)"
              >注销用户
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
            class="el-pagination"
            background
            layout="prev, pager, next"
            :total="user.total"
            :current-page="user.userPage"
            :page-size="user.userPageSize"
            @prev-click="userPageChange"
            @next-click="userPageChange"
            @current-change="userPageChange"
        >
        </el-pagination>
      </el-main>

      <el-main
          class="box-main"
          v-show="display === 'category' && hasPer === true"
      >
        <section id="todoapp">
          <h2>种类管理</h2>
          <!-- 输入框 -->
          <header class="header">
            <input
                v-model="addCategoryFrom.name"
                @keyup.enter="addCategory"
                autofocus="autofocus"
                autocomplete="off"
                placeholder="请输入需要添加的种类"
                class="new-todo"
            />
          </header>
          <!-- 列表区域 -->
          <section class="main">
            <ul class="todo-list">
              <li
                  class="todo"
                  v-for="(item, index) in category.categoryList"
                  :key="item.categoryId"
              >
                <div class="view">
                  <span class="index">{{ index + 1 }}.</span>
                  <label>{{ item.name }}</label>
                  <button
                      class="destroy"
                      @click="deleteCategory(item.categoryId)"
                  ></button>
                </div>
              </li>
            </ul>
          </section>
          <!-- 统计和清空 -->
        </section>
      </el-main>

      <el-dialog title="添加产品" :visible.sync="addProductTable" width="30%">
        <el-form
            ref="form"
            :model="addProductForm"
            label-width="80px"
            style="width: 80%"
        >
          <div class="hiddenInfo">
            <el-upload
                action="http://localhost:9999/file/upload"
                :on-success="handleUploadSuccess"
                :before-upload="beforeUpload"
                :show-file-list="false"
            >
              <el-image
                  :src="getImgUrl(addProductForm.image)"
                  class="changeHeadImage"
              ></el-image>
            </el-upload>
          </div>
          <el-form-item label="产品名称" size="mini">
            <el-input v-model="addProductForm.productName"></el-input>
          </el-form-item>
          <el-form-item label="种类" size="mini">
            <category-select v-model="addProductForm.category">
            </category-select>
          </el-form-item>
          <el-form-item label="产品描述" size="medium">
            <el-input v-model="addProductForm.description"></el-input>
          </el-form-item>
          <el-form-item label="产品价格" size="mini">
            <el-input v-model="addProductForm.price"></el-input>
          </el-form-item>
          <el-form-item label="产品重量" size="mini">
            <el-input v-model="addProductForm.weight"></el-input>
          </el-form-item>
          <el-form-item label="产地" size="mini">
            <el-input v-model="addProductForm.origin"></el-input>
          </el-form-item>
          <el-form-item label="库存" size="mini">
            <el-input v-model="addProductForm.inventory"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="addProductTable = false">取 消</el-button>
          <el-button type="primary" @click="addProduct()">确 定</el-button>
        </span>
      </el-dialog>

      <el-dialog
          title="编辑产品"
          :visible.sync="updateProductTable"
          width="30%"
      >
        <el-form
            ref="form"
            :model="updateForm"
            label-width="80px"
            style="width: 80%"
        >
          <div class="hiddenInfo">
            <el-upload
                action="http://localhost:9999/file/upload"
                :on-success="handleUploadSuccess"
                :before-upload="beforeUpload"
                :show-file-list="false"
            >
              <el-image
                  :src="getImgUrl(updateForm.image)"
                  class="changeHeadImage"
              ></el-image>
            </el-upload>
          </div>
          <el-form-item label="产品名称" size="mini">
            <el-input v-model="updateForm.productName"></el-input>
          </el-form-item>
          <el-form-item label="种类" size="mini">
            <category-select
                v-model="updateForm.category"
                :initSelectLabel="updateForm.categoryName"
                :placeholder="updateForm.categoryName"
                @onSelectLabel="updateForm.categoryName = $event"
            >
            </category-select>
          </el-form-item>
          <el-form-item label="产品描述" size="medium">
            <el-input v-model="updateForm.description"></el-input>
          </el-form-item>
          <el-form-item label="产品价格" size="mini">
            <el-input v-model="updateForm.price"></el-input>
          </el-form-item>
          <el-form-item label="产品重量" size="mini">
            <el-input v-model="updateForm.weight"></el-input>
          </el-form-item>
          <el-form-item label="库存" size="mini">
            <el-input v-model="updateForm.inventory"></el-input>
          </el-form-item>
          <el-form-item label="产地" size="mini">
            <el-input v-model="updateForm.origin"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="updateProductTable = false">取 消</el-button>
          <el-button type="primary" @click="updateProduct()">确 定</el-button>
        </span>
      </el-dialog>

      <el-dialog title="修改信息" :visible.sync="adminTable" width="30%">
        <el-form
            ref="form"
            :model="adminForm"
            label-width="80px"
            style="width: 80%"
        >
          <div class="hiddenInfo">
            <el-upload
                action="http://localhost:9999/file/upload"
                :on-success="handleUploadSuccess"
                :before-upload="beforeUpload"
                :show-file-list="false"
            >
              <el-image
                  :src="getImgUrl(adminForm.image)"
                  class="changeHeadImage"
              ></el-image>
            </el-upload>
          </div>
          <el-form-item label="员工ID" size="mini">
            <el-input v-model="adminForm.userId"></el-input>
          </el-form-item>
          <el-form-item label="员工姓名" size="medium">
            <el-input v-model="adminForm.userName"></el-input>
          </el-form-item>
          <el-form-item label="性别" size="mini">
            <el-radio-group v-model="adminForm.sex">
              <el-radio :label="1">男</el-radio>
              <el-radio :label="0">女</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="密码" size="mini">
            <el-input v-model="adminForm.password"></el-input>
          </el-form-item>
          <el-form-item label="手机号码" size="mini">
            <el-input v-model="adminForm.phone"></el-input>
          </el-form-item>
          <el-form-item label="电子邮箱" size="mini">
            <el-input v-model="adminForm.email"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="adminTable = false">取 消</el-button>
          <el-button type="primary" @click="updateAdmin">确 定</el-button>
        </span>
      </el-dialog>

      <el-dialog title="地址信息" :visible.sync="addressTable" width="50%">
        <el-table :data="addressList">
          <el-table-column prop="addressId" label="地址ID" width="140">
          </el-table-column>
          <el-table-column prop="province" label="省份" width="140">
          </el-table-column>
          <el-table-column prop="city" label="市" width="140">
          </el-table-column>
          <el-table-column prop="detail" label="详细地址" width="140">
          </el-table-column>
          <el-table-column prop="phone" label="手机号" width="140">
          </el-table-column>
          <el-table-column prop="receiver" label="收货人" width="140">
          </el-table-column>
          <el-table-column prop="sex" label="性别" width="140">
          </el-table-column>
        </el-table>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="addressTable = false"
          >确 定</el-button
          >
        </span>
      </el-dialog>

      <el-dialog title="员工权限" :visible.sync="permissionTable">
        <div class="dialog-header">
          <i class="el-icon-user"></i>
          <span>权限修改</span>
        </div>
        <div class="dialog-body">
          <div class="permission-list">
            <div
                v-for="permission in allPermission"
                :key="permission.permissionId"
                style="margin-left: 25%"
            >
              <el-checkbox-group
                  v-model="checkedValues"
                  style="
                  margin-right: 30px;
                  margin-top: 10px;
                  display: flex;
                  float: left;
                "
                  :style="
                  permission.permissionName.includes('增加产品') ||
                  permission.permissionName.includes('查看订单') ||
                  permission.permissionName.includes('查看用户信息') ||
                  permission.permissionName.includes('新增种类')
                    ? 'clear:left;'
                    : ''
                "
              >
                <el-checkbox
                    :key="permission.permissionId"
                    :label="permission.permissionId"
                    :disabled="permissionForm.userName === 'admin'"
                >{{ permission.permissionName }}
                </el-checkbox>
              </el-checkbox-group>
            </div>
            <div style="clear: both"></div>
          </div>
        </div>
        <div class="dialog-footer">
          <el-button @click="permissionTable = false">取 消</el-button>
          <el-button type="primary" @click="savePermissions">确 定</el-button>
        </div>
      </el-dialog>

      <el-dialog title="添加员工" :visible.sync="addAdminTable" width="30%">
        <el-form
            ref="form"
            :rules="rules"
            :model="addAdminForm"
            label-width="80px"
            style="width: 80%"
            class=""
        >
          <div class="hiddenInfo">
            <el-upload
                action="http://localhost:9999/file/upload"
                :on-success="handleUploadSuccess"
                :before-upload="beforeUpload"
                :show-file-list="false"
            >
              <el-image
                  :src="getImgUrl(addAdminForm.image)"
                  class="changeHeadImage"
              ></el-image>
            </el-upload>
          </div>
          <el-form-item label="姓名" size="mini" prop="userName">
            <el-input v-model="addAdminForm.userName"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-radio v-model="addAdminForm.sex" label="1" border>男</el-radio>
            <el-radio v-model="addAdminForm.sex" label="0" border>女</el-radio>
          </el-form-item>
          <el-form-item label="密码" size="medium" prop="password">
            <el-input v-model="addAdminForm.password"></el-input>
          </el-form-item>
          <el-form-item label="手机号" size="mini" prop="phone">
            <el-input v-model="addAdminForm.phone"></el-input>
          </el-form-item>
          <el-form-item label="电子邮箱" size="mini" prop="email">
            <el-input v-model="addAdminForm.email"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="addAdminTable = false">取 消</el-button>
          <el-button type="primary" @click="addAdmin()">确 定</el-button>
        </span>
      </el-dialog>

      <!--echarts  -->
      <!--销量前十-->
      <div v-show="display === 'saleInfo' && hasPer === true">
        <br>
        <div id="saleTopChart" class="saleTopChart" ref="saleTopChart"></div>
        <div id="monthSaleChart" class="saleTopChart" ref="monthSaleChart"></div>
      </div>
    </el-container>
  </div>
</template>

<script>
import request from "@/utils/request";
import adminAside from "@/components/subcomponents/adminAside.vue";
import Logout from "./user/Logout.vue";
import CategorySelect from "./CategorySelect.vue";
import * as echarts from 'echarts';

export default {
  components: {
    adminAside,
    Logout,
    CategorySelect,
  },
  data() {
    return {
      selectedYear: 2023,

      adminName: '',// 管理员名称
      avatarImgName: '', // 头像图片

      display: "product",
      url: "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",

      addProductTable: false,
      updateProductTable: false,
      permissionTable: false,
      orderDetail: false,
      addressTable: false,
      addAdminTable: false,
      adminTable: false,
      hasPer: true,

      product: {
        productTotal: 1,
        //TODO 改
        productPage: 2,
        productPageSize: 5,
        productName: "",
        productCategory: "",
        productOrigin: "",
        searchWay: "",
        inventory: 0,

        productList: [
          {
            productId: 1,
            productName: "白羽肉鸡",
            description: "肉质肥美，口感细嫩",
            price: 45,
            weight: 1,
            origin: "河南",
            category: 2,
            categoryName: "肉鸡",
            inventory: 100,
            image: "6e79d623-eab1-4dc2-92ef-9081ef58d1b0.jpg",
            sales: null,
          },
        ],
      },

      user: {
        total: 100,
        userPage: 1,
        userPageSize: 5,
        userName: null,
        userList: [
          {
            userId: 1,
            userName: "bin",
            password: "222222",
            email: "bin@example.com",
            phone: "18523654256",
            sex: 1,
            image: "6e79d623-eab1-4dc2-92ef-9081ef58d1b0.jpg",
            type: 0,
          },
        ],
      },

      order: {
        total: 1,
        orderPage: 1,
        orderPageSize: 10,
        orderList: [
          {
            orderId: 13,
            userId: 13,
            orderdate: "2023-04-21 16:00:00",
            totalprice: 600,
            status: "已给钱",
            num: 13,
          },
        ],
      },

      addressList: [
        {
          addressId: 6,
          city: "汕尾市",
          detail: "仲恺5",
          isdefault: 0,
          phone: "3",
          province: "广东省",
          receiver: "收货人",
          region: "陆丰村",
          sex: "男",
          userId: 2,
        },
      ],

      category: {
        categoryName: "请输入种类",
        categoryList: [
          {
            categoryId: 1,
            name: "",
            type: 1,
          },
        ],
      },

      orderForm: {
        orderId: 13,
        userId: 13,
        orderDate: 1681315200000,
        totalPrice: 600,
        status: "已给钱",
        num: 13,
      },

      addProductForm: {
        productName: "哥哥",
        description: "练习时长两年半",
        price: 45,
        weight: 1,
        origin: "偶像练习生",
        category: 2,
        categoryName: "肉鸡",
        inventory: 100,
        image: "6e79d623-eab1-4dc2-92ef-9081ef58d1b0.jpg",
        sales: null,
      },

      updateForm: {
        productId: 1,
        productName: "白羽肉鸡",
        description: "肉质肥美，口感细嫩",
        price: 45,
        weight: 1,
        origin: "河南",
        category: 2,
        inventory: 100,
        image: "6e79d623-eab1-4dc2-92ef-9081ef58d1b0.jpg",
        sales: null,
      },

      checkedValues: [],

      permissionForm: {
        userId: "",
        userName: "",
        permissionList: [
          {
            userId: 1,
            permissionId: 1,
          },
        ],
      },

      allPermission: [
        {
          permissionId: 1,
          permissionName: "权限管理",
        },
      ],

      addAdminForm: {
        email: "222222@qq.com",
        password: "233333",
        phone: "111111111",
        type: 0,
        userName: "11",
        image: "6e79d623-eab1-4dc2-92ef-9081ef58d1b0.jpg",
        sex: 0,
      },

      addCategoryFrom: {
        name: "",
      },

      // 表单验证
      rules: {
        userName: [
          {required: true, message: "请填写用户名", trigger: "blur"},
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
        sex: [{required: true, message: "性别不能为空", trigger: "blur"}],
        password: [
          {required: true, message: "请输入密码", trigger: "blur"},
          {
            min: 5,
            max: 20,
            message: "密码长度在 5 到 20 个字符",
            trigger: "blur",
          },
        ],
        phone: [
          {required: true, message: "请填写手机号码"},
          {
            pattern: /^((\(\d{2,3}\))|(\d{3}\-))?1[3|5|8]\d{9}$/,
            trigger: "blur",
            message: "电话格式有误",
          }, // eslint-disable-line
        ],
        email: [
          {required: true, message: "请填写电子邮箱", trigger: "blur"},
          {
            trigger: ["blur", "change"],
            pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
            message: "请输入正确的邮箱",
          },
        ],
      },

      adminForm: {
        userId: 1,
        userName: "admin",
        password: "123456",
        email: "zhao@example.com",
        phone: "18025532654",
        type: 1,
        image: "6e79d623-eab1-4dc2-92ef-9081ef58d1b0.jpg",
        sex: 1,
      },

      ordersId: [],

      admins: {
        total: 23,
        adminsPage: 1,
        adminsPageSize: 5,
        userName: null,
        list: [
          {
            userId: 1,
            userName: "admin",
            password: "123456",
            email: "zhao@example.com",
            phone: "18025532654",
            type: 1,
            image: "6e79d623-eab1-4dc2-92ef-9081ef58d1b0.jpg",
            sex: 1,
            permissionList: [
              {
                permissionId: 1,
                permissionName: "权限管理",
              },
            ],
          },
        ],
      },

      permissionValue: [
        {
          permissionId: 1,
          permissionName: "权限管理",
        },
      ],

      userId: 1,

      permissiondetails: [
        {
          permissionId: 1,
          permissionName: "权限管理",
        },
      ],

      saleTopChart: null,

      monthSale: [12, 1],

      isAllProduct: true,
    };
  },

  created() {
    // this.getProductList();
    this.getProductList();
    // 获取管理员姓名
    this.adminName = localStorage.getItem("loginName");
    //获得当前登陆用户id
    const loginId = localStorage.getItem("loginId");
    this.avatarImgName = this.getAvatarImgName(loginId);
  },

  methods: {
    //显示页面
    getShow(display, num) {
      if (num === 2) {
        this.getOrderList();
      } else if (num === 1) {
        this.getProductList();
      } else if (num === 3) {
        this.getUserList();
      } else if (num === 4) {
        this.getAdminList();
      } else if (num === 5) {
        this.getCategory();
      } else if (num === 6) {
        this.getTopSales();
        this.getMonthSale();
      }
      this.display = display;
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

    errorHandler() {
      return true;
    },

    //获取订单列表
    getOrderList() {
      request
          .get("/order/selectAllOrder", {
            params: {
              page: this.order.orderPage,
              pageSize: this.order.orderPageSize,
            },
          })
          .then((res) => {
            if (res.data.code === 0) {
              this.$message(res.data.msg);
              this.hasPer = false;
            } else if (res.data.code === 200) {
              this.order.total = res.data.data.total;
              this.order.orderList = res.data.data.list;
              this.hasPer = true;
            } else {
              this.$message({
                duration: 1500,
                message: "获取失败，请检查网络连接！",
              });
            }
          });
    },

    //获得产品列表
    getProductList() {
      this.isAllProduct = true;
      request
          .get("/product/selectAllProduct", {
            params: {
              page: this.product.productPage,
              pageSize: this.product.productPageSize,
              productName: this.product.productName,
              category: this.product.productCategory,
              origin: this.product.productOrigin,
            },
          })
          .then((res) => {
            this.product.productTotal = res.data.data.total;
            this.product.productList = res.data.data.list;
            this.hasPer = true;
          });
    },
    clearAddProduct() {
      this.addProductForm.productName = null;
      this.addProductForm.description = null;
      this.addProductForm.price = null;
      this.addProductForm.weight = null;
      this.addProductForm.origin = null;
      this.addProductForm.category = null;
      this.addProductForm.categoryName = null;
      this.addProductForm.inventory = null;
      this.addProductForm.image = null;
      this.addProductForm.sales = null;
    },
    addProductButton() {
      this.addProductTable = true;
      this.clearAddProduct();
    },
    selectLackProductButton() {
      this.isAllProduct = false;
      request
          .get("/product/selectShortSupplyProduct", {
            params: {
              page: this.product.productPage,
              pageSize: this.product.productPageSize,
              productName: this.product.productName,
              category: this.product.productCategory,
              origin: this.product.productOrigin,
            },
          })
          .then((res) => {
            this.product.productTotal = res.data.data.total;
            this.product.productList = res.data.data.list;
            this.hasPer = true;
          });
    },
    //获取用户列表
    getUserList() {
      console.log(this.user.userName);
      request
          .get("/admin/selectUser", {
            params: {
              keywords: this.user.userName,
              page: this.user.userPage,
              pageSize: this.user.userPageSize,
              type: 0,
            },
          })
          .then((res) => {
            if (res.data.code === 0) {
              this.$message(res.data.msg);
              this.hasPer = false;
            } else if (res.data.code === 200) {
              this.user.total = res.data.data.total;
              this.user.userList = res.data.data.list;
              this.hasPer = true;
            } else {
              this.$message({
                duration: 1500,
                message: "获取失败，请检查网络连接！",
              });
            }
          });
    },

    //获得员工列表
    getAdminList() {
      request
          .get("/admin/selectUser", {
            params: {
              keywords: this.admins.userName,
              page: this.admins.adminsPage,
              pageSize: this.admins.adminsPageSize,
              type: 1,
            },
          })
          .then((res) => {
            if (res.data.code === 0) {
              this.$message(res.data.msg);
              this.hasPer = false;
            } else if (res.data.code === 200) {
              this.admins.total = res.data.data.total;
              this.admins.list = res.data.data.list;
              this.hasPer = true;
            } else {
              this.$message({
                duration: 1500,
                message: "获取失败，请检查网络连接！",
              });
            }
          });
    },

    //获取种类列表
    getCategory() {
      request.get("/category/selectAllCategory").then((res) => {
        if (res.data.code === 0) {
          this.$message(res.data.msg);
          this.hasPer = false;
        } else if (res.data.code === 200) {
          this.category.categoryList = res.data.data;
          this.hasPer = true;
        } else {
          this.$message({
            duration: 1500,
            message: "获取失败，请检查网络连接！",
          });
        }
      });
    },

    //获取当前员工信息
    getAdmin(row) {
      this.adminTable = true;
      this.adminForm = Object.assign({}, row);
      console.log(row);
    },

    // 添加产品
    addProduct() {
      request.post("/product/addProduct", this.addProductForm).then((res) => {
        if (res.data.code === 0) {
          this.$message(res.data.msg);
        } else if (res.data.code === 200) {
          this.$message({
            duration: 1500,
            message: "添加成功！",
          });
          this.addProductTable = false;
          this.getProductList();
        } else {
          this.$message({
            duration: 1500,
            message: "添加失败！",
          });
        }
      });
    },

    //清空临时管理员信息
    clearAddAdmin() {
      this.addAdminForm.email = null;
      this.addAdminForm.password = null;
      this.addAdminForm.phone = null;
      this.addAdminForm.type = null;
      this.addAdminForm.userName = null;
      this.addAdminForm.image = null;
      this.addAdminForm.sex = null;
    },

    addAdminInfo() {
      this.clearAddAdmin();
      console.log(this.addAdminForm);
      this.addAdminTable = true;
    },

    //添加员工
    addAdmin() {
      request.post("/admin/addAdmin", this.addAdminForm).then((res) => {
        if (res.data.code === 0) {
          this.$message(res.data.msg);
        } else if (res.data.code === 200) {
          this.$message({
            duration: 1500,
            message: "添加成功！",
          });
          this.addAdminTable = false;
          this.getAdminList();
        } else {
          this.$message({
            duration: 1500,
            message: "添加失败！",
          });
        }
      });
    },

    //添加种类
    addCategory() {
      request
          .post("/category/addCategory", this.addCategoryFrom)
          .then((res) => {
            if (res.data.code === 0) {
              this.$message(res.data.msg);
            } else if (res.data.code === 200) {
              this.$message({
                duration: 1500,
                message: "添加成功！",
              });
              this.getCategory();
            } else {
              this.$message({
                duration: 1500,
                message: "添加失败！",
              });
            }
          });
    },

    // 更新产品信息
    updateProduct() {
      console.log(this.updateForm)
      request.put("/product/updateProduct", this.updateForm).then((res) => {
        if (res.data.code === 0) {
          this.$message(res.data.msg);
        } else if (res.data.code === 200) {
          this.$message({
            type: "info",
            duration: 1500,
            message: "修改成功！",
          });
          this.updateProductTable = false;
          this.getProductList();
        } else {
          this.$message({
            type: "info",
            duration: 1500,
            message: "修改失败！",
          });
        }
      });
    },

    // 获取当前产品信息
    getUpdateProduct(row) {
      this.updateProductTable = true;
      this.updateForm = Object.assign({}, row);
    },

    //获取当前用户地址列表
    getAddressList(row) {
      this.addressTable = true;
      this.addressList = row.addressList;
    },

    //获取所有权限
    getAllPermissions() {
      request.get("/permission/selectAllPermission").then((res) => {
        this.allPermission = res.data.data;
      });
    },

    //获取当前员工权限
    getPermission(row) {
      this.permissionTable = true;
      this.permissionForm.userName = row.userName;
      this.permissionForm.userId = row.userId;
      this.getAllPermissions();
      request
          .get("/permission/selectProcessPermission", {
            params: {
              userId: row.userId,
            },
          })
          .then((res) => {
            this.checkedValues = res.data.data;
          });
    },

    //更新员工信息
    updateAdmin() {
      this.$confirm("是否修改该员工信息?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        center: true,
      }).then((res) => {
        request.put("/admin/updateAdmin", this.adminForm).then((res) => {
          if (res.data.code === 0) {
            this.$message(res.data.msg);
          } else if (res.data.code === 200) {
            this.$message({
              type: "info",
              duration: 1500,
              message: "修改成功！",
            });
            this.adminTable = false;
            this.getAdminList();
          } else {
            this.$message({
              type: "info",
              duration: 1500,
              message: "修改失败！",
            });
          }
        });
      });
    },

    //更新员工权限
    savePermissions() {
      this.$confirm("是否为该员工添加权限?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        center: true,
      }).then((res) => {
        this.permissionForm.permissionList = this.checkedValues.map(
            (permissionId) => ({
              permissionId: permissionId,
              userId: this.permissionForm.userId,
            })
        );
        this.permissionDetails = this.permissionForm.permissionList;
        request
            .post("/permission/updatePermission", this.permissionDetails)
            .then((res) => {
              if (res.data.code === 0) {
                this.$message(res.data.msg);
              } else if (res.data.code === 200) {
                this.$message({
                  type: "info",
                  duration: 1500,
                  message: "修改成功！",
                });
                this.permissionTable = false;
                this.getAdminList();
              } else {
                this.$message({
                  type: "info",
                  duration: 1500,
                  message: "修改失败！",
                });
              }
            });
      });
    },

    // 删除订单
    orderDelete(orderId) {
      this.$confirm("是否确定删除该订单?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        center: true,
      })
          .then((res) => {
            request
                .delete("/order/deleteOrder", {
                  params: {
                    ids: orderId,
                  },
                })
                .then((res) => {
                  if (res.data.code === 0) {
                    this.$message(res.data.msg);
                  } else if (res.data.code === 200) {
                    this.$message({
                      type: "info",
                      duration: 1500,
                      message: "删除成功！",
                    });
                    this.getOrderList();
                  } else {
                    this.$message({
                      type: "info",
                      duration: 1500,
                      message: "删除失败！",
                    });
                  }
                });
          })
          .catch(() => {
            this.$message({
              type: "info",
              duration: 1500,
              message: "已取消删除",
            });
          });
    },

    send(item) {
      this.$confirm("是否修改订单状态?", "提示", {
        confirmButtonText: "是",
        cancelButtonText: "否",
        type: "warning",
        center: true,
      })
          .then((res) => {
            console.log(res);
            item.status = '已发货';
            request
                .put("/order/updateOrder", item)
                .then((res) => {
                  if (res.data.code == 200) {
                    this.$message({
                      type: "info",
                      duration: 1500,
                      message: "修改成功！",
                    });
                    this.getOrderList();
                  }
                });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消修改",
            });
          });
    },

    arrive(item) {
      this.$confirm("是否修改订单状态?", "提示", {
        confirmButtonText: "是",
        cancelButtonText: "否",
        type: "warning",
        center: true,
      })
          .then((res) => {
            console.log(res);
            item.status = '已送达';
            request
                .put("/order/updateOrder", item)
                .then((res) => {
                  if (res.data.code == 200) {
                    this.$message({
                      type: "info",
                      duration: 1500,
                      message: "修改成功！",
                    });
                    this.getOrderList();
                  }
                });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消修改",
            });
          });
    },
    refund(item) {
      this.$confirm("是否修改订单状态?", "提示", {
        confirmButtonText: "是",
        cancelButtonText: "否",
        type: "warning",
        center: true,
      })
          .then((res) => {
            console.log(res);
            item.status = '已退款';
            request
                .put("/order/updateOrder", item)
                .then((res) => {
                  if (res.data.code == 200) {
                    this.$message({
                      type: "info",
                      duration: 1500,
                      message: "修改成功！",
                    });
                    this.getOrderList();
                  }
                });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消修改",
            });
          });
    },

    //跳转到订单详情页面
    toOrderDetails(orderId) {
      let text = this.$router.resolve({
        path: "/user/order/" + orderId,
      });

      // 打开一个新的页面
      window.open(text.href, "_blank");
    },

    // 删除所选订单
    orderSelectionChange(val) {
      const ids = [];
      for (let i = 0; i < val.length; i++) {
        ids[i] = val[i].orderId;
      }
      this.ordersId = ids;
    },

    orderSelectionDelete() {
      this.$confirm("是否确定删除所选订单?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        center: true,
      })
          .then((res) => {
            request
                .delete("/order/deleteOrder", {
                  params: {
                    ids: this.ordersId.join(","),
                  },
                })
                .then((res) => {
                  if (res.data.code === 0) {
                    this.$message(res.data.msg);
                  } else if (res.data.code === 200) {
                    this.$message({
                      type: "info",
                      duration: 1500,
                      message: "删除成功！",
                    });
                    this.getOrderList();
                  } else {
                    this.$message({
                      type: "info",
                      duration: 1500,
                      message: "删除失败！",
                    });
                  }
                });
          })
          .catch(() => {
            this.$message({
              type: "info",
              duration: 1500,
              message: "已取消删除",
            });
          });
    },

    // 删除产品
    productDelete(productId) {
      this.$confirm("是否确定删除该产品?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        center: true,
      })
          .then((res) => {
            request
                .delete("/product/deleteProduct", {
                  params: {
                    productId,
                  },
                })
                .then((res) => {
                  if (res.data.code === 0) {
                    this.$message(res.data.msg);
                  } else if (res.data.code === 200) {
                    this.$message({
                      type: "info",
                      duration: 1500,
                      message: "删除成功！",
                    });
                    this.getProductList();
                  } else {
                    this.$message({
                      type: "info",
                      duration: 1500,
                      message: "删除失败！",
                    });
                  }
                });
          })
          .catch(() => {
            this.$message({
              type: "info",
              duration: 1500,
              message: "已取消删除",
            });
          });
    },

    //删除用户
    deleteUser(row) {
      this.$confirm("确认删除当前用户?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        center: true,
      })
          .then((res) => {
            request
                .delete("/user/deleteUser", {
                  params: {
                    userId: row.userId,
                  },
                })
                .then((res) => {
                  if (res.data.code === 0) {
                    this.$message(res.data.msg);
                  } else if (res.data.code === 200) {
                    this.$message({
                      type: "info",
                      duration: 1500,
                      message: "删除成功！",
                    });
                    this.getUserList();
                  } else {
                    this.$message({
                      type: "info",
                      duration: 1500,
                      message: "删除失败！",
                    });
                  }
                });
          })
          .catch(() => {
            this.$message({
              type: "info",
              duration: 1500,
              message: "已取消删除",
            });
          });
    },

    //删除种类
    deleteCategory(categoryId) {
      this.$confirm("确认删除当前种类?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        center: true,
      })
          .then((res) => {
            request
                .delete("/category/deleteCategory", {
                  params: {
                    categoryId: categoryId,
                  },
                })
                .then((res) => {
                  if (res.data.code === 0) {
                    this.$message(res.data.msg);
                  } else if (res.data.code === 200) {
                    this.$message({
                      type: "info",
                      duration: 1500,
                      message: "删除成功！",
                    });
                    this.getCategory();
                  } else {
                    this.$message({
                      type: "info",
                      duration: 1500,
                      message: "删除失败！",
                    });
                  }
                });
          })
          .catch(() => {
            // this.$message({
            //   type: 'info',
            //   duration: 1500,
            //   message: '已取消删除'
            // });
          });
    },

    //删除员工
    deleteAdmin(user) {
      this.$confirm("是否确定删除该员工?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        center: true,
      })
          .then((res) => {
            request
                .delete("/admin/deleteAdmin", {
                  params: {
                    userId: user.userId,
                  },
                })
                .then((res) => {
                  if (res.data.code === 0) {
                    this.$message(res.data.msg);
                  } else if (res.data.code === 200) {
                    this.$message({
                      type: "info",
                      duration: 1500,
                      message: "删除成功！",
                    });
                    this.getAdminList();
                  } else {
                    this.$message({
                      type: "info",
                      duration: 1500,
                      message: "删除失败！",
                    });
                  }
                });
          })
          .catch(() => {
            this.$message({
              type: "info",
              duration: 1500,
              message: "已取消删除",
            });
          });
    },

    // 上传图片前
    beforeUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isPNG = file.type === "image/png";
      const isWEBP = file.type === "image/webp";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG && !isPNG && !isWEBP) {
        this.$message.error("上传图片只能是 JPG、PNG和isWEBP 格式!");
        return false;
      }
      if (!isLt2M) {
        this.$message.error("上传图片大小不能超过 2MB!");
        return false;
      }
      return true;
    },

    // 获取图片
    getImgUrl(img) {
      return `http://localhost:9999/file/download?fileName=${img}`;
    },


    // 上传图片成功后
    handleUploadSuccess(response) {
      this.addProductForm.image = response.data;
      this.updateForm.image = response.data;
      console.log(this.addProductForm.image);
      this.$message.success("上传成功!");
    },

    // 更改页码
    productPageChange(page) {
      this.product.productPage = page;
      this.getProductList();
    },

    orderPageChange(page) {
      this.order.orderPage = page;
      this.getOrderList();
    },

    adminsPageChange(page) {
      this.admins.adminsPage = page;
      this.getAdminList();
    },

    userPageChange(page) {
      this.user.userPage = page;
      this.getUserList();
    },

    //产品销量前十
    getTopSales() {
      request
          .get("/product/selectProductBySales", {
            params: {
              page: 1,
              pageSize: 10,
              productName: null,
              category: null,
              origin: null,
            },
          })
          .then((res) => {
            this.product.productTotal = res.data.data.total;
            this.product.productList = res.data.data.list;
            this.initSaleTopChart();
          });
    },
    //echarts
    //产品销量前十柱状图
    initSaleTopChart() {
      var saleTopProductName = this.product.productList.map((product) => product.productName);
      var saleTopProductSales = this.product.productList.map((product) => product.sales);
      // 初始化echarts
      this.saleTopChart = echarts.init(document.getElementById('saleTopChart'));
      // 绘制图表
      this.saleTopChart.setOption({
        title: {
          text: '销量前十的产品',
        },
        tooltip: {
          trigger: 'axis',
        },
        // legend: {
        //   data: ['销量']
        // },
        xAxis: {
          data: saleTopProductName,
          axisLabel: {
            interval: 0
          }
        },
        yAxis: {},
        series: [
          {
            name: '销量',
            type: 'bar',
            color: 'darkorange',
            data: saleTopProductSales
          }
        ],
      })
      console.log(this.product.productList);
    },
    //获取月销售额
    getMonthSale() {
      request
          .get("/saleInfo/selectTotalSales",)
          .then((res) => {
            this.monthSale = res.data.data;
            this.initMonthSaleChart();
          });
    },
    //产品销量前十柱状图
    initMonthSaleChart() {
      // 初始化echarts
      this.monthSaleChart = echarts.init(document.getElementById('monthSaleChart'));
      // 绘制图表
      this.monthSaleChart.setOption({
        title: {
          text: '月份销售额曲线图',
        },
        tooltip: {
          trigger: 'axis',
          // formatter: '{b}: {c} 元', // 鼠标悬停时显示的提示内容
        },
        xAxis: {
          type: 'category',
          data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
          axisLabel: {
            interval: 0
          },
        },
        yAxis: {
          type: 'value',
        },
        series: [{
          type: 'line',
          data: this.monthSale,
        }],
      })
      console.log(this.product.productList);
    },
  },
};
</script>

<style scoped>
.logo {
  width: 200px;
  height: 100px;
  float: left;
}

.hiddenInfo {
  visibility: hidden;
}

.changeHeadImage {
  width: 200px;
  height: 150px;
  object-fit: cover;
  border-radius: 10%;
  margin-left: 100px; /*58px*/
  margin-bottom: 10px;
  border: 2px solid #007bff;
  visibility: visible;
}

.el-header {
  color: #333;
  line-height: 60px;
  background-color: yellowgreen;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}

.el-icon-arrow-down {
  font-size: 12px;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}

.el-icon-arrow-down {
  font-size: 12px;
}

.dialog-header {
  display: flex;
  align-items: center;
  font-size: 20px;
  padding: 20px;
  background-color: #f5f7fa;
}

.dialog-header i {
  font-size: 30px;
  margin-right: 10px;
}

.dialog-body {
  padding: 20px;
}

.permission-row {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.role-info {
  flex: 1;
}

.role-name {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
}

.role-desc {
  font-size: 14px;
  color: #909399;
  margin-bottom: 5px;
}

.permission-list {
  flex: 3;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  padding: 20px;
  background-color: #f5f7fa;
}

.lackButt {
  width: 110px;
  font-size: 1em;
  margin-left: 37%;
}

.addButt {
  float: right;
  width: 110px;
  font-size: 1em;
  margin-right: 2%;
}

.el-container {
  text-align: center;
  min-height: 1000px;
}

.el-pagination {
  margin-top: 10px;
}

.box-main {
  text-align: left;
}

el-button {
  margin-left: 20px;
}

.form-font-size {
  text-align: left;
  font-size: 2em;
}

.ProcessPermission {
}

.el-tag {
  font-size: 1.25em;
  float: left;
  margin: 15px;
}

html,
body {
  margin: 0;
  padding: 0;
}

body {
  background: #fff;
}

/*button {*/
/*  margin: 0;*/
/*  padding: 0;*/
/*  border: 0;*/
/*  background: none;*/
/*  font-size: 100%;*/
/*  vertical-align: baseline;*/
/*  font-family: inherit;*/
/*  font-weight: inherit;*/
/*  color: inherit;*/
/*  -webkit-appearance: none;*/
/*  appearance: none;*/
/*  -webkit-font-smoothing: antialiased;*/
/*  -moz-osx-font-smoothing: grayscale;*/
/*}*/

body {
  font: 14px "Helvetica Neue", Helvetica, Arial, sans-serif;
  line-height: 1.4em;
  background: #f5f5f5;
  color: #4d4d4d;
  min-width: 230px;
  max-width: 550px;
  margin: 0 auto;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  font-weight: 300;
}

:focus {
  outline: 0;
}

.hidden {
  display: none;
}

#todoapp {
  background: #fff;
  margin: 180px 0 40px 0;
  position: relative;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2), 0 25px 50px 0 rgba(0, 0, 0, 0.1);
}

#todoapp input::-webkit-input-placeholder {
  font-style: italic;
  font-weight: 300;
  color: #e6e6e6;
}

#todoapp input::-moz-placeholder {
  font-style: italic;
  font-weight: 300;
  color: #e6e6e6;
}

#todoapp input::input-placeholder {
  font-style: italic;
  font-weight: 300;
  color: gray;
}

.new-todo,
.edit {
  position: relative;
  margin: 0;
  width: 100%;
  font-size: 24px;
  font-family: inherit;
  font-weight: inherit;
  line-height: 1.4em;
  border: 0;
  color: inherit;
  padding: 6px;
  border: 1px solid #999;
  box-shadow: inset 0 -1px 5px 0 rgba(0, 0, 0, 0.2);
  box-sizing: border-box;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.new-todo {
  padding: 16px;
  border: none;
  background: rgba(0, 0, 0, 0.003);
  box-shadow: inset 0 -2px 1px rgba(0, 0, 0, 0.03);
}

.main {
  position: relative;
  z-index: 2;
  border-top: 1px solid #e6e6e6;
}

.toggle-all {
  width: 1px;
  height: 1px;
  border: none; /* Mobile Safari */
  opacity: 0;
  position: absolute;
  right: 100%;
  bottom: 100%;
}

#todoapp h2 {
  position: absolute;
  top: -160px;
  width: 100%;
  font-size: 60px;
  font-weight: 100;
  text-align: center;
  color: rgba(175, 47, 47, 0.8);
  -webkit-text-rendering: optimizeLegibility;
  -moz-text-rendering: optimizeLegibility;
  text-rendering: optimizeLegibility;
}

.toggle-all + label {
  width: 60px;
  height: 34px;
  font-size: 0;
  position: absolute;
  top: -52px;
  left: -13px;
  -webkit-transform: rotate(90deg);
  transform: rotate(90deg);
}

.toggle-all + label:before {
  content: "❯";
  font-size: 22px;
  color: #e6e6e6;
  padding: 10px 27px 10px 27px;
}

.toggle-all:checked + label:before {
  color: #737373;
}

.todo-list {
  margin: 0;
  padding: 0;
  list-style: none;
  max-height: 420px;
  overflow: auto;
}

.todo-list li {
  position: relative;
  font-size: 24px;
  border-bottom: 1px solid #ededed;
  height: 60px;
  box-sizing: border-box;
}

.todo-list li:last-child {
  border-bottom: none;
}

.todo-list .view .index {
  position: absolute;
  color: gray;
  left: 10px;
  top: 20px;
  font-size: 16px;
}

.todo-list li .toggle {
  text-align: center;
  width: 40px;
  /* auto, since non-WebKit browsers doesn't support input styling */
  height: auto;
  position: absolute;
  top: 0;
  bottom: 0;
  margin: auto 0;
  border: none; /* Mobile Safari */
  -webkit-appearance: none;
  appearance: none;
}

.todo-list li .toggle {
  opacity: 0;
}

.todo-list li .toggle + label {
  /*
		Firefox requires `#` to be escaped - https://bugzilla.mozilla.org/show_bug.cgi?id=922433
		IE and Edge requires *everything* to be escaped to render, so we do that instead of just the `#` - https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/7157459/
	*/
  background-repeat: no-repeat;
  background-position: center left;
}

.todo-list li label {
  word-break: break-all;
  padding: 15px 15px 15px 60px;
  display: block;
  line-height: 1.2;
  transition: color 0.4s;
}

.todo-list li.completed label {
  color: #d9d9d9;
  text-decoration: line-through;
}

.todo-list li .destroy {
  margin: 0;
  padding: 0;
  border: 0;
  background: none;
  font-size: 100%;
  vertical-align: baseline;
  font-family: inherit;
  font-weight: inherit;
  color: inherit;
  -webkit-appearance: none;
  appearance: none;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.todo-list li .destroy {
  display: none;
  position: absolute;
  top: 0;
  right: 10px;
  bottom: 0;
  width: 40px;
  height: 40px;
  margin: auto 0;
  font-size: 30px;
  color: #cc9a9a;
  margin-bottom: 11px;
  transition: color 0.2s ease-out;
}

.todo-list li .destroy:hover {
  color: #af5b5e;
}

.todo-list li .destroy:after {
  content: "×";
}

.todo-list li:hover .destroy {
  display: block;
}

.todo-list li .edit {
  display: none;
}

.todo-list li.editing:last-child {
  margin-bottom: -1px;
}

.footer {
  color: #777;
  padding: 10px 15px;
  height: 20px;
  text-align: center;
  border-top: 1px solid #e6e6e6;
}

.footer:before {
  content: "";
  position: absolute;
  right: 0;
  bottom: 0;
  left: 0;
  height: 50px;
  overflow: hidden;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.2), 0 8px 0 -3px #f6f6f6,
  0 9px 1px -3px rgba(0, 0, 0, 0.2), 0 16px 0 -6px #f6f6f6,
  0 17px 2px -6px rgba(0, 0, 0, 0.2);
}

.todo-count {
  float: left;
  text-align: left;
}

.todo-count strong {
  font-weight: 300;
}

.filters {
  margin: 0;
  padding: 0;
  list-style: none;
  position: absolute;
  right: 0;
  left: 0;
}

.filters li {
  display: inline;
}

.filters li a {
  color: inherit;
  margin: 3px;
  padding: 3px 7px;
  text-decoration: none;
  border: 1px solid transparent;
  border-radius: 3px;
}

.filters li a:hover {
  border-color: rgba(175, 47, 47, 0.1);
}

.filters li a.selected {
  border-color: rgba(175, 47, 47, 0.2);
}

.clear-completed,
html .clear-completed:active {
  float: right;
  position: relative;
  line-height: 20px;
  text-decoration: none;
  cursor: pointer;
}

.clear-completed:hover {
  text-decoration: underline;
}

.info {
  margin: 50px auto 0;
  color: #bfbfbf;
  font-size: 15px;
  text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
  text-align: center;
}

.info p {
  line-height: 1;
}

.info a {
  color: inherit;
  text-decoration: none;
  font-weight: 400;
}

.info a:hover {
  text-decoration: underline;
}

/*
	Hack to remove background from Mobile Safari.
	Can't use it globally since it destroys checkboxes in Firefox
*/
@media screen and (-webkit-min-device-pixel-ratio: 0) {
  .toggle-all,
  .todo-list li .toggle {
    background: none;
  }

  .todo-list li .toggle {
    height: 40px;
  }
}

@media (max-width: 430px) {
  .footer {
    height: 50px;
  }

  .filters {
    bottom: 10px;
  }
}

/* 头部二 */
.header-part {
  /*background-color: #fff;*/
  background-color: yellowgreen;
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
  vertical-align: bottom;
  width: 40px;
  height: 40px;
  margin-top: 20px;
  border-radius: 99px;
  margin-left: -50px;
}

.uname {
  display: inline-block;
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

.saleTopChart {
  width: 1200px;
  height: 600px;
}

</style>
