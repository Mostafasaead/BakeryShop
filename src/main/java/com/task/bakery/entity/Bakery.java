package com.task.bakery.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NaturalId;

import lombok.Data;

@Data
@Entity
@Table(name = "Bakery", uniqueConstraints = { @UniqueConstraint(columnNames = "id") })
public class Bakery implements Serializable{
	@Id
	@Column(name = "id")
	@GeneratedValue
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "code", unique = true)
	@NaturalId
	private String code;
	@OneToMany(mappedBy = "bakery", cascade = CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Pack> packs = new HashSet<Pack>();

}
