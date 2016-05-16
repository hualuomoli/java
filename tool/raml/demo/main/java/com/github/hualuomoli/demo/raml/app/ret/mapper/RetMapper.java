package com.github.hualuomoli.demo.raml.app.ret.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.demo.raml.app.ret.entity.GetObjectdataEntity;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetObjectdataResultJsonEntityUser;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetNodataEntity;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetNodataResultJsonEntity;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetPagedataEntity;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetPagedataResultJsonEntityUser;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetListdataEntity;
import com.github.hualuomoli.demo.raml.app.ret.entity.GetListdataResultJsonEntityUser;

/**
 * @Description 响应
 * @Author hualuomoli
 * @Date 2016-05-16 10:27:44
 * @Version 1.0
 */
@Repository(value = "com.github.hualuomoli.demo.raml.app.ret.RetMapper")
public interface RetMapper {

	GetObjectdataResultJsonEntityUser
	getObjectdata(GetObjectdataEntity getObjectdataEntity);
	
	java.util.List<GetPagedataResultJsonEntityUser>
	getPagedata(GetPagedataEntity getPagedataEntity);
	
	java.util.List<GetListdataResultJsonEntityUser>
	getListdata(GetListdataEntity getListdataEntity);
	

}
