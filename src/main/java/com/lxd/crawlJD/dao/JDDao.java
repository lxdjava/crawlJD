package com.lxd.crawlJD.dao;

import com.lxd.crawlJD.pojo.JDItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JDDao extends JpaRepository<JDItem,Long> {

}
