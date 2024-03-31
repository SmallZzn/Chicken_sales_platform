package com.zhao.salechicken.controller;

import com.zhao.salechicken.common.R;
import com.zhao.salechicken.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/saleInfo")
public class SaleInfoController {

    @Autowired
    private OrderService orderService;

    /**
     * 查看销售情况
     * @return
     */
    @GetMapping("/selectTotalSales")
    public R<List<Double>> selectTotalSales() {
        List<Double> totalSales = orderService.selectTotalSales();
        return R.success(totalSales);
    }
}
