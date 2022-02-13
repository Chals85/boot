package com.chals.boot.common;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@Column(name = "create_at", nullable = false)
	protected LocalDateTime createAt;
	
	@Column(name = "update_at")
	protected LocalDateTime updateAt;
	
	@PrePersist
	public void createAt() {
		this.createAt = LocalDateTime.now();
		this.updateAt = LocalDateTime.now();
	}

}

