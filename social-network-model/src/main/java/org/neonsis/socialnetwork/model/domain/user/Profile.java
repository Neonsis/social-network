package org.neonsis.socialnetwork.model.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "profile")
public class Profile implements Serializable {

    private static final long serialVersionUID = 4644116734879167994L;

    @Id
    private Long id;

    @Column(name = "gender")
    @Enumerated(EnumType.ORDINAL)
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
    @JoinColumn(name = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}
