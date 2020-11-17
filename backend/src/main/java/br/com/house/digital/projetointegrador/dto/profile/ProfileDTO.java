package br.com.house.digital.projetointegrador.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

    @Length(max = 100)
    private String name;

    @Length(max = 50)
    private String title;

    @URL
    private String imgSrc;

    @Length(max = 50)
    private String location;

    @Length(max = 1000)
    private String about;

    @URL
    private String linkedin;

    @URL
    private String github;

    @URL
    private String site;

}
