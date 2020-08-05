package com.webserve.web.controller;

import com.webserve.web.bean.equipment;
import com.webserve.web.mapper.equipmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class equipmentController {
    @Autowired
    equipmentMapper equipmentMapper;

    @GetMapping("/equipid/{id}")
    public equipment getEquipment(@PathVariable("id") String id){
        System.out.println(id);
        return equipmentMapper.getEquipById(id);

    }
    @GetMapping("/equip/{id}&{owner}")
    public void insertEquipment(equipment equipment){
        equipmentMapper.insertEquipment(equipment);

    }

}
