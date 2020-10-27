package br.com.house.digital.projetointegrador.model.profile;

import br.com.house.digital.projetointegrador.model.AbstractEntity;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "external_links")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ExternalLink extends AbstractEntity<Long> {

    @Column(length = 30, nullable = false)
    private String name;

    @URL
    @Column(nullable = false)
    private String url;

}
