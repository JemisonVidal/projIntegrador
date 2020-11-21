package br.com.house.digital.projetointegrador.service.impl;

import br.com.house.digital.projetointegrador.dto.profile.ApplicantProfileDTO;
import br.com.house.digital.projetointegrador.dto.profile.UpdateAvatarDTO;
import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import br.com.house.digital.projetointegrador.model.profile.Course;
import br.com.house.digital.projetointegrador.model.profile.Skill;
import br.com.house.digital.projetointegrador.model.profile.WorkExperience;
import br.com.house.digital.projetointegrador.repository.ApplicantProfileRepository;
import br.com.house.digital.projetointegrador.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test"})
class ApplicantProfileServiceTest {

    @MockBean
    ApplicantProfileRepository repository;

    ApplicantProfileService service;

    ApplicantProfile profile;

    @BeforeEach
    void setUp() {
        service = new ApplicantProfileService(repository, new ModelMapper());
        profile = ApplicantProfile.builder()
            .id(1L)
            .name("Luke Skywalker")
            .skills(new ArrayList<>())
            .courses(new ArrayList<>())
            .workExperiences(new ArrayList<>())
            .build();
    }

    @Test
    @DisplayName("Should modify the fields included in the DTO")
    void patchAvatarTest() {
        given(repository.findById(anyLong())).willReturn(Optional.of(profile));
        given(repository.save(any(ApplicantProfile.class))).will(i -> i.getArguments()[0]);
        UpdateAvatarDTO dto = UpdateAvatarDTO.builder()
            .imgSrc("http://placekitten.com/300/300")
            .name("Anakin Skywalker")
            .title("Sith Lord")
            .build();
        ApplicantProfile patched = service.patchAvatar(dto, this.profile.getId());
        assertThat(patched.getImgSrc()).isEqualTo(dto.getImgSrc());
        assertThat(patched.getName()).isEqualTo(dto.getName());
        assertThat(patched.getTitle()).isEqualTo(dto.getTitle());
        verify(repository, times(1)).save(profile);
        verify(repository, times(1)).findById(profile.getId());
    }

    @Test
    @DisplayName("Should not modify the fields that are null in the DTO")
    void patchAvatarNullFieldTest() {
        profile.setImgSrc("http://placekitten.com/300/300");
        profile.setTitle("Jedi Master");
        given(repository.findById(anyLong())).willReturn(Optional.of(profile));
        given(repository.save(any(ApplicantProfile.class))).will(i -> i.getArguments()[0]);
        UpdateAvatarDTO dto = UpdateAvatarDTO.builder()
            .name("Anakin Skywalker")
            .build();
        ApplicantProfile patched = service.patchAvatar(dto, this.profile.getId());
        assertThat(patched.getImgSrc()).isNotNull();
        assertThat(patched.getName()).isEqualTo(dto.getName());
        assertThat(patched.getTitle()).isNotNull();
        verify(repository, times(1)).save(profile);
        verify(repository, times(1)).findById(profile.getId());
    }

    @Test
    @DisplayName("Should call repository to persist entity")
    void saveTest() {
        given(repository.save(any(ApplicantProfile.class))).willReturn(profile);
        assertThat(service.save(profile)).isEqualTo(profile);
        verify(repository, times(1)).save(profile);
    }

    @Test
    @DisplayName("Should return entity when id exists")
    void findByIdTest() {
        given(repository.findById(anyLong())).willReturn(Optional.of(profile));
        assertThat(service.findById(profile.getId())).isEqualTo(profile);
        verify(repository, times(1)).findById(profile.getId());
    }

    @Test
    @DisplayName("Should throw exception when id does not exists")
    void idNotFoundTest() {
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        assertThat(catchThrowable(() -> service.findById(profile.getId())))
            .isInstanceOf(ObjectNotFoundException.class)
            .hasMessageContaining("Object not found with id");
        verify(repository, times(1)).findById(profile.getId());
    }

    @Test
    @DisplayName("Should return list of all profiles")
    void findAllListTest() {
        given(repository.findAll()).willReturn(Collections.singletonList(profile));
        List<ApplicantProfile> list = service.findAll();
        assertThat(list).isNotNull();
        assertThat(list).hasSizeGreaterThan(0);
        assertThat(list).contains(profile);
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return page of all profiles")
    void findAllPageTest() {
        given(repository.findAll(any(Pageable.class))).willReturn(new PageImpl<>(Arrays.asList(profile, profile)));
        Page<ApplicantProfile> page = service.findAll(PageRequest.of(0, 10));
        assertThat(page).isNotNull();
        assertThat(page.getTotalElements()).isEqualTo(2);
        assertThat(page.getContent()).contains(profile);
        verify(repository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Should convert DTO into an entity")
    void convertToEntityTest() {
        ApplicantProfileDTO dto = ApplicantProfileDTO.builder()
            .id(profile.getId())
            .name(profile.getName())
            .build();
        ApplicantProfile entity = service.convertToEntity(dto);
        assertThat(entity).isEqualTo(profile);
    }

    @Test
    @DisplayName("Should convert entity into a DTO")
    void convertFromEntityTest() {
        ApplicantProfileDTO dto = service.convertFromEntity(profile, ApplicantProfileDTO.class);
        assertThat(dto.getId()).isEqualTo(profile.getId());
        assertThat(dto.getName()).isEqualTo(profile.getName());
    }

    @Test
    @DisplayName("Should replace all elements in a list without replacing the list instance")
    void replaceListTest() {
        Skill skill = Skill.builder().name("Light Saber").knowledgeLevel(2).build();
        List<Skill> skills = profile.getSkills();
        service.replaceList(Arrays.asList(skill, skill), skills);
        assertThat(profile.getSkills() == skills).isTrue();
        assertThat(skills).hasSize(2);
        assertThat(skills).contains(skill);
    }

    @Test
    @DisplayName("Should modify the profile with fields included in the DTO")
    void patchTest() {
        given(repository.findById(anyLong())).willReturn(Optional.of(profile));
        given(repository.save(any(ApplicantProfile.class))).will(i -> i.getArguments()[0]);
        ApplicantProfileDTO dto = ApplicantProfileDTO.builder()
            .id(profile.getId())
            .desiredSalary(1234.56)
            .about("Luke Skywalker is a fictional character and the main protagonist of the original film trilogy of the Star Wars franchise created by George Lucas.")
            .location("Tatooine")
            .locationWanted("Mos Eisley")
            .build();
        ApplicantProfile patched = service.patch(dto, profile.getId());
        assertThat(patched.getDesiredSalary()).isEqualTo(dto.getDesiredSalary());
        assertThat(patched.getAbout()).isEqualTo(dto.getAbout());
        assertThat(patched.getLocation()).isEqualTo(dto.getLocation());
        assertThat(patched.getLocationWanted()).isEqualTo(dto.getLocationWanted());
        verify(repository, times(1)).save(any(ApplicantProfile.class));
    }

    @Test
    @DisplayName("Should modify the profile and replace with lists included in the DTO")
    void patchListsTest() {
        this.service = Mockito.spy(new ApplicantProfileService(repository, new ModelMapper()));
        given(repository.findById(anyLong())).willReturn(Optional.of(profile));
        given(repository.save(any(ApplicantProfile.class))).will(i -> i.getArguments()[0]);
        ApplicantProfileDTO dto = ApplicantProfileDTO.builder()
            .id(profile.getId())
            .skills(Collections.singletonList(new Skill()))
            .courses(Collections.singletonList(new Course()))
            .workExperiences(Collections.singletonList(new WorkExperience()))
            .build();
        ApplicantProfile patched = service.patch(dto, profile.getId());
        assertThat(patched.getSkills()).hasSize(1);
        assertThat(patched.getCourses()).hasSize(1);
        assertThat(patched.getWorkExperiences()).hasSize(1);
        verify(repository, times(1)).save(any(ApplicantProfile.class));
        verify(service, times(3)).replaceList(anyCollection(), anyCollection());
    }
}