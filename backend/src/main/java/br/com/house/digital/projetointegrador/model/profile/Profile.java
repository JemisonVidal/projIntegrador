package br.com.house.digital.projetointegrador.model.profile;

import br.com.house.digital.projetointegrador.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "profiles")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Profile extends AbstractEntity<Long> {

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "address", column = @Column(name = "location_address")),
        @AttributeOverride(name = "city", column = @Column(name = "location_city", nullable = false)),
        @AttributeOverride(name = "state", column = @Column(name = "location_state", nullable = false))
    })
    private Location location;

    @Version
    private Integer version;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private Set<ExternalLink> links = new HashSet<>();

}
