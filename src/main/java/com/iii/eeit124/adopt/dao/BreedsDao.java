package com.iii.eeit124.adopt.dao;

import java.util.List;
import com.iii.eeit124.entity.Breeds;

public interface BreedsDao {

	public Breeds create(Breeds entity);
	
	public Breeds read(Integer breedsId);
	
	public List<Breeds> readAll();
	
	public Breeds update(Breeds entity);
	
	public boolean delete(Integer breedsId);
	
	public List<String> readAllFamilies();
	
	public List<Breeds> readAllBreeds(String family);
	
	public List<Breeds> readDogsBreeds();
}