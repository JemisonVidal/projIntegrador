package br.com.house.digital.projetointegrador.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAvatarDTO {

    @URL
    private String imgSrc;
    @Length(max = 100)
    private String name;
    @Length(max = 50)
    private String title;

}
