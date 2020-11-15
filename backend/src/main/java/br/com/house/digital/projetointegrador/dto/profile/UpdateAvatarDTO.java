package br.com.house.digital.projetointegrador.dto.profile;

import lombok.Value;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Value
public class UpdateAvatarDTO {

    @URL String imgSrc;
    @Length(max = 100) String name;
    @Length(max = 50) String title;

}
