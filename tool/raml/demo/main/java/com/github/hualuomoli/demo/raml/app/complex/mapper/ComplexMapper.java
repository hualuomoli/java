package com.github.hualuomoli.demo.raml.app.complex.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.demo.raml.app.complex.entity.PostUriformByIdJsonEntity;
import com.github.hualuomoli.demo.raml.app.complex.entity.PostUriformByIdResultJsonEntityUser;

/**
 * @Description 复杂json参数
 * @Author hualuomoli
 * @Date 2016-05-16 10:27:45
 * @Version 1.0
 */
@Repository(value = "com.github.hualuomoli.demo.raml.app.complex.ComplexMapper")
public interface ComplexMapper {

	PostUriformByIdResultJsonEntityUser
	postUriformById(PostUriformByIdJsonEntity postUriformByIdJsonEntity);
	

}
