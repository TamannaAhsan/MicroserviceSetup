package com.tamanna.customerserver.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tamanna.customerserver.dto.roles.RoleDTO;
import com.tamanna.customerserver.entity.geo.Geo;
import com.tamanna.customerserver.entity.groups.Group;
import com.tamanna.customerserver.entity.mailboxes.Mailbox;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String name;

    private String profilePictureKey;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private Integer visitGeosCount;

    private Integer countriesCount;

    private boolean showProfileProBadge;

    private boolean isProUser;

    private String email;

    private boolean activated;

    private boolean hasInitialized;

    private boolean hasGooglePhotosPermission;

    private boolean hasUsedApp;

    private String facebookId;

    private Set<Geo> geos;
    private Set<Group> groups;
    private Set<Mailbox> mailboxes;

    private RoleDTO role;

    private String clientLatestSubscription;

    private String homeCityGeoId;

    private LocalDateTime createdAt;

    private String contactNumber;

    private String address;

}
