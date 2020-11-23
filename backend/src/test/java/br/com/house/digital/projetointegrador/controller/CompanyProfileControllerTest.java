package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.annotation.WithMockCustomUser;
import br.com.house.digital.projetointegrador.controller.exception.ControllerExceptionHandler;
import br.com.house.digital.projetointegrador.dto.profile.CompanyProfileDTO;
import br.com.house.digital.projetointegrador.dto.profile.UpdateAvatarDTO;
import br.com.house.digital.projetointegrador.model.enums.UserType;
import br.com.house.digital.projetointegrador.model.profile.CompanyProfile;
import br.com.house.digital.projetointegrador.security.JWTAuthenticationEntryPoint;
import br.com.house.digital.projetointegrador.security.JWTRequestFilter;
import br.com.house.digital.projetointegrador.security.JWTUtil;
import br.com.house.digital.projetointegrador.security.WebSecurityConfig;
import br.com.house.digital.projetointegrador.service.impl.CompanyProfileService;
import br.com.house.digital.projetointegrador.service.impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@ActiveProfiles({"test"})
@WebMvcTest(controllers = CompanyProfileController.class)
@Import(CompanyProfileController.class)
@WithMockCustomUser(type = UserType.COMPANY)
@ContextConfiguration(classes = {
    JWTAuthenticationEntryPoint.class,
    WebSecurityConfig.class,
    JWTRequestFilter.class,
    JWTUtil.class,
    ModelMapper.class,
    ControllerExceptionHandler.class
})
class CompanyProfileControllerTest {

    @MockBean
    UserDetailsServiceImpl userDetailsServiceImpl;
    String urlPrefix = "/v1/api/profile/company";
    ObjectMapper mapper = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    @MockBean
    private CompanyProfileService service;
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Should return status 204 NO CONTENT on PATCH profile request")
    void patchTest() throws Exception {
        CompanyProfileDTO dto = CompanyProfileDTO.builder()
            .id(1L)
            .name("USS Enterprise")
            .startDate(LocalDate.of(2020, 1, 1))
            .category("Starship")
            .build();
        CompanyProfile profile = CompanyProfile.builder()
            .id(dto.getId())
            .name(dto.getName())
            .startDate(dto.getStartDate())
            .category(dto.getCategory())
            .build();
        when(service.convertToEntity(any(CompanyProfileDTO.class))).thenReturn(profile);
        when(service.patch(profile)).thenReturn(profile);
        final String json = mapper.writeValueAsString(dto);
        mvc.perform(patch(urlPrefix + "/1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json))
            .andExpect(status().isNoContent());
        verify(service, times(1)).patch(any(CompanyProfile.class));
    }

    @Test
    @DisplayName("Should return status 400 BAD REQUEST on invalid PATCH profile request")
    void patchInvalidTest() throws Exception {
        CompanyProfileDTO dto = CompanyProfileDTO.builder().id(1L).category("").build();
        mvc.perform(patch(urlPrefix + "/1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
        verify(service, never()).patch(any(CompanyProfile.class));
    }

    @Test
    @DisplayName("Should return status 204 NO CONTENT on PATCH avatar request")
    void patchAvatarTest() throws Exception {
        UpdateAvatarDTO dto = UpdateAvatarDTO.builder().name("Death Star").build();
        when(service.patchAvatar(any(UpdateAvatarDTO.class), anyLong())).thenReturn(null);
        mvc.perform(patch(urlPrefix + "/1/avatar")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(dto)))
            .andExpect(status().isNoContent());
        verify(service, times(1)).patchAvatar(any(UpdateAvatarDTO.class), anyLong());
    }

    @Test
    @DisplayName("Should return status 400 BAD REQUEST on invalid PATCH avatar request")
    void patchAvatarInvalidTest() throws Exception {
        UpdateAvatarDTO dto = UpdateAvatarDTO.builder().imgSrc("not an URL").build();
        mvc.perform(patch(urlPrefix + "/1/avatar")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
        verify(service, never()).patchAvatar(any(UpdateAvatarDTO.class), anyLong());
    }

    @Test
    @DisplayName("Should return status 200 and avatar image source")
    void findAvatarById() throws Exception {
        String imgSrc = "http://placekitten.com/300/300";
        when(service.findById(anyLong())).thenReturn(CompanyProfile.builder().imgSrc(imgSrc).build());
        mvc.perform(get(urlPrefix + "/1/avatar")
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.imgSrc", is(imgSrc)));
        verify(service, times(1)).findById(anyLong());
    }


    @Test
    void mapDTOTest() {
        CompanyProfileController controller = new CompanyProfileController(service);
        CompanyProfile profile = CompanyProfile.builder().id(1L).name("Death Star").build();
        CompanyProfileDTO dto = CompanyProfileDTO.builder().id(profile.getId()).name(profile.getName()).build();
        when(service.convertFromEntity(any(CompanyProfile.class), any())).thenReturn(dto);
        assertThat(controller.mapDTO(profile)).isEqualTo(dto);
    }
}