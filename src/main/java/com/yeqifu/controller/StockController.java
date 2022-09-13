package com.yeqifu.controller;

import com.yeqifu.service.StockService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: yeqifu-
 * @date: 2022/8/30 22:00
 */
@RestController
@RequestMapping("/stock")
public class StockController {

    @Resource
    private StockService stockService;

    @PostMapping("/reduce")
    public String reduce() {
        stockService.reduceThree();
        return "stock reduce!";
    }

}
