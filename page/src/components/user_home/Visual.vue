<template>
  <!-- visual部分 -->
  <section class="visual-part">
    <div class="container">
      <div class="row">
        <div class="col-sm-6 col-lg-3">
          <div class="visual-wrap">
            <div class="visual-icon">
              <i class="glyphicon glyphicon-plane"></i>
            </div>
            <div class="visual-content">
              <h5>免费配送</h5>
              <p>免费配送，一键直达</p>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-lg-3">
          <div class="visual-wrap">
            <div class="visual-icon">
              <i class="glyphicon glyphicon-refresh"></i>
            </div>
            <div class="visual-content">
              <h5>24h无理由退换</h5>
              <p>注重客户体验</p>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-lg-3">
          <div class="visual-wrap">
            <div class="visual-icon">
              <i class="glyphicon glyphicon-user"></i>
            </div>
            <div class="visual-content">
              <h5>人工客服</h5>
              <p>人工客服24h在线</p>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-lg-3">
          <div class="visual-wrap">
            <div class="visual-icon">
              <i class="glyphicon glyphicon-lock"></i>
            </div>
            <div class="visual-content">
              <h5>安全支付</h5>
              <p>安全支付，放心买</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!-- visual部分结束 -->
</template>

<script>
export default {};
</script>

<style scoped>
/* visual部分 */
.visual-part {
  padding: 100px 0;
  background: #f8fffa;
}

.visual-wrap {
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
}

.visual-wrap:hover .visual-icon i {
  color: var(--white);
  background: var(--primary);
}

.visual-icon {
  margin-right: 20px;
}

.visual-icon i {
  width: 50px;
  height: 50px;
  font-size: 25px;
  line-height: 63px;
  border-radius: 50%;
  text-align: center;
  display: inline-block;
  color: #119744;
  background: var(--white);
  border: 3px double var(--primary);
  box-shadow: var(--primary-bshadow);
  transition: all linear 0.3s;
  -webkit-transition: all linear 0.3s;
  -moz-transition: all linear 0.3s;
  -ms-transition: all linear 0.3s;
  -o-transition: all linear 0.3s;
}

.visual-content h5 {
  font-size: 17px;
  margin-bottom: 8px;
  text-transform: capitalize;
}

.visual-content p {
  font-size: 15px;
  line-height: 22px;
}

/* visual部分结束 */
</style>
