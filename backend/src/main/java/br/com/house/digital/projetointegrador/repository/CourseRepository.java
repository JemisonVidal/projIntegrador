package br.com.house.digital.projetointegrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.house.digital.projetointegrador.model.profile.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
