package com.github.hualuomoli.demo.raml.app.repeat.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.demo.raml.app.repeat.entity.PostJsonJsonEntity;
import com.github.hualuomoli.demo.raml.app.repeat.entity.PostJsonResultJsonEntityUser;
import com.github.hualuomoli.demo.raml.app.repeat.entity.PostUrlencodedUrlEncodedEntity;
import com.github.hualuomoli.demo.raml.app.repeat.entity.PostUrlencodedResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.repeat.entity.PostFileFileEntity;
import com.github.hualuomoli.demo.raml.app.repeat.entity.PostFileResultJsonEntity;

/**
 * @Description 重复值
 * @Author hualuomoli
 * @Date 2016-05-19 10:25:26
 * @Version 1.0
 */
@Repository(value = "com.github.hualuomoli.demo.raml.app.repeat.RepeatMapper")
public interface RepeatMapper {

	PostJsonResultJsonEntityUser
	postJson(PostJsonJsonEntity postJsonJsonEntity);
	

}
