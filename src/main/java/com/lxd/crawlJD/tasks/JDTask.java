package com.lxd.crawlJD.tasks;

import com.lxd.crawlJD.pojo.JDItem;
import com.lxd.crawlJD.service.JDService;
import com.lxd.crawlJD.utils.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JDTask {
    @Autowired
    private JDService jdService;

    private HttpUtil httpUtil = new HttpUtil();

    //每当下载任务完成后，间隔多长时间进行下一次的任务
    @Scheduled(fixedDelay = 100*1000)
    public void jdTask() throws IOException{
        //声明需要解析的初始地址
        String url = "https://search.jd.com/Search?" +
                "keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&" +
                "qrst=1&rt=1&stop=1&vt=2&wq=%E6%89%8B%E6%9C%BA" +
                "&cid2=653&cid3=655&" +
                "s=60&click=0&page=";
        //按照页码对手机的搜索信息进行遍历爬取
        for (int i=1;i<3;i+=2){
            String html = httpUtil.doGetHtml(url + i);
            //解析页面，获取商品数据并保存
            this.parse(html);
        }
    }
    //解析页面，获取数据
    private void parse(String html) {
        //解析html获取DOM元素
        Document document = Jsoup.parse(html);
        //获取一整页商品
        Elements elements = document.select("#J_goodList>ul>li");

        for (Element element:elements){
            //获取spu
            long spu = Long.parseLong(elements.attr("data-spu"));
            //获取sku
            Elements elements1 = element.select("li.ps-item");
            for (Element element1:elements1){
                long sku = Long.parseLong(element1.select("[data-spu]").attr("data-spu"));
                //根据sku查询商品数据
                JDItem jdItem = new JDItem();
                jdItem.setSku(sku);
                List<JDItem> list = jdService.findAll(jdItem);
                //如果商品存在，则不保存该商品，直接跳入下一个循环
                if (list.size()>0){
                    continue;
                }
                jdItem.setSpu(spu);
                //商品链接
                jdItem.setUrl("https://item.jd.com/"+sku+".html");
                //商品图片
                jdItem.setPic(element.select("img[data=sku]").first().attr("src"));
                //商品价格
            }

        }

    }
}
