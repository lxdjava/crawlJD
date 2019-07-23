package com.lxd.crawlJD.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "JDItem")
@Data
public class JDItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long spu;//产品标准编号
    private Long sku;//一个产品下不同型号的编号
    private String title;//产品名
    private Double price;//产品价格
    private String pic;//产品图片
    private String url;//产品详情链接
    private Date created;//创建时间
    private Date updated;//更新时间
}
