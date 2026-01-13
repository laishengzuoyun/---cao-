package com.example.delta.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.delta.entity.GoodsPrice;
import com.example.delta.mapper.GoodsPriceMapper;
import com.example.delta.service.GoodsPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GoodsPriceServiceImpl extends ServiceImpl<GoodsPriceMapper, GoodsPrice> implements GoodsPriceService {

    @Autowired
    private GoodsPriceMapper goodsPriceMapper;

    @Override
    public List<GoodsPrice> getAllPrices() {
        return goodsPriceMapper.selectList(null);
    }

    @Override
    public List<GoodsPrice> searchByName(String name) {
        return goodsPriceMapper.searchByName(name);
    }

    @Override
    public void saveOrUpdatePrice(GoodsPrice goodsPrice) {
        this.saveOrUpdate(goodsPrice);
    }
}