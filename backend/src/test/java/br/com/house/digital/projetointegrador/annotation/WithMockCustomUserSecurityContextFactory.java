package br.com.house.digital.projetointegrador.annotation;

import br.com.house.digital.projetointegrador.model.User;
import br.com.house.digital.projetointegrador.model.enums.UserType;
import br.com.house.digital.projetointegrador.model.profile.ApplicantProfile;
import br.com.house.digital.projetointegrador.model.profile.CompanyProfile;
import br.com.house.digital.projetointegrador.model.profile.Profile;
import br.com.house.digital.projetointegrador.service.exceptions.DataIntegrityException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory
        implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Profile profile = generateEmptyProfile(customUser.type());
        profile.setId(customUser.id());
        User principal = User.builder()
                .email(customUser.username())
                .password(customUser.password())
                .type(customUser.type())
                .profile(profile)
                .build();
        Authentication auth =
                new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }

    private Profile generateEmptyProfile(UserType type) {
        if (type == UserType.APPLICANT) return new ApplicantProfile();
        else if (type == UserType.COMPANY) return new CompanyProfile();
        else throw new DataIntegrityException("Unexpected UserType value: " + type);
    }
}
