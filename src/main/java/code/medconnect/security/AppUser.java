package code.medconnect.security;

import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.validator.constraints.Length;

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
