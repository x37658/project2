package com.iii.eeit124.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "breeds")
@Component
public class Breeds {

	private Integer breedId;
	private String family;
	private String breed;
	private Date createAt;
	private Date updatedAt;
	private Date deleteAt;
	private Set<Animals> animals = new HashSet<Animals>();
	
	@Id @Column(name = "BREED_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getBreedId() {
		return breedId;
	}
	public void setBreedId(Integer breedId) {
		this.breedId = breedId;
	}
	@Column(name = "FAMILY")
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	@Column(name = "BREED")
	public String getBreed() {
		return breed;
	}
	public void setBreed(String breed) {
		this.breed = breed;
	}
	@Column(name = "CREATED_AT")
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	@Column(name = "UPDATED_AT")
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Column(name = "DELETED_AT")
	public Date getDeleteAt() {
		return deleteAt;
	}
	public void setDeleteAt(Date deleteAt) {
		this.deleteAt = deleteAt;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "breeds", cascade = CascadeType.ALL)
	public Set<Animals> getAnimals() {
		return animals;
	}

	public void setAnimals(Set<Animals> animals) {
		this.animals = animals;
	}
}