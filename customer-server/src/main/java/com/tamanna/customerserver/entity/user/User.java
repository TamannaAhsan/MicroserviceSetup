package com.tamanna.customerserver.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tamanna.customerserver.entity.geo.Geo;
import com.tamanna.customerserver.entity.groups.Group;
import com.tamanna.customerserver.entity.mailboxes.Mailbox;
import com.tamanna.customerserver.entity.roles.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String name;

    private String profilePictureKey;

    private Integer visitGeosCount;

    private Integer countriesCount;

    private Boolean showProfileProBadge;

    private Boolean isProUser;

    private Boolean activated;
    private Boolean hasInitialized;
    private Boolean hasGooglePhotosPermission;
    private Boolean hasUsedApp;
    private String facebookId;

    @OneToMany
    private Set<Geo> geos = new HashSet<>();

    @OneToMany
    private Set<Group> groups = new HashSet<>();

    @OneToMany
    private Set<Mailbox> mailboxes = new HashSet<>();

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String contactNumber;

    private String address;

    @ManyToOne
    private Role role;

    private String clientLatestSubscription;
    private String homeCityGeoId;
    private LocalDateTime createdAt;

}
