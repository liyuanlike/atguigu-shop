package com.atguigu.manager.pojo;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "productDesc")
public class ProductDesc implements Serializable {
	
	@Id// 对应product表中的id
	@Column(name = "id")
	private Long id;

	@Column(name = "productdesc")
	private String productDesc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
}
