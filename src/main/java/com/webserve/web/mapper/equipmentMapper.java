package com.webserve.web.mapper;

import com.webserve.web.bean.equipment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface equipmentMapper {
    public equipment getEquipById(String id);

    public void insertEquipment(equipment equipment);

}
