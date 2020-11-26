package br.com.house.digital.projetointegrador.annotation;

import br.com.house.digital.projetointegrador.model.enums.UserType;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    String username() default "natasha_romanov@shield.com";

    String name() default "Natasha Romanov";

    String password() default "Shield2020";

    UserType type() default UserType.APPLICANT;

    long id() default 1L;

}
