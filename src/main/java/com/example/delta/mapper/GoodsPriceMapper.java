package com.example.delta.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.delta.entity.GoodsPrice;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface GoodsPriceMapper extends BaseMapper<GoodsPrice> {
    List<GoodsPrice> searchByName(String name);
}