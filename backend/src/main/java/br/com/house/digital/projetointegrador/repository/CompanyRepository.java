package br.com.house.digital.projetointegrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.house.digital.projetointegrador.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
