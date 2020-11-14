package br.com.house.digital.projetointegrador.repository;

import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantProfileRepository extends BaseRepository<ApplicantProfile, Long> {

}
