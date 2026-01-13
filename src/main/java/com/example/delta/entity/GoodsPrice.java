package com.example.delta.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;

@TableName("goods_price")
public class GoodsPrice {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("goods_name")
    private String goodsName;

    @TableField("price")
    private Double price;

    @TableField("rise_rate")
    private String riseRate;

    @TableField("goods_type")
    private String goodsType;

    @TableField("update_time")
    private LocalDateTime updateTime;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getRiseRate() {
        return riseRate;
    }

    public void setRiseRate(String riseRate) {
        this.riseRate = riseRate;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}