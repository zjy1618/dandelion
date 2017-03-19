package com.zjy.dandelion.dao.mapper.base;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.jdbc.SQL;

import com.zjy.dandelion.entity.BaseEntity;
import com.zjy.dandelion.entity.example.Example;
import com.zjy.dandelion.entity.example.Example.Criteria;
import com.zjy.dandelion.entity.example.Example.Criterion;
import com.zjy.dandelion.page.Pager;


/**
 *
 *
 *
 * Created by chars on 2015 下午4:23:40.
 *
 * Copyright © mizhuanglicai
 */
public class BaseDaoTemplate<T extends BaseEntity> {

	final private Log logger = LogFactory.getLog(getClass());

	public String countByExample(final ParamMap<?> map) {
		final Example<?> example = (Example<?>) map.get("example");
		final String sql = new SQL() {
			{
				SELECT("count(id)");
				FROM(example.getTableName());
				exampleWhereClause(this, example);
			}
		}.toString();
		logger.debug(sql.replaceAll("\n", " "));
		return sql;
	}

	public String countColByExample(final ParamMap<?> map) {
		final String col = (String) map.get("col");
		final Example<?> example = (Example<?>) map.get("example");
		final String sql = new SQL() {
			{
				SELECT("count(" + col + ")");
				FROM(example.getTableName());
				exampleWhereClause(this, example);
			}
		}.toString();
		logger.debug(sql.replaceAll("\n", " "));
		return sql;
	}

	public String deleteByExample(final Example<?> example) {
		final String sql = new SQL() {
			{
				DELETE_FROM(example.getTableName());
				exampleWhereClause(this, example);
			}
		}.toString();
		logger.debug(sql.replaceAll("\n", " "));
		return sql;
	}

	public String deleteByPrimaryKey(final T entity) {
		final String sql = new SQL() {
			{
				DELETE_FROM(entity.getTableName());
				WHERE("id=#{id}");
			}
		}.toString();
		logger.debug(sql.replaceAll("\n", " "));
		return sql;
	}

	public String insert(final T entity) {

		final String sql = new SQL() {
			{
				INSERT_INTO(entity.getTableName());
				entity.caculationColumnList();
				VALUES(entity.returnInsertColumnsName(false),
						entity.returnInsertColumnsDefine(false));
			}
		}.toString();

		logger.debug(sql.replaceAll("\n", " "));
		return sql;
	}

	public String insertSelective(final T entity) {
		final String sql = new SQL() {
			{
				INSERT_INTO(entity.getTableName());
				entity.caculationColumnList();
				VALUES(entity.returnInsertColumnsName(true),
						entity.returnInsertColumnsDefine(true));
			}
		}.toString();

		logger.debug(sql.replaceAll("\n", " "));
		return sql;
	}

	@SuppressWarnings("unchecked")
	public String selectByExample(final ParamMap<?> map) {
		final Example<?> example = (Example<?>) map.get("example");
		final Pager<T> pager = map.get("pager") == null ? null : (Pager<T>) map
				.get("pager");
		String sql = new SQL() {
			{
				SELECT("*");
				FROM(example.getTableName());
				exampleWhereClause(this, example);
			}
		}.toString();

		if (StringUtils.isNotBlank(example.getOrderByClause())) {
			sql += (" ORDER BY " + example.getOrderByClause());
		}

		if (pager != null) {
			sql += " limit " + pager.getStartNumber() + ","
					+ pager.getPageSize();
		}

		logger.debug(sql.replaceAll("\n", " "));
		return sql;
	}

	public String selectByPrimaryKey(final T entity) {
		final String sql = new SQL() {
			{
				SELECT("*");
				FROM(entity.getTableName());
				WHERE("id=#{id}");
			}
		}.toString();

		logger.debug(sql.replaceAll("\n", " "));
		return sql;
	}

	public String selectOneByExample(final ParamMap<?> map) {
		final Example<?> example = (Example<?>) map.get("example");
		String sql = new SQL() {
			{
				SELECT("*");
				FROM(example.getTableName());
				exampleWhereClause(this, example);
			}
		}.toString();

		if (StringUtils.isNotBlank(example.getOrderByClause())) {
			sql += (" ORDER BY " + example.getOrderByClause());
		}

		sql += " limit 0,1";

		logger.debug(sql.replaceAll("\n", " "));
		return sql;
	}

	public String sumByExample(final ParamMap<?> map) {
		final String field = (String) map.get("field");
		final Example<?> example = (Example<?>) map.get("example");
		final String sql = new SQL() {
			{
				SELECT("sum(" + field + ")");
				FROM(example.getTableName());
				exampleWhereClause(this, example);
			}
		}.toString();
		logger.debug(sql.replaceAll("\n", " "));
		return sql;
	}

	@SuppressWarnings("unchecked")
	public String updateByExample(final ParamMap<?> map) {
		final T entity = (T) map.get("record");
		final Example<T> example = (Example<T>) map.get("example");

		final String sql = new SQL() {
			{
				UPDATE(entity.getTableName());
				final Map<Field, Object> fields = getEntityFields(
						entity.getClass(), entity);
				for (final Field field : fields.keySet()) {
					final Object value = fields.get(field);
					if (value == null
							&& BaseEntity.class.isAssignableFrom(field
									.getType())) {
						SET(field.getName() + "_id=null");
					} else if (value instanceof BaseEntity) {
						SET(field.getName() + "_id=#{record." + field.getName()
								+ ".id}");
					} else {
						SET(field.getName() + "=#{record." + field.getName()
								+ "}");
					}

				}
				exampleWhereClause(this, example);
			}
		}.toString();
		logger.debug(sql.replaceAll("\n", " "));
		return sql;
	}

