import Vue from "vue";
import VueRouter from "vue-router";
import MyLogin from "../components/MyLogin.vue";
import Register from "../components/Register.vue";
import AdminHome from "../components/adminHome.vue";

import UserHomeMain from "../components/user_home/UserHomeMain.vue";
import UserHome from "../components/UserHome.vue";
import GoodsCart from "../components/cart/GoodsCart.vue";
import Payment from "../components/payment/Payment.vue";
import OrderDetail from "@/components/order/OrderDetail.vue";
import review from "@/components/order/review.vue";
import SearchGoodsShow from "../components/goods/SearchGoodsShow.vue";
import GoodsDetail from "../components/goods/GoodsDetail.vue";


import UserMeView from "../views/UserMeView.vue";

Vue.use(VueRouter);

const routes = [
  { path: "/", redirect: "/user/home" },
  { path: "/login", name: "Login", component: MyLogin },
  { path: "/register", name: "Register", component: Register },
  {
    path: "/admin",
    name: "Admin",
    component: AdminHome,
  },
  {
    path: "/user",
    redirect: "/user/home",
    component: UserHome,
    children: [
      { path: "/user/home", component: UserHomeMain },
      { path: "cart", component: GoodsCart },
      { path: "pay", name: "Payment", component: Payment },
      { path: "order/:orderId", component: OrderDetail },
      { path: "/review/:productId/:orderdetailId/:orderId", component: review},
      {
        path: "search/:productName",
        name: "SearchGoods",
        component: SearchGoodsShow,
      },
      { path: "goods/:id", name: "GoodsDetail", component: GoodsDetail },
    ],
  },
  { path: "/user/me", name: "UserMe", component: UserMeView },
];

const router = new VueRouter({
  routes,
});

router.beforeEach((to, from, next) => {
  if (
    to.path == "/admin" ||
    to.path == "/user/cart" ||
    to.name === "Payment" ||
    to.name === "UserMe"
  ) {
    //判断本地是否登录
    if (localStorage.getItem("loginId")) {
      next();
    } else {
      // 未登陆
      next({ name: "Login" });
    }
  } else {
    //其他情况，直接放行
    next();
  }
});

export default router;
