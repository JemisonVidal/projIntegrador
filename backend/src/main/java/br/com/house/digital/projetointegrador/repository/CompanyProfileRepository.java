package br.com.house.digital.projetointegrador.repository;

import br.com.house.digital.projetointegrador.model.profile.CompanyProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyProfileRepository extends BaseRepository<CompanyProfile, Long> {

}
