package code.medconnect.business;

import code.medconnect.security.AppUserDetailsService;
import code.medconnect.security.AppUserEntity;
import code.medconnect.security.AppUserRepository;
import code.medconnect.security.RoleEntity;
import code.medconnect.util.EntityFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class AppUserDetailsServiceTest {

    @InjectMocks
    AppUserDetailsService appUserDetailsService;

    @Mock
    AppUserRepository appUserRepository;

    @Test
    void loadUserByUsernameTest() {
        //given
        AppUserEntity appUserEntity = EntityFixtures.someAppUserEntityFixture1();
        String userName = appUserEntity.getUserName();
        appUserEntity.setRoles(Set.of(
                RoleEntity.builder()
                        .id(1)
                        .role("PATIENT")
                        .build()));

        Mockito.when(appUserRepository.findByUserName(userName)).thenReturn(appUserEntity);

        //when
        UserDetails userDetails = appUserDetailsService.loadUserByUsername(userName);

        //then
        Assertions.assertThat(userDetails).isNotNull();
        Assertions.assertThat(userDetails.getUsername()).isEqualTo(userName);
        Assertions.assertThat(userDetails.getPassword()).isEqualTo(appUserEntity.getPassword());
        Assertions.assertThat(userDetails.isEnabled()).isEqualTo(appUserEntity.getActive());
    }
}
