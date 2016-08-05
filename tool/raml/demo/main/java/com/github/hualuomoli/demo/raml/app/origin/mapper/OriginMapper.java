package com.github.hualuomoli.demo.raml.app.origin.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.demo.raml.app.origin.entity.GetListEntity;
import com.github.hualuomoli.demo.raml.app.origin.entity.GetListResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.origin.entity.GetObjectEntity;
import com.github.hualuomoli.demo.raml.app.origin.entity.GetObjectResultJsonEntity;

/**
 * @Description 包括省市区、养殖类型、养殖种类等基础数据
 * @Author hualuomoli
 * @Date 2016-05-19 10:25:26
 * @Version 1.0
 */
@Repository(value = "com.github.hualuomoli.demo.raml.app.origin.OriginMapper")
public interface OriginMapper {

	java.util.List<GetListResultJsonEntity>
	getList(GetListEntity getListEntity);
	
	GetObjectResultJsonEntity
	getObject(GetObjectEntity getObjectEntity);
	

}
