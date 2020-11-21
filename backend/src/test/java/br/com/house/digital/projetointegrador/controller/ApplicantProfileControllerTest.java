package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.annotation.WithMockCustomUser;
import br.com.house.digital.projetointegrador.controller.exception.ControllerExceptionHandler;
import br.com.house.digital.projetointegrador.dto.profile.ApplicantProfileDTO;
import br.com.house.digital.projetointegrador.dto.profile.UpdateAvatarDTO;
import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import br.com.house.digital.projetointegrador.model.profile.Skill;
import br.com.house.digital.projetointegrador.security.JWTAuthenticationEntryPoint;
import br.com.house.digital.projetointegrador.security.JWTRequestFilter;
import br.com.house.digital.projetointegrador.security.JWTUtil;
import br.com.house.digital.projetointegrador.security.WebSecurityConfig;
import br.com.house.digital.projetointegrador.service.impl.ApplicantProfileService;
import br.com.house.digital.projetointegrador.service.impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@ActiveProfiles({"test"})
@WebMvcTest(controllers = ApplicantProfileController.class)
@Import(ApplicantProfileController.class)
@WithMockCustomUser
@ContextConfiguration(classes = {
    JWTAuthenticationEntryPoint.class,
    WebSecurityConfig.class,
    JWTRequestFilter.class,
    JWTUtil.class,
    ModelMapper.class,
    ControllerExceptionHandler.class
})
class ApplicantProfileControllerTest {

    @MockBean
    UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private ApplicantProfileService service;

    @Autowired
    private MockMvc mvc;

    String urlPrefix = "/v1/api/profile/applicant";

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Should return status 204 NO CONTENT on PATCH profile request")
    void patchTest() throws Exception {
        ApplicantProfileDTO dto = ApplicantProfileDTO.builder().id(1L).desiredSalary(9999.99).build();
        when(service.patch(dto, dto.getId())).thenReturn(null);
        mvc.perform(patch(urlPrefix + "/1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isNoContent());
        verify(service, times(1)).patch(any(ApplicantProfileDTO.class), anyLong());
    }

    @Test
    @DisplayName("Should return status 400 BAD REQUEST on invalid PATCH profile request")
    void patchInvalidTest() throws Exception {
        ApplicantProfileDTO dto = ApplicantProfileDTO.builder()
            .id(1L)
            .skills(Collections.singletonList(Skill.builder().name("").knowledgeLevel(1).build()))
            .build();
        mvc.perform(patch(urlPrefix + "/1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
        verify(service, never()).patch(any(ApplicantProfileDTO.class), anyLong());
    }

    @Test
    @DisplayName("Should return status 204 NO CONTENT on PATCH avatar request")
    void patchAvatarTest() throws Exception {
        UpdateAvatarDTO dto = UpdateAvatarDTO.builder().name("Leia Skywalker").build();
        when(service.patchAvatar(any(UpdateAvatarDTO.class), anyLong())).thenReturn(null);
        mvc.perform(patch(urlPrefix + "/1/avatar")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isNoContent());
        verify(service, times(1)).patchAvatar(any(UpdateAvatarDTO.class), anyLong());
    }

    @Test
    @DisplayName("Should return status 400 BAD REQUEST on invalid PATCH avatar request")
    void patchAvatarInvalidTest() throws Exception {
        UpdateAvatarDTO dto = UpdateAvatarDTO.builder().imgSrc("not an URL").build();
        mvc.perform(patch(urlPrefix + "/1/avatar")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
        verify(service, never()).patchAvatar(any(UpdateAvatarDTO.class), anyLong());
    }

    @Test
    @DisplayName("Should return status 200 and avatar image source")
    void findAvatarById() throws Exception {
        String imgSrc = "http://placekitten.com/300/300";
        when(service.findById(anyLong())).thenReturn(ApplicantProfile.builder().imgSrc(imgSrc).build());
        mvc.perform(get(urlPrefix + "/1/avatar")
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.imgSrc", is(imgSrc)));
        verify(service, times(1)).findById(anyLong());
    }


    @Test
    void mapDTOTest() {
        ApplicantProfileController controller = new ApplicantProfileController(service);
        ApplicantProfile profile = ApplicantProfile.builder().id(1L).name("Lara Croft").build();
        ApplicantProfileDTO dto = ApplicantProfileDTO.builder().id(profile.getId()).name(profile.getName()).build();
        when(service.convertFromEntity(any(ApplicantProfile.class), any())).thenReturn(dto);
        assertThat(controller.mapDTO(profile)).isEqualTo(dto);
    }
}