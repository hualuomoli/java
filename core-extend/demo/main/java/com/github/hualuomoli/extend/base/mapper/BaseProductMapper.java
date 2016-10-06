package com.github.hualuomoli.extend.base.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.hualuomoli.extend.base.entity.BaseProduct;
import com.github.hualuomoli.extend.entity.Product;

// #BaseProduct
@Repository(value = "com.github.hualuomoli.extend.base.mapper.BaseProductMapper")
public interface BaseProductMapper {

	
	BaseProduct get(String id);

	int insert(Product product);
	
	<T extends Product> int batchInsert(@Param(value = "list") List<T> list);

	int update(Product product);

	
	int delete(String id);
	
	int deleteByIds(@Param(value = "ids") String[] ids);
	

	List<BaseProduct> findList(BaseProduct baseProduct);

}
