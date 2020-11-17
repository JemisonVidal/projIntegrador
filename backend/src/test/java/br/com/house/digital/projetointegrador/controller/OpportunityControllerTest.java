package br.com.house.digital.projetointegrador.controller;

import br.com.house.digital.projetointegrador.annotation.WithMockCustomUser;
import br.com.house.digital.projetointegrador.dto.opportunity.NewOpportunityDTO;
import br.com.house.digital.projetointegrador.dto.opportunity.OpportunityDTO;
import br.com.house.digital.projetointegrador.model.Opportunity;
import br.com.house.digital.projetointegrador.model.Requirement;
import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.enums.KnowledgeLevel;
import br.com.house.digital.projetointegrador.model.enums.UserType;
import br.com.house.digital.projetointegrador.security.JWTAuthenticationEntryPoint;
import br.com.house.digital.projetointegrador.security.JWTRequestFilter;
import br.com.house.digital.projetointegrador.security.JWTUtil;
import br.com.house.digital.projetointegrador.security.WebSecurityConfig;
import br.com.house.digital.projetointegrador.service.impl.OpportunityService;
import br.com.house.digital.projetointegrador.service.impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test"})
@WebMvcTest(controllers = AuthenticationController.class)
@Import(OpportunityController.class)
@AutoConfigureMockMvc
@WithMockCustomUser(type = UserType.COMPANY)
@ContextConfiguration(classes = {
    JWTAuthenticationEntryPoint.class,
    WebSecurityConfig.class,
    JWTRequestFilter.class,
    JWTUtil.class,
    ModelMapper.class
})
public class OpportunityControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    OpportunityService opportunityService;

    @MockBean
    UserDetailsServiceImpl userDetailsServiceImpl;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Should create an opportunity successfully and return status 201")
    void createOpportunityTest() throws Exception {
        List<Requirement> requirements = new ArrayList<>();
        requirements.add(Requirement.builder()
            .name("Requerimento")
            .knowledgeLevel(KnowledgeLevel.BASIC)
            .build());

        NewOpportunityDTO dto = NewOpportunityDTO.builder()
            .name("Vaga para teste")
            .location("São Paulo, SP")
            .description("Descrição da vaga")
            .requirements(requirements)
            .build();

        Opportunity opportunity = Opportunity.builder()
            .name(dto.getName())
            .description(dto.getDescription())
            .requirements(dto.getRequirements())
            .id(1L)
            .build();

        when(opportunityService.convertToEntity(any(NewOpportunityDTO.class))).thenReturn(opportunity);
        when(opportunityService.save(any(Opportunity.class))).thenReturn(opportunity);

        String json = objectMapper.writeValueAsString(dto);

        RequestBuilder request = post("/v1/api/opportunity")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(json);

        MockHttpServletResponse response = mvc.perform(request)
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse();

        verify(opportunityService, times(1)).save(any(Opportunity.class));
        assertThat(response.getHeader("Location")).isEqualTo("http://localhost/v1/api/opportunity/1");
    }

    @Test
    @DisplayName("Should not create on invalid input and return status 400")
    void createInvalidOpportunityTest() throws Exception {
        NewOpportunityDTO dto = NewOpportunityDTO.builder()
            .name("")
            .description("")
            .requirements(null)
            .build();

        String json = objectMapper.writeValueAsString(dto);

        RequestBuilder request = post("/v1/api/opportunity")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(json);

        mvc.perform(request)
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse();

        verify(opportunityService, never()).save(any(Opportunity.class));
    }

    @Test
    @DisplayName("Should apply user to opportunity and return status 204")
    @WithMockCustomUser
    void applyToOpportunityTest() throws Exception {
        RequestBuilder request = post("/v1/api/opportunity/1/apply")
            .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
            .andExpect(status().isNoContent());

        verify(opportunityService, times(1)).apply(anyLong(), any(User.class));
    }

    @Test
    @DisplayName("Should apply company to opportunity and return status 403")
    @WithMockCustomUser(type = UserType.COMPANY)
    void applyCompanyToOpportunityTest() throws Exception {
        RequestBuilder request = post("/v1/api/opportunity/1/apply")
            .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
            .andExpect(status().isForbidden());

        verify(opportunityService, never()).apply(anyLong(), any(User.class));
    }

    @Test
    @DisplayName("Should find opportunity by id and return it")
    void findByIdTest() throws Exception {
        Long id = 1L;
        Opportunity opportunity = Opportunity.builder().id(id).build();
        when(opportunityService.findById(id)).thenReturn(opportunity);
        when(opportunityService.convertFromEntity(opportunity, OpportunityDTO.class)).thenReturn(new OpportunityDTO());
        RequestBuilder request = get("/v1/api/opportunity/1")
            .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return page of opportunities")
    void findAllTest() throws Exception {
        Opportunity opportunity = Opportunity.builder().name("Teste").build();
        OpportunityDTO opportunityDTO = OpportunityDTO.builder().name(opportunity.getName()).build();
        Page<Opportunity> opportunities = new PageImpl<>(Arrays.asList(opportunity, opportunity));
        when(opportunityService.findAll(any(Pageable.class))).thenReturn(opportunities);
        when(opportunityService.convertFromEntity(any(Opportunity.class), any())).thenReturn(opportunityDTO);

        RequestBuilder request = get("/v1/api/opportunity/")
            .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.numberOfElements", is(2)))
            .andReturn()
            .getResponse();
    }

    @Test
    @DisplayName("Should return opportunities from a company")
    void findAllByCompanyIdTest() throws Exception {
        OpportunityDTO opportunityDTO = OpportunityDTO.builder().name("Teste").build();
        List<OpportunityDTO> opportunities = Arrays.asList(opportunityDTO, opportunityDTO);
        when(opportunityService.findAllByCompanyId(anyLong())).thenReturn(opportunities);

        RequestBuilder request = get("/v1/api/opportunity/company/1")
            .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();
    }
}