	@SuppressWarnings("unchecked")
	public String updateByExampleSelective(final ParamMap<?> map) {
		final T entity = (T) map.get("record");
		final Example<T> example = (Example<T>) map.get("example");
		final String sql = new SQL() {
			{
				UPDATE(entity.getTableName());
				final Map<Field, Object> fields = getEntityFields(
						entity.getClass(), entity);
				for (final Field field : fields.keySet()) {
					final Object value = fields.get(field);
					if (value != null) {
						if (value instanceof BaseEntity) {
							SET(field.getName() + "_id=#{record."
									+ field.getName() + ".id}");
						} else {
							SET(field.getName() + "=#{record."
									+ field.getName() + "}");
						}
					}

				}
				exampleWhereClause(this, example);
			}
		}.toString();
		logger.debug(sql.replaceAll("\n", " "));
		return sql;

	}

	public String updateByPrimaryKey(final T entity) {
		final String sql = new SQL() {
			{
				UPDATE(entity.getTableName());
				final Map<Field, Object> fields = getEntityFields(
						entity.getClass(), entity);
				for (final Field field : fields.keySet()) {
					final Object value = fields.get(field);
					if (value == null
							&& BaseEntity.class.isAssignableFrom(field
									.getType())) {
						SET(field.getName() + "_id=null");
					} else if (value instanceof BaseEntity) {
						SET(field.getName() + "_id=#{" + field.getName()
								+ ".id}");
					} else {
						SET(field.getName() + "=#{" + field.getName() + "}");
					}

				}
				WHERE("id=#{id}");
			}
		}.toString();
		logger.debug(sql.replaceAll("\n", " "));
		return sql;
	}

	public String updateByPrimaryKeySelective(final T entity) {
		final String sql = new SQL() {
			{
				UPDATE(entity.getTableName());
				final Map<Field, Object> fields = getEntityFields(
						entity.getClass(), entity);
				for (final Field field : fields.keySet()) {
					final Object value = fields.get(field);
					if (value != null) {
						if (value instanceof BaseEntity) {
							SET(field.getName() + "_id=#{" + field.getName()
									+ ".id}");
						} else {
							SET(field.getName() + "=#{" + field.getName() + "}");
						}
					}

				}
				WHERE("id=#{id}");
			}
		}.toString();
		logger.debug(sql.replaceAll("\n", " "));
		return sql;
	}

	private SQL exampleWhereClause(final SQL sql, final Example<?> example) {
		int orCount = 0;
		int i = 0;
		for (final Criteria criteria : example.getOredCriteria()) {
			if (orCount != 0) {
				sql.OR();
			}
			orCount++;
			if (criteria.isValid()) {
				int j = 0;
				for (final Criterion criterion : criteria.getCriteria()) {
					if (criterion.isNoValue()) {
						sql.WHERE(criterion.getCondition());
					} else if (criterion.isSingleValue()) {
						// sql.WHERE(criterion.getCondition() +
						// criterion.getValue());
						sql.WHERE(criterion.getCondition()
								+ "#{example.oredCriteria[" + i + "].criteria["
								+ j + "].value}");
					} else if (criterion.isBetweenValue()) {
						// sql.WHERE(criterion.getCondition() +
						// criterion.getValue() + " and " +
						// criterion.getSecondValue());
						sql.WHERE(criterion.getCondition()
								+ "#{example.oredCriteria[" + i + "].criteria["
								+ j + "].value}" + " and "
								+ "#{example.oredCriteria[" + i + "].criteria["
								+ j + "].secondValue}");
					} else if (criterion.isListValue()) {
						final StringBuilder valueBuilder = new StringBuilder(
								"(");
						int vCount = 0;
						for (@SuppressWarnings("unused")
						final Object v : (List<?>) criterion.getValue()) {
							if (vCount != 0) {
								valueBuilder.append(",");
							}
							vCount++;
							// valueBuilder.append(v);
							valueBuilder.append("#{example.oredCriteria[" + i
									+ "].criteria[" + j + "].value["
									+ (vCount - 1) + "]}");
						}
						valueBuilder.append(")");
						sql.WHERE(criterion.getCondition()
								+ valueBuilder.toString());
					}
					j++;
				}
			}
			i++;
		}
		return sql;
	}

	private Map<Field, Object> getEntityFields(final Class<?> clazz,
			final T entity) {
		Map<Field, Object> result = null;
		try {
			result = new HashMap<Field, Object>();
			for (final Field field : clazz.getDeclaredFields()) {

				if (!field.isAnnotationPresent(Column.class)) {
					continue;
				}

				field.setAccessible(true);
				result.put(field, field.get(entity));
			}
			final Class<?> superClass = clazz.getSuperclass();
			if (superClass != null && !superClass.equals(Object.class)) {
				result.putAll(getEntityFields(superClass, entity));
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {

		}
		return result;
	}

}
