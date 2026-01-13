package com.example.delta.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.delta.entity.GoodsPrice;
import java.util.List;

public interface GoodsPriceService extends IService<GoodsPrice> {
    List<GoodsPrice> getAllPrices();
    List<GoodsPrice> searchByName(String name);
    void saveOrUpdatePrice(GoodsPrice goodsPrice);
}