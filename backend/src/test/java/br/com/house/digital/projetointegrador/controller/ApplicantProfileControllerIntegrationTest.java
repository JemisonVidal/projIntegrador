package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.annotation.WithMockCustomUser;
import br.com.house.digital.projetointegrador.dto.profile.ApplicantProfileDTO;
import br.com.house.digital.projetointegrador.dto.profile.UpdateAvatarDTO;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.enums.UserType;
import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import br.com.house.digital.projetointegrador.model.profile.Course;
import br.com.house.digital.projetointegrador.model.profile.Skill;
import br.com.house.digital.projetointegrador.repository.ApplicantProfileRepository;
import br.com.house.digital.projetointegrador.repository.UserRepository;
import br.com.house.digital.projetointegrador.security.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
@Transactional
public class ApplicantProfileControllerIntegrationTest {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    private static final String NAME = "Ada Lovelace";
    private static final String EMAIL = "ada@lovelace.com";
    private static final String PASSWORD = "Lov3Lac3";
    private static final String URL_PREFIX = "/v1/api/profile/applicant/";
    private static final long id = 1L;
    private final JWTUtil jwtUtil = new JWTUtil();
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ApplicantProfileRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ObjectMapper mapper;
    private String token;

    @BeforeEach
    void setUp() {
        User user = userRepository.save(User.builder()
            .email(EMAIL)
            .password(passwordEncoder.encode(PASSWORD))
            .type(UserType.APPLICANT)
            .profile(ApplicantProfile.builder()
                .name(NAME)
                .skills(singletonList(Skill.builder()
                    .name("Java")
                    .knowledgeLevel(3)
                    .build()))
                .courses(singletonList(Course.builder()
                    .name("Java")
                    .institution("Java School")
                    .conclusionYear(2020)
                    .build()))
                .build())
            .build());
        token = jwtUtil.generateToken(user);
    }

