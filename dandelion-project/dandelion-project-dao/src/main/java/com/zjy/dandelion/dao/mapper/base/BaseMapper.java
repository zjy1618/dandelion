package com.zjy.dandelion.dao.mapper.base;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.zjy.dandelion.entity.BaseEntity;
import com.zjy.dandelion.entity.example.Example;

public interface BaseMapper <T extends BaseEntity>{

	@SelectProvider(type = BaseDaoTemplate.class, method = "countByExample")
	int countByExample(@Param("example") Example<T> example);

	@SelectProvider(type = BaseDaoTemplate.class, method = "countColByExample")
	int countColByExample(@Param("col") String col,
			@Param("example") Example<T> example);

	@DeleteProvider(type = BaseDaoTemplate.class, method = "deleteByExample")
	int deleteByExample(@Param("example") Example<T> example);

	@DeleteProvider(type = BaseDaoTemplate.class, method = "deleteByPrimaryKey")
	int deleteByPrimaryKey(T entity);

	@InsertProvider(type = BaseDaoTemplate.class, method = "insertSelective")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	int insert(T record);

	@InsertProvider(type = BaseDaoTemplate.class, method = "insert")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	int insertWithNullValue(T record);

//	@SelectProvider(type = BaseDaoTemplate.class, method = "selectByExample")
//	@ResultMap("ListMap")
//	List<T> selectByExample(@Param("example") Example<T> example,
//			@Param("pager") Pager<T> pager);

//	@SelectProvider(type = BaseDaoTemplate.class, method = "selectOneByExample")
//	@ResultMap("DetailMap")
//	T selectOneByExample(@Param("example") Example<T> example);

	@SelectProvider(type = BaseDaoTemplate.class, method = "sumByExample")
	Double sumByExample(@Param("field") String field,
			@Param("example") Example<T> example);

	@UpdateProvider(type = BaseDaoTemplate.class, method = "updateByExampleSelective")
	int updateByExample(@Param("record") T record,
			@Param("example") Example<T> example);

	@UpdateProvider(type = BaseDaoTemplate.class, method = "updateByExample")
	int updateByExampleWithNullValue(@Param("record") T record,
			@Param("example") Example<T> example);

	@UpdateProvider(type = BaseDaoTemplate.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKey(T record);

	@UpdateProvider(type = BaseDaoTemplate.class, method = "updateByPrimaryKey")
	int updateByPrimaryKeyWithNullValue(T record);
}
