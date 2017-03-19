package com.zjy.dandelion.entity.example;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Table;

import com.zjy.dandelion.entity.BaseEntity;


/**
 * Created by chars on 2015 下午3:59:08.
 *
 * Copyright © mizhuanglicai
 */
public abstract class Example<T extends BaseEntity> {

	public static class Criteria extends GeneratedCriteria {
		protected Criteria() {
			super();
		}
	}

	public static class Criterion {
		private boolean betweenValue;
		private String condition;
		private boolean listValue;
		private boolean noValue;
		private Object secondValue;
		private boolean singleValue;
		private String typeHandler;
		private Object value;

		protected Criterion(final String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(final String condition, final Object value) {
			this(condition, value, null);
		}

		protected Criterion(final String condition, final Object value,
				final Object secondValue) {
			this(condition, value, secondValue, null);
		}

		protected Criterion(final String condition, final Object value,
				final Object secondValue, final String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(final String condition, final Object value,
				final String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		public String getCondition() {
			return condition;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		public Object getValue() {
			return value;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}
	}

	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public Criteria addCriterion(final String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
			return (Criteria) this;
		}

		public Criteria addCriterion(final String condition, final Object value) {
			if (value == null) {
				throw new RuntimeException("Value for " + condition
						+ " cannot be null");
			}
			// if(value instanceof String){
			// value="'"+ (String)value +"'";
			// }
			criteria.add(new Criterion(condition, value));
			return (Criteria) this;
		}

		public Criteria addCriterion(final String condition,
				final Object value1, final Object value2) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + condition
						+ " cannot be null");
			}

			// if(value1 instanceof String){
			// value1="'"+ (String)value1 +"'";
			// }
			//
			// if(value2 instanceof String){
			// value2="'"+ (String)value2 +"'";
			// }

			criteria.add(new Criterion(condition, value1, value2));
			return (Criteria) this;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

	}

	private Class<T> entityClass;

	protected boolean distinct;

	protected String orderByClause;

	protected List<Criteria> oredCriteria;

	protected String tableName;

	public Example() {
		oredCriteria = new ArrayList<Criteria>();
	}

	public Criteria appendCriterion(final String condition) {
		return getCriteria().addCriterion(condition);
	}

	public Criteria appendCriterion(final String condition, final Object value) {
		return getCriteria().addCriterion(condition, value);
	}

	public Criteria appendCriterion(final String condition,
			final Object value1, final Object value2) {
		return getCriteria().addCriterion(condition, value1, value2);
	}

	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	public Criteria createCriteria() {
		final Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	@SuppressWarnings("unchecked")
	public String getTableName() {
		if (entityClass == null) {
			entityClass = (Class<T>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		}

		final Table table = entityClass.getAnnotation(Table.class);
		if (table != null) {
			return "`" + table.name() + "`";
		} else {
			return "`" + entityClass.getSimpleName() + "`";
		}
	}

	public boolean isDistinct() {
		return distinct;
	}

	public Criteria or() {
		final Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	public void or(final Criteria criteria) {
		oredCriteria.add(criteria);
	}

	public void setDistinct(final boolean distinct) {
		this.distinct = distinct;
	}

	public void setOrderByClause(final String orderByClause) {
		this.orderByClause = orderByClause;
	}

	protected Criteria createCriteriaInternal() {
		final Criteria criteria = new Criteria();
		return criteria;
	}

	protected Criteria getCriteria() {

		return getCriteria(0);
	}

	protected Criteria getCriteria(final int index) {
		if (getOredCriteria().size() > index) {
			return getOredCriteria().get(index);
		} else {
			final Criteria criteria = new Criteria();
			getOredCriteria().add(criteria);
			return criteria;
		}

	}

}
