package com.lxd.crawlJD.service;

import com.lxd.crawlJD.dao.JDDao;
import com.lxd.crawlJD.pojo.JDItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class JDServiceImpl implements JDService {
    @Autowired
    private JDDao jdDao;

    @Override
    public void save(JDItem jdItem) {
        jdDao.save(jdItem);
    }

    @Override
    public List<JDItem> findAll(JDItem jdItem) {
        Example<JDItem> example = Example.of(jdItem);
        List<JDItem> all = jdDao.findAll(example);
        return all;
    }
}
