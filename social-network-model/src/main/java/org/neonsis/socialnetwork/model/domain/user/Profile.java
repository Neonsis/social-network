package org.neonsis.socialnetwork.model.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.base.BaseEntityAudit;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@Table(name = "profile")
public class Profile extends BaseEntityAudit implements Serializable {

    private static final long serialVersionUID = 4644116734879167994L;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "birthday")
    private LocalDateTime birthday;

    @Column(name = "about")
    private String about;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}
