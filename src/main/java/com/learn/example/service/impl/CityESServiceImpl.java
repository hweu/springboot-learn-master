package com.learn.example.service.impl;

import com.learn.example.model.City;
import com.learn.example.service.CityESService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 城市 ES 业务逻辑实现类
 */
@Service
public class CityESServiceImpl implements CityESService {
    private static final Logger log = LoggerFactory.getLogger(CityESServiceImpl.class);
    /* 分页参数 */
    Integer PAGE_SIZE = 12;          // 每页数量
    Integer DEFAULT_PAGE_NUMBER = 0; // 默认当前页码
    /* 搜索模式 */
    String SCORE_MODE_SUM = "sum"; // 权重分求和模式
    Float  MIN_SCORE = 10.0F;      // 由于无相关性的分值默认为 1 ，设置权重分最小值为 10

    /*@Autowired
    private CityRepository cityRepository;*/

    @Override
    public Long saveCity(City city) {
        /*City cityResult = (City) cityRepository.save(city);
        return cityResult.getId();*/
        return null;
    }

    @Override
    public List<City> searchCity(Integer pageNumber, Integer pageSize, String searchContent) {
        // 校验分页参数
        if (pageSize == null || pageSize <= 0) {
            pageSize = PAGE_SIZE;
        }

        if (pageNumber == null || pageNumber < DEFAULT_PAGE_NUMBER) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        log.info("\n searchCity: searchContent [" + searchContent + "] \n ");

        Pageable pageable = new PageRequest(pageNumber, pageSize);

        //Page<City> cityPage = cityRepository.search(getCitySearchQuery(searchContent),pageable);
        //return cityPage.getContent();
        return null;
    }

    /**
     * @param searchContent 搜索内容
     * @return
     */
    private QueryBuilder getCitySearchQuery(String searchContent) {
        /// 构建搜索查询
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("cityname", searchContent);
        return queryBuilder;
    }
}
