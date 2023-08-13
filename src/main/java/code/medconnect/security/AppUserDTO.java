package code.medconnect.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDTO {

    private Integer id;
    private String userName;
    private String email;
    private String password;
    private Set<RoleEntity> roles;
}
