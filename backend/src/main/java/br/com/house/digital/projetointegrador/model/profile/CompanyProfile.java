package br.com.house.digital.projetointegrador.model.profile;

import br.com.house.digital.projetointegrador.model.Opportunity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "companies_profiles")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CompanyProfile extends Profile {

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 14, nullable = false)
    private String cnpj;

    @Column(length = 100)
    private String shortDescription;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private String category;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Opportunity> opportunities = new HashSet<>();

}
