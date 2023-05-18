<template>
  <!-- footer 部分 -->
  <footer class="footer-part">
    <div class="container">
      <div class="row">
        <div class="col-md-3">
          <div class="footer-widget">
            <a class="footer-logo" href="#"
              ><img src="/images/logo.png" alt="logo"
            /></a>
            <p class="footer-desc footer-text">纯人工养殖 保证肉质鲜美</p>
          </div>
        </div>
        <div class="col-md-3">
          <div class="footer-widget contact">
            <h3 class="footer-title">联系我们</h3>
            <ul class="footer-contact">
              <li>
                <i class="glyphicon glyphicon-envelope"></i>
                <p><span>10086@qq.com</span><span>10086@qq.com</span></p>
              </li>
              <li>
                <i class="glyphicon glyphicon-earphone"></i>
                <p>
                  <span>(+86) 111 1111 1111</span
                  ><span>(+86) 111 1111 1111</span>
                </p>
              </li>
              <li>
                <i class="glyphicon glyphicon-map-marker"></i>
                <p>仲恺农业工程学院</p>
              </li>
            </ul>
          </div>
        </div>
        <div class="col-md-3">
          <div class="footer-widget">
            <h3 class="footer-title">快速链接</h3>
            <div class="footer-links">
              <ul>
                <li><a href="#/user/me" target="_blank">我的账户</a></li>
                <li><a href="#" @click.prevent="onOrderClick">历史订单</a></li>
                <li><a href="#">浏览记录</a></li>
                <li><a href="#">畅销榜</a></li>
              </ul>
              <ul>
                <li><a href="#">位置</a></li>
                <li><a href="#">合同</a></li>
                <li><a href="#">指南</a></li>
                <li><a href="#">问题反馈</a></li>
              </ul>
            </div>
          </div>
        </div>
        <div class="col-md-3">
          <div class="footer-widget">
            <h3 class="footer-title">下载app</h3>
            <p class="footer-desc">app更方便</p>
            <div class="footer-app">
              <a href="#"><img src="/images/google-store.png" alt="google" /></a
              ><a href="#"><img src="/images/app-store.png" alt="app" /></a>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-12">
          <div class="footer-bottom">
            <p class="footer-copytext">
              &copy;All Copyrights Reserved by ysh zxr zzn team
            </p>
            <div class="footer-card">
              <a href="#"
                ><img src="/images/payment/jpg/01.jpg" alt="payment" /></a
              ><a href="#"
                ><img src="/images/payment/jpg/02.jpg" alt="payment" /></a
              ><a href="#"
                ><img src="/images/payment/jpg/03.jpg" alt="payment" /></a
              ><a href="#"
                ><img src="/images/payment/jpg/04.jpg" alt="payment"
              /></a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </footer>
  <!-- footer部分结束 -->
</template>

<script>
export default {
  methods: {
    onOrderClick() {
      this.$router.push({
        path: "/user/me",
        params: {
          userMePageIndex: 4,
        },
      });
    },
  },
};
</script>

<style scoped>
/* footer部分 */
.footer-part {
  padding-top: 40px;
  font-size: 16px;
}

.footer-widget {
  margin-left: 40px;
  display: grid;
  grid-template-columns: 100%;
}

.footer-logo {
  margin-bottom: 25px;
}

.footer-logo img {
  margin-left: -130px;
  width: 400px;
  height: 180px;
}

.footer-text {
  margin-left: -70px;
  font-size: 25px;
  font-weight: 700;
  color: yellowgreen;
}

.footer-desc {
  margin-bottom: 30px;
}

.footer-title {
  margin-bottom: 25px;
  letter-spacing: -0.3px;
  text-transform: capitalize;
}

.footer-widget.contact {
  margin-left: 70px;
}

.footer-widget.contact ul li i {
  color: #119744;
}

.footer-contact li {
  display: flex;
  align-items: center;
  margin-bottom: 18px;
}

.footer-contact li:last-child {
  margin-bottom: 0;
}

.footer-contact li i {
  font-size: 30px;
  margin-right: 15px;
  color: var(--primary);
}

.footer-contact li p span {
  display: block;
}

.footer-links {
  display: grid;
  grid-gap: 50px;
  grid-template-columns: 1fr 1fr;
}

.footer-links ul li {
  margin-bottom: 15px;
}

.footer-links ul li:last-child {
  margin-bottom: 0;
}

.footer-links ul li a {
  color: var(--text);
  text-transform: capitalize;
  transition: all linear 0.3s;
  -webkit-transition: all linear 0.3s;
  -moz-transition: all linear 0.3s;
  -ms-transition: all linear 0.3s;
  -o-transition: all linear 0.3s;
}

.footer-links ul li a:hover {
  color: var(--primary);
  text-decoration: underline;
}

.footer-app {
  display: grid;
  grid-gap: 10px;
  grid-template-columns: 1fr 1fr;
}

.footer-app a img {
  width: 100%;
}

.footer-bottom {
  margin-top: 75px;
  padding: 30px 50px;
  border-radius: 8px 8px 0 0;
  background: #119744;
  border-top: 1px solid var(--border);
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.footer-copytext {
  font-size: 15px;
  color: #fff;
}

.footer-card a {
  margin-left: 10px;
}

.footer-card a img {
  width: 50px;
  border-radius: 3px;
}

/* footer部分结束 */
</style>
