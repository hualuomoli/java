package com.github.hualuomoli.demo.raml.app.urlEncoded.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostNoparamEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostNoparamResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostUriparamByIdEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostUriparamByIdResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostUriformparamByPageNumberPageSizeUrlEncodedEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostUriformparamByPageNumberPageSizeResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostFormparamUrlEncodedEntity;
import com.github.hualuomoli.demo.raml.app.urlEncoded.entity.PostFormparamResultJsonEntity;

/**
 * @Description 表单提交
 * @Author hualuomoli
 * @Date 2016-05-16 10:27:45
 * @Version 1.0
 */
@Repository(value = "com.github.hualuomoli.demo.raml.app.urlEncoded.UrlEncodedMapper")
public interface UrlEncodedMapper {


}
