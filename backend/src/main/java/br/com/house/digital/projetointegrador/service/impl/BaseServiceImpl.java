package br.com.house.digital.projetointegrador.service.impl;

import br.com.house.digital.projetointegrador.model.AbstractEntity;
import br.com.house.digital.projetointegrador.repository.BaseRepository;
import br.com.house.digital.projetointegrador.service.BaseService;
import br.com.house.digital.projetointegrador.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public abstract class BaseServiceImpl<T extends AbstractEntity<PK>, PK extends Serializable> implements BaseService<T, PK> {

    protected final BaseRepository<T, PK> repository;
    protected final ModelMapper modelMapper;
    private final Class<T> genericType;

    public BaseServiceImpl(BaseRepository<T, PK> repository, ModelMapper modelMapper, Class<T> genericType) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.genericType = genericType;
    }

    @Override
    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public T patch(T partial) {
        final T entity = findById(partial.getId());
        modelMapper.map(partial, entity);
        return save(entity);
    }

    public T patch(Object partial, T entity) {
        modelMapper.map(partial, entity);
        return save(entity);
    }

    @Override
    public T findById(PK id) {
        return repository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Object not found with id: " + id));
    }

    @Override
    public void deleteById(PK id) {
        T entity = findById(id);
        repository.delete(entity);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<T> findAllByName(String name, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public T convertToEntity(Object dto) {
        return modelMapper.map(dto, this.genericType);
    }

    @Override
    public <D> D convertFromEntity(T entity, Class<D> destinationType) {
        return modelMapper.map(entity, destinationType);
    }

    protected <E extends AbstractEntity<Long>> void replaceList(Collection<E> source, Collection<E> destination) {
        destination.clear();
        destination.addAll(source);
    }
}
