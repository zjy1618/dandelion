package com.zjy.dandelion.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 抽象数据库实体<br>
 *
 * <pre>
 * 待重构
 * get and set
 * </pre>
 *
 * @author yueny09
 *
 */
public abstract class BaseEntity implements Serializable {
	/**
	 * 用于存放POJO的列信息
	 */
	private transient static Map<Class<? extends BaseEntity>, List<String>> columnMap = new HashMap<Class<? extends BaseEntity>, List<String>>();

	/**
	 *
	 */
	private static final long serialVersionUID = 5519791637940996854L;

	private static Field getField(final Class<?> clazz, final String fieldName)
			throws NoSuchFieldException {
		try {
			return clazz.getDeclaredField(fieldName);
		} catch (final NoSuchFieldException e) {
			final Class<?> superClass = clazz.getSuperclass();
			if (superClass == null) {
				throw e;
			} else {
				return getField(superClass, fieldName);
			}
		}
	}

	/**
	 * ID
	 */
	@Column
	private Long id;

	/**
	 * 创建日期
	 */
	@Column
	protected Date createDate;

	@Deprecated
	protected String key;

	/**
	 * 修改日期
	 */
	@Column
	protected Date modifyDate;

	public void caculationColumnList() {
		if (columnMap.containsKey(this.getClass())) {
			return;
		}

		Class<?> currentClass = this.getClass();
		final List<String> columnList = caculationColumnList(currentClass);
		currentClass = currentClass.getSuperclass();

		while (currentClass != null) {
			columnList.addAll(caculationColumnList(currentClass));
			currentClass = currentClass.getSuperclass();
		}

		columnMap.put(this.getClass(), columnList);

	}

	public Date getCreateDate() {
		return createDate;
	}

	public Long getId() {
		return id;
	}

	public String getKey() {
		return key;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * 获取POJO对应的表名 需要POJO中的属性定义@Table(name)
	 *
	 * @return
	 */
	@JsonIgnore
	public String getTableName() {
		final Table table = this.getClass().getAnnotation(Table.class);
		if (table != null) {
			return "`" + table.name() + "`";
		} else {
			return "`" + this.getClass().getSimpleName() + "`";
		}
	}

	/**
	 * 用于获取Insert的字段映射累加
	 *
	 * @return
	 */
	public String returnInsertColumnsDefine(final boolean skipNull) {
		final StringBuilder sb = new StringBuilder();

		final List<String> list = columnMap.get(this.getClass());
		int i = 0;
		for (String column : list) {
			if (column.contains("_id")) {
				column = column.replace("_id", "");
				final boolean isNull = isNull(column);
				if (skipNull && isNull) {
					continue;
				} else {
					if (i++ != 0) {
						sb.append(',');
					}

					if (isNull) {
						sb.append("null");
					} else {
						sb.append("#{").append(column + ".id").append('}');
					}
				}

			} else {
				if (skipNull && isNull(column)) {
					continue;
				}
				if (i++ != 0) {
					sb.append(',');
				}
				sb.append("#{").append(column).append('}');
			}

		}
		return sb.toString();
	}

	/**
	 * 用于获取Insert的字段累加
	 *
	 * @return
	 */
	public String returnInsertColumnsName(final boolean skipNull) {
		final StringBuilder sb = new StringBuilder();

		final List<String> list = columnMap.get(this.getClass());
		int i = 0;
		for (final String column : list) {
			boolean isNull = false;
			if (column.contains("_id")) {
				isNull = isNull(column.replace("_id", ""));
			} else {
				isNull = isNull(column);
			}

			if (skipNull && isNull) {
				continue;
			}

			if (i++ != 0) {
				sb.append(',');
			}
			sb.append(column);
		}
		return sb.toString();
	}

	/**
	 * 用于获取Update Set的字段累加
	 *
	 * @return
	 */
	public String returnUpdateSet() {
		final StringBuilder sb = new StringBuilder();

		final List<String> list = columnMap.get(this.getClass());
		int i = 0;
		for (final String column : list) {
			if (isNull(column)) {
				continue;
			}

			if (i++ != 0) {
				sb.append(',');
			}
			sb.append(column).append("=#{").append(column).append('}');
		}
		return sb.toString();
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setKey(final String key) {
		this.key = key;
	}

	public void setModifyDate(final Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	/**
	 * 用于计算类定义 需要POJO中的属性定义@Column(name)
	 */
	private List<String> caculationColumnList(final Class<?> clazz) {

		final Field[] fields = clazz.getDeclaredFields();
		final List<String> columnList = new ArrayList<String>(fields.length);

		for (final Field field : fields) {

			if (!field.isAnnotationPresent(Column.class)) {
				continue;
			}

			if (BaseEntity.class.isAssignableFrom(field.getType())) {
				columnList.add(field.getName() + "_id");
			} else {
				columnList.add(field.getName());
			}
		}

		return columnList;

	}

	private boolean isNull(final Field field) {
		try {
			field.setAccessible(true);
			return field.get(this) == null;
		} catch (final SecurityException e) {
			e.printStackTrace();
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		}

		return false;
	}

	private boolean isNull(final String fileName) {
		try {
			final Field field = getField(this.getClass(), fileName);
			return isNull(field);

		} catch (final NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
}
