package br.com.house.digital.projetointegrador.model.profile;

import br.com.house.digital.projetointegrador.model.Opportunity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies_profiles")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CompanyProfile extends Profile {

    @Column(length = 50)
    private String name;

    @Column(length = 14)
    private String cnpj;

    private LocalDate startDate;

    private String category;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Opportunity> opportunities = new ArrayList<>();

}
