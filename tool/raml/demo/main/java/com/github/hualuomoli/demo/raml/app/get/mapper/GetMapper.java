package com.github.hualuomoli.demo.raml.app.get.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.demo.raml.app.get.entity.GetUriparamByIdEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetUriparamByIdResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetQueryparamEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetQueryparamResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetUriqueryparamByPageNumberPageSizeEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetUriqueryparamByPageNumberPageSizeResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetNoparamEntity;
import com.github.hualuomoli.demo.raml.app.get.entity.GetNoparamResultJsonEntity;

/**
 * @Description get方式请求
 * @Author hualuomoli
 * @Date 2016-05-12 15:42:01
 * @Version 1.0
 */
@Repository(value = "com.github.hualuomoli.demo.raml.app.get.GetMapper")
public interface GetMapper {


}
