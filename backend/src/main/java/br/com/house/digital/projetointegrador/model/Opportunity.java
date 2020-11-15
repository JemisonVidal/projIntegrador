package br.com.house.digital.projetointegrador.model;

import br.com.house.digital.projetointegrador.model.profile.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "opportunities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Opportunity extends AbstractEntity<Long> {

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String location;

    @Column(nullable = false)
    private String description;
    private String benefits;
    private Double salary;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "text")
    private String text;

    @Column(nullable = false)
    private Boolean active;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "opportunity_id")
    private List<Requirement> requirements = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "company_profile_id")
    @JsonIgnore
    private Profile company;

    @ManyToMany
    private List<Profile> appliedUsers = new ArrayList<>();

    @JsonValue
    public Long getCompanyId() {
        return company.getId();
    }

    @JsonValue
    public String getCompanyName() {
        return company.getName();
    }
}