    @Test
    @DisplayName("Should return status 200 and the profile with the given ID")
    void findByIdTest() throws Exception {
        mvc.perform(get(URL_PREFIX + id)
            .header(AUTHORIZATION, BEARER + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.name").value(NAME));
    }

    @Test
    @DisplayName("Should return status 200 and pages with all profiles")
    void findAllTest() throws Exception {
        mvc.perform(get(URL_PREFIX)
            .header(AUTHORIZATION, BEARER + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(1)))
            .andExpect(jsonPath("$.content[0].name").value(NAME))
            .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @DisplayName("Should return status 200 and pages with profiles containing the name")
    void findByNameTest() throws Exception {
        repository.save(ApplicantProfile.builder().name("Margaret Hamilton").build());
        mvc.perform(get(URL_PREFIX)
            .header(AUTHORIZATION, BEARER + token)
            .param("name", "ada"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(1)))
            .andExpect(jsonPath("$.content[0].name").value(NAME))
            .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @DisplayName("Should return status 200 and pages with profiles containing the skill")
    void findBySkillTest() throws Exception {
        repository.save(ApplicantProfile.builder().name("Margaret Hamilton")
            .skills(singletonList(Skill.builder().name("Javascript").knowledgeLevel(3).build()))
            .build());
        mvc.perform(get(URL_PREFIX)
            .header(AUTHORIZATION, BEARER + token)
            .accept(MediaType.APPLICATION_JSON)
            .param("skills", "java"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(2)))
            .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    @DisplayName("Should return status 200 and pages with profiles containing the name and skill")
    void findByNameAndSkillTest() throws Exception {
        repository.save(ApplicantProfile.builder().name("Margaret Hamilton")
            .skills(singletonList(Skill.builder().name("Javascript").knowledgeLevel(3).build()))
            .build());
        mvc.perform(get(URL_PREFIX)
            .header(AUTHORIZATION, BEARER + token)
            .accept(MediaType.APPLICATION_JSON)
            .param("name", "ada")
            .param("skills", "java"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(1)))
            .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @DisplayName("Should return status 200 and pages with profiles containing the course")
    void findByCourseTest() throws Exception {
        repository.save(ApplicantProfile.builder().name("Margaret Hamilton")
            .courses(singletonList(Course.builder()
                .name("Javascript")
                .institution("Javascript School")
                .conclusionYear(2020)
                .build()))
            .build());
        mvc.perform(get(URL_PREFIX)
            .header(AUTHORIZATION, BEARER + token)
            .accept(MediaType.APPLICATION_JSON)
            .param("course", "java"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(2)))
            .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    @DisplayName("Should return status 204 and update the profile")
    void updateAvatarTest() throws Exception {
        UpdateAvatarDTO dto = UpdateAvatarDTO.builder()
            .title("Condessa de Lovelace")
            .imgSrc("https://upload.wikimedia.org/wikipedia/commons/0/0f/Ada_lovelace.jpg")
            .build();

        mvc.perform(patch(URL_PREFIX + id + "/avatar")
            .header(AUTHORIZATION, BEARER + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(dto)))
            .andExpect(status().isNoContent());

        ApplicantProfile profile = repository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));

        assertThat(profile.getTitle()).isEqualTo(dto.getTitle());
        assertThat(profile.getImgSrc()).isEqualTo(dto.getImgSrc());
    }

    @Test
    @DisplayName("Should return status 403 and not update the profile")
    @WithMockCustomUser(username = EMAIL, id = 2)
    void updateAvatarForbiddenTest() throws Exception {
        UpdateAvatarDTO dto = UpdateAvatarDTO.builder()
            .title("Condessa de Lovelace")
            .imgSrc("https://upload.wikimedia.org/wikipedia/commons/0/0f/Ada_lovelace.jpg")
            .build();

        mvc.perform(patch(URL_PREFIX + id + "/avatar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(dto)))
            .andExpect(status().isForbidden());

        ApplicantProfile profile = repository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));

        assertThat(profile.getTitle()).isNotEqualTo(dto.getTitle());
        assertThat(profile.getImgSrc()).isNotEqualTo(dto.getImgSrc());
    }

    @Test
    @DisplayName("Should return status 200 and the profile avatar")
    void getAvatarTest() throws Exception {
        ApplicantProfile profile = repository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
        String imgSrc = "https://upload.wikimedia.org/wikipedia/commons/0/0f/Ada_lovelace.jpg";
        profile.setImgSrc(imgSrc);
        repository.save(profile);

        mvc.perform(get(URL_PREFIX + id + "/avatar")
            .header(AUTHORIZATION, BEARER + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.imgSrc").value(imgSrc));
    }

    @Test
    @DisplayName("Should return status 204 and update the profile")
    void updateProfileTest() throws Exception {
        ApplicantProfileDTO dto = ApplicantProfileDTO.builder()
            .id(id)
            .location("Londres, UK")
            .about(
                "Matemática e escritora inglesa, reconhecida principalmente por ter escrito o primeiro algoritmo para ser processado por uma máquina.")
            .skills(singletonList(Skill.builder().name("Matemática").knowledgeLevel(3).build()))
            .build();

        mvc.perform(patch(URL_PREFIX + id)
            .header(AUTHORIZATION, BEARER + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(dto)))
            .andExpect(status().isNoContent());

        ApplicantProfile profile = repository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));

        assertThat(profile.getLocation()).isEqualTo(dto.getLocation());
        assertThat(profile.getAbout()).isEqualTo(dto.getAbout());
        assertThat(profile.getSkills()).hasSize(1);
        assertThat(profile.getSkills().get(0).getName()).isEqualTo(dto.getSkills().get(0).getName());
    }

    @Test
    @DisplayName("Should return status 403 and not update the profile")
    @WithMockCustomUser(username = EMAIL, id = 2)
    void updateProfileForbiddenTest() throws Exception {
        ApplicantProfileDTO dto = ApplicantProfileDTO.builder()
            .id(id)
            .location("Londres, UK")
            .about(
                "Matemática e escritora inglesa, reconhecida principalmente por ter escrito o primeiro algoritmo para ser processado por uma máquina.")
            .skills(singletonList(Skill.builder().name("Matemática").knowledgeLevel(3).build()))
            .build();

        mvc.perform(patch(URL_PREFIX + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(dto)))
            .andExpect(status().isForbidden());

        ApplicantProfile profile = repository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));

        assertThat(profile.getLocation()).isNotEqualTo(dto.getLocation());
        assertThat(profile.getAbout()).isNotEqualTo(dto.getAbout());
        assertThat(profile.getSkills()).hasSize(0);
    }

    @Test
    @DisplayName("Should return status 401 when token signature is invalid")
    void invalidTokenTest() throws Exception {
        mvc.perform(get(URL_PREFIX)
            .header(AUTHORIZATION, BEARER + token.concat("invalidate-token")))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.msg").value("Invalid token."));
    }

}
