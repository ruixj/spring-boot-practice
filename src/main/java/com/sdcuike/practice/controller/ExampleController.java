package com.sdcuike.practice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.doctor.beaver.domain.result.ModelResult;
import com.google.common.collect.Lists;
import com.sdcuike.practice.config.CommonConfig;
import com.sdcuike.practice.dao.CityRepository;
import com.sdcuike.practice.domain.City;

@RestController
@RequestMapping(path = "/example", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ExampleController {
    private final Logger   log = LoggerFactory.getLogger(getClass());

    @Resource
    private CommonConfig   commonConfig;

    @Resource
    private CityRepository cityRepository;

    @RequestMapping("/")
    public ModelResult<String> home() {
        ModelResult<String> modelResult = new ModelResult<>();
        modelResult.setData("hello world spring boot");
        return modelResult;
    }

    /**
     * 测试配置
     */
    @RequestMapping("/testConfig")
    public ModelResult<String> testConfig() {
        ModelResult<String> modelResult = new ModelResult<>();
        modelResult.setData(commonConfig.getAppName());
        return modelResult;
    }

    @RequestMapping("/testConfigN")
    public ModelResult<String> testConfig_(@RequestHeader("headerN") String headerN) {
        ModelResult<String> modelResult = new ModelResult<>();
        modelResult.setData(commonConfig.getAppName());
        return modelResult;
    }

    @RequestMapping("/testConfigE")
    public ModelResult<String> testConfig_E() {
        ModelResult<String> modelResult = new ModelResult<>();
        modelResult.setData(commonConfig.getAppName());
        throw new RuntimeException("test e");
    }

    @RequestMapping("/db")
    public ModelResult<List<City>> testJPA() {
        ModelResult<List<City>> modelResult = new ModelResult<>();
        MDC.put("WHO", "WHO");
        log.info("testJPA");
        cityRepository.save(new City("name", "city"));
        Iterable<City> iterable = cityRepository.findAll();
        ArrayList<City> list = Lists.newArrayList(iterable);
        modelResult.setData(list);
        return modelResult;
    }

    @RequestMapping("/test-request-decrypt-response-encrypt")
    public ModelResult<List<City>> testRequestDecryptResponseEncryptBody(@RequestBody Map<String, String> map) {
        ModelResult<List<City>> modelResult = new ModelResult<>();
        cityRepository.save(new City(map.get("name"), map.get("city")));
        Iterable<City> iterable = cityRepository.findAll();
        ArrayList<City> list = Lists.newArrayList(iterable);
        modelResult.setData(list);
        return modelResult;
    }
}
