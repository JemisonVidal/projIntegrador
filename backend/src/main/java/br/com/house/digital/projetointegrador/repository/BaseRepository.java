package br.com.house.digital.projetointegrador.repository;

import br.com.house.digital.projetointegrador.model.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T extends AbstractEntity<PK>, PK extends Serializable> extends JpaRepository<T, PK> {

    Page<T> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
