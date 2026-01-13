package com.example.delta.task;

import com.example.delta.entity.GoodsPrice;
import com.example.delta.service.GoodsPriceService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
public class CrawlerTask {

    @Autowired
    private GoodsPriceService goodsPriceService;

    // 每5分钟执行一次
    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void crawlPrices() {
        try {
            // 支持分页爬取，爬取第1到第8页数据
            int totalPages = 8;
            int totalSavedCount = 0;
            
            for (int page = 1; page <= totalPages; page++) {
                String url = String.format("https://orzice.com/v/ammo?a=ammo&top=1-2&p=%d&grade=-1&n=", page);
                System.out.println("开始爬取第 " + page + " 页: " + url);
                
                Document doc = Jsoup.connect(url)
                        .timeout(10000)
                        .get();

                // 根据子弹数据页面的结构调整选择器
                Elements goodsList = doc.select(".ammo-item");
                
                // 如果没有找到.ammo-item类，尝试其他常见的选择器
                if (goodsList.isEmpty()) {
                    goodsList = doc.select("tr[class*='ammo']");
                    System.out.println("尝试选择器: tr[class*='ammo']，找到 " + goodsList.size() + " 个物品");
                }
                
                if (goodsList.isEmpty()) {
                    goodsList = doc.select("tr:not(:first-child)"); // 排除表头
                    System.out.println("尝试选择器: tr:not(:first-child)，找到 " + goodsList.size() + " 个物品");
                }

                System.out.println("第 " + page + " 页找到 " + goodsList.size() + " 个物品");
                
                int pageSavedCount = 0;
                for (Element item : goodsList) {
                GoodsPrice goodsPrice = new GoodsPrice();
                
                // 解析物品名称 - 调整选择器以匹配实际页面结构
                String goodsName = item.select(".ammo-name, .name, td:nth-child(1)").text();
                if (goodsName.isEmpty()) {
                    System.out.println("跳过空名称的物品");
                    continue;
                }
                goodsPrice.setGoodsName(goodsName);
                
                // 解析价格 - 调整选择器以匹配实际页面结构
                String priceStr = item.select(".ammo-price, .price, td:nth-child(2)").text();
                System.out.println("物品: " + goodsName + "，原始价格: " + priceStr);
                
                double price = 0;
                boolean priceParsed = false;
                
                // 处理模板语法的情况，提取其中的数值ID
                if (priceStr.contains("{{")) {
                    // 从模板中提取数值ID，例如: NumJBWJB(519) → 519
                    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("NumJBWJB\\((\\d+)\\)");
                    java.util.regex.Matcher matcher = pattern.matcher(priceStr);
                    if (matcher.find()) {
                        String numericId = matcher.group(1);
                        price = Double.parseDouble(numericId);
                        priceParsed = true;
                        System.out.println("提取模板数值ID: " + price);
                    } else {
                        System.out.println("无法从模板中提取数值ID，跳过: " + priceStr);
                        continue;
                    }
                } else {
                    // 处理正常价格格式
                    priceStr = priceStr.replace("¥", "").replace(",", "").replace("K", "000").replace("M", "000000");
                    if (!priceStr.isEmpty()) {
                        try {
                            price = Double.parseDouble(priceStr);
                            priceParsed = true;
                            System.out.println("解析价格成功: " + price);
                        } catch (NumberFormatException e) {
                            // 如果价格解析失败，跳过这个条目
                            System.out.println("价格解析失败，跳过: " + priceStr);
                            continue;
                        }
                    } else {
                        System.out.println("价格为空，跳过: " + goodsName);
                        continue;
                    }
                }
                
                // 只有当价格成功解析时才继续
                if (priceParsed) {
                    goodsPrice.setPrice(price);
                } else {
                    System.out.println("价格解析失败，跳过: " + goodsName);
                    continue;
                }
                
                // 解析涨跌幅度 - 调整选择器以匹配实际页面结构
                String riseRate = item.select(".ammo-change, .rise-rate, .change, td:nth-child(3)").text();
                goodsPrice.setRiseRate(riseRate);
                
                // 设置物品类型为子弹
                goodsPrice.setGoodsType("子弹");
                
                // 设置更新时间
                goodsPrice.setUpdateTime(LocalDateTime.now());
                
                // 保存或更新数据
                goodsPriceService.saveOrUpdatePrice(goodsPrice);
                pageSavedCount++;
                totalSavedCount++;
                System.out.println("保存成功: " + goodsName);
            }
            
            System.out.println("第 " + page + " 页爬取完成，成功保存 " + pageSavedCount + " 个物品数据");
            
            // 休息1秒，避免请求过快
            Thread.sleep(1000);
        }
        
        System.out.println("全部页面爬取完成，总共成功保存 " + totalSavedCount + " 个物品数据");
        } catch (Exception e) {
            // 网络异常时，不更新数据，保留最后一次的数据
            System.out.println("爬取失败，保留最后一次数据: " + e.getMessage());
        }
    }
}