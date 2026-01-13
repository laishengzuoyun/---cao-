package com.example.delta.controller;

import com.example.delta.entity.ApiResponse;
import com.example.delta.entity.GoodsPrice;
import com.example.delta.service.GoodsPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/price")
public class PriceController {

    @Autowired
    private GoodsPriceService goodsPriceService;

    @GetMapping("/all")
    public ApiResponse<List<GoodsPrice>> getAllPrices() {
        try {
            List<GoodsPrice> prices = goodsPriceService.getAllPrices();
            return ApiResponse.success(prices);
        } catch (Exception e) {
            return ApiResponse.error("获取物价数据失败：" + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ApiResponse<List<GoodsPrice>> searchByName(@RequestParam String name) {
        try {
            List<GoodsPrice> prices = goodsPriceService.searchByName(name);
            return ApiResponse.success(prices);
        } catch (Exception e) {
            return ApiResponse.error("搜索物价数据失败：" + e.getMessage());
        }
    }
}