package br.com.house.digital.projetointegrador.model.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Location {

    private String address;
    @Column(length = 50, nullable = false)
    private String city;
    @Column(length = 30, nullable = false)
    private String state;

}
