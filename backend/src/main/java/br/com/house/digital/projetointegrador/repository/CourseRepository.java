package br.com.house.digital.projetointegrador.repository;

import br.com.house.digital.projetointegrador.model.profile.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
