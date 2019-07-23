package com.lxd.crawlJD.service;

import com.lxd.crawlJD.pojo.JDItem;
import org.springframework.data.domain.Example;

import java.util.List;

public interface JDService {
    public void save(JDItem jdItem);

    public List<JDItem> findAll(JDItem jdItem);
}
