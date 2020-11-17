package br.com.house.digital.projetointegrador.dto.profile;

import lombok.Value;
import org.hibernate.validator.constraints.URL;

@Value
public class AvatarDTO {

    @URL String imgSrc;

}
