package br.com.house.digital.projetointegrador.service;

import java.util.List;

public interface IService<T> {
	T create(T object);

	T findById(Long id);

	List<T> findAll();

	T update(T object);

	void delete(Long id);
}
