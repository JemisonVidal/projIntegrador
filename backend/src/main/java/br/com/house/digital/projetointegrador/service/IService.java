package br.com.house.digital.projetointegrador.service;

import java.util.List;

public interface IService<T> {
	public T create(T object);

	public T findById(Integer id);

	public List<T> findAll();

	public T update(T object);

	public void delete(Integer id);
}
