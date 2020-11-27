package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.annotation.WithMockCustomUser;
import br.com.house.digital.projetointegrador.controller.exception.ControllerExceptionHandler;
import br.com.house.digital.projetointegrador.dto.opportunity.NewOpportunityDTO;
import br.com.house.digital.projetointegrador.dto.opportunity.OpportunityDTO;
import br.com.house.digital.projetointegrador.dto.profile.ApplicantProfileDTO;
import br.com.house.digital.projetointegrador.model.Opportunity;
import br.com.house.digital.projetointegrador.model.Requirement;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.enums.KnowledgeLevel;
import br.com.house.digital.projetointegrador.model.enums.UserType;
import br.com.house.digital.projetointegrador.security.JWTAuthenticationEntryPoint;
import br.com.house.digital.projetointegrador.security.JWTRequestFilter;
import br.com.house.digital.projetointegrador.security.JWTUtil;
import br.com.house.digital.projetointegrador.security.WebSecurityConfig;
import br.com.house.digital.projetointegrador.service.exceptions.ObjectNotFoundException;
import br.com.house.digital.projetointegrador.service.exceptions.UserForbiddenException;
import br.com.house.digital.projetointegrador.service.impl.OpportunityService;
import br.com.house.digital.projetointegrador.service.impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@ActiveProfiles({"test"})
@WebMvcTest(controllers = OpportunityController.class)
@Import(OpportunityController.class)
@WithMockCustomUser(type = UserType.COMPANY)
@ContextConfiguration(classes = {
    JWTAuthenticationEntryPoint.class,
    WebSecurityConfig.class,
    JWTRequestFilter.class,
    JWTUtil.class,
    ModelMapper.class,
    ControllerExceptionHandler.class
})
public class OpportunityControllerTest {

    private static final String URL_PREFIX = "/v1/api/opportunity";

    @Autowired
    MockMvc mvc;

    @MockBean
    OpportunityService opportunityService;

