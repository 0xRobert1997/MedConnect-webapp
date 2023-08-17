package code.medconnect.security;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    private Integer id;
    private String userName;
    private String email;
    private String password;
    private Set<RoleEntity> roles;


}
