package com.ishidai.ischedule.business.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @note 行政区实体
 * @author wangmeng
 * @date 2015年10月22日 下午6:38:50
 */
public class SystemRegion implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键自增
	 */
	private Long id;

	/**
	 * 行政编码
	 */
	private String code;

	/**
	 * 行政名称
	 */
	private String name;

	/**
	 * 层级
	 */
	private Integer layer;

	/**
	 * 父行政编码
	 */
	private String parentCode;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}