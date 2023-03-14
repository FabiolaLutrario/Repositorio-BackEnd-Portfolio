package com.project.portfolio.controller;

import com.project.portfolio.entity.Porcentaje;
import com.project.portfolio.service.PorcentajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/porcentaje")
@CrossOrigin(origins = "https://frontendportfolio-bff8c.web.app")
public class PorcentajeController {

    @Autowired
    PorcentajeService porcentajeService;

    @GetMapping("/list")
    public ResponseEntity<List<Porcentaje>> list(){
        List<Porcentaje> list = porcentajeService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
}