    @MockBean
    UserDetailsServiceImpl userDetailsServiceImpl;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Should create an opportunity successfully and return status 201")
    @WithMockCustomUser(type = UserType.COMPANY)
    void createOpportunityTest() throws Exception {
        NewOpportunityDTO dto = NewOpportunityDTO.builder()
            .name("Vaga para teste")
            .location("São Paulo, SP")
            .description("Descrição da vaga")
            .requirements(Collections.singletonList(Requirement.builder()
                .name("Requerimento")
                .knowledgeLevel(KnowledgeLevel.BASIC)
                .build()))
            .build();

        Opportunity opportunity = Opportunity.builder()
            .name(dto.getName())
            .description(dto.getDescription())
            .requirements(dto.getRequirements())
            .id(1L)
            .build();

        when(opportunityService.save(any(NewOpportunityDTO.class), any(User.class))).thenReturn(opportunity);

        RequestBuilder request = post(URL_PREFIX)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto));

        MockHttpServletResponse response = mvc.perform(request)
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse();

        verify(opportunityService, times(1)).save(any(NewOpportunityDTO.class), any(User.class));
        assertThat(response.getHeader("Location")).isEqualTo("http://localhost" + URL_PREFIX + "/1");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should not create on invalid input and return status 400")
    void createInvalidOpportunityTest(String string) throws Exception {
        NewOpportunityDTO dto = NewOpportunityDTO.builder()
            .name(string)
            .description(string)
            .requirements(null)
            .build();

        RequestBuilder request = post(URL_PREFIX)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto));

        mvc.perform(request)
            .andExpect(status().isBadRequest());

        verify(opportunityService, never()).save(any(Opportunity.class));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Should not create on invalid requirement and return status 400")
    void createInvalidRequirementOpportunityTest(String name) throws Exception {
        NewOpportunityDTO dto = NewOpportunityDTO.builder()
            .name("Teste")
            .description("Teste")
            .requirements(Collections.singletonList(Requirement.builder()
                .name(name)
                .knowledgeLevel(KnowledgeLevel.BASIC)
                .build()))
            .build();

        RequestBuilder request = post(URL_PREFIX)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto));

        mvc.perform(request)
            .andExpect(status().isBadRequest());

        verify(opportunityService, never()).save(any(Opportunity.class));
    }

    @Test
    @DisplayName("Should apply user to opportunity and return status 204")
    @WithMockCustomUser
    void applyToOpportunityTest() throws Exception {
        mvc.perform(post(URL_PREFIX + "/1/apply"))
            .andExpect(status().isNoContent());
        doNothing().when(opportunityService).apply(anyLong(), any(User.class));
        verify(opportunityService, times(1)).apply(anyLong(), any(User.class));
    }

    @Test
    @DisplayName("Should not apply company to opportunity and return status 403")
    @WithMockCustomUser(type = UserType.COMPANY)
    void applyCompanyToOpportunityTest() throws Exception {
        mvc.perform(post(URL_PREFIX + "/1/apply"))
            .andExpect(status().isForbidden());
        verify(opportunityService, never()).apply(anyLong(), any(User.class));
    }

    @Test
    @DisplayName("Should find opportunity by id and return it")
    void findByIdTest() throws Exception {
        Long id = 1L;
        Opportunity opportunity = Opportunity.builder().id(id).build();
        when(opportunityService.findById(id)).thenReturn(opportunity);
        when(opportunityService.mapDTO(opportunity)).thenReturn(OpportunityDTO.builder().id(id).build());
        RequestBuilder request = get(URL_PREFIX + "/1")
            .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("id", is(1)));
    }

    @Test
    @DisplayName("Should return 404 on id not found")
    void findById404Test() throws Exception {
        when(opportunityService.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object not found with id: 1"));
        RequestBuilder request = get(URL_PREFIX + "/1")
            .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return page of opportunities")
    void findAllTest() throws Exception {
        Opportunity opportunity = Opportunity.builder().name("Teste").build();
        OpportunityDTO opportunityDTO = OpportunityDTO.builder().name(opportunity.getName()).build();
        Page<Opportunity> opportunities = new PageImpl<>(Arrays.asList(opportunity, opportunity));
        when(opportunityService.findAll(any(Pageable.class))).thenReturn(opportunities);
        when(opportunityService.mapDTO(any(Opportunity.class))).thenReturn(opportunityDTO);

        RequestBuilder request = get(URL_PREFIX + "/")
            .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.numberOfElements", is(2)));
    }

    @Test
    @DisplayName("Should return opportunities from a company")
    void findAllByCompanyIdTest() throws Exception {
        OpportunityDTO opportunityDTO = OpportunityDTO.builder().name("Teste").build();
        List<OpportunityDTO> opportunities = Arrays.asList(opportunityDTO, opportunityDTO);
        when(opportunityService.findAllByCompanyId(anyLong())).thenReturn(opportunities);

        RequestBuilder request = get(URL_PREFIX + "/company/1")
            .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("Should return list of applied users in opportunity")
    void findAppliedUsersByOpportunityIdTest() throws Exception {
        ApplicantProfileDTO dto = ApplicantProfileDTO.builder().id(1L).name("Boba Fett").build();
        when(opportunityService.findAppliedUsersByOpportunityId(anyLong())).thenReturn(Arrays.asList(dto, dto));
        mvc.perform(get(URL_PREFIX + "/1/applied").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("Should return list of applied opportunities for the profile")
    void findAppliedOpportunitiesByProfileIdTest() throws Exception {
        final OpportunityDTO dto = OpportunityDTO.builder().id(1L).name("Pod Racer").build();
        when(opportunityService.findAppliedOpportunitiesByProfileId(anyLong())).thenReturn(Arrays.asList(dto, dto));
        mvc.perform(get(URL_PREFIX + "/applied?id=1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("Should return status 204 when delete is successful")
    void deleteOpportunityByIdTest() throws Exception {
        doNothing().when(opportunityService).deleteById(anyLong());
        mvc.perform(delete(URL_PREFIX + "/1"))
            .andExpect(status().isNoContent());
        verify(opportunityService).deleteById(1L);
    }

    @Test
    @DisplayName("Should return status 204 when toggle active is successful")
    void toggleActiveTest() throws Exception {
        doNothing().when(opportunityService).toggleActive(anyLong());
        mvc.perform(patch(URL_PREFIX + "/1/active"))
            .andExpect(status().isNoContent());
        verify(opportunityService).toggleActive(1L);
    }

    @Test
    @DisplayName("Should return forbidden when user does not own the opportunity")
    void userForbiddenTest() throws Exception {
        doThrow(UserForbiddenException.class).when(opportunityService).toggleActive(anyLong());
        mvc.perform(patch(URL_PREFIX + "/1/active"))
            .andExpect(status().isForbidden());
        verify(opportunityService).toggleActive(1L);
    }

    @Test
    @DisplayName("Should patch and return status 200")
    void patchByIdTest() throws Exception {
        NewOpportunityDTO dto = NewOpportunityDTO.builder()
            .name("Vaga para teste")
            .location("São Paulo, SP")
            .description("Descrição da vaga")
            .requirements(Collections.singletonList(Requirement.builder()
                .name("Requerimento")
                .knowledgeLevel(KnowledgeLevel.BASIC)
                .build()))
            .build();

        Opportunity opportunity = Opportunity.builder()
            .name(dto.getName())
            .location(dto.getLocation())
            .description(dto.getDescription())
            .requirements(dto.getRequirements())
            .id(1L)
            .build();

        when(opportunityService.patch(anyLong(), any(NewOpportunityDTO.class))).thenReturn(opportunity);

        mvc.perform(patch(URL_PREFIX + "/1")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk());

    }
}
