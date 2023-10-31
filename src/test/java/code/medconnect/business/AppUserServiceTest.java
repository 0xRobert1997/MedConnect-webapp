package code.medconnect.business;

import code.medconnect.security.*;
import code.medconnect.util.DomainFixtures;
import code.medconnect.util.EntityFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceTest {

    @Mock
    AppUserRepository appUserRepository;

    @Mock
    AppUserMapper appUserMapper;

    @InjectMocks
    private AppUserService appUserService;

    @Test
    void createUserWorksCorrectly() {
        //given
        AppUserEntity appUserEntity = EntityFixtures.someAppUserEntityFixture1();
        AppUser appUser = DomainFixtures.someAppUser();

        Mockito.when(appUserMapper.map(appUser)).thenReturn(appUserEntity);
        Mockito.when(appUserMapper.map(appUserEntity)).thenReturn(appUser);
        Mockito.when(appUserRepository.save(appUserEntity)).thenReturn(appUserEntity);

        //when
        AppUser result = appUserService.createUser(appUser);

        //then
        Assertions.assertEquals(appUser, result);
        Mockito.verify(appUserMapper).map(appUser);
        Mockito.verify(appUserRepository).save(appUserEntity);
    }

    @Test
    void findByUserNameWorksCorrectly() {
        //given
        AppUserEntity appUserEntity = EntityFixtures.someAppUserEntityFixture1();
        String username = appUserEntity.getUserName();
        AppUser appUser = DomainFixtures.someAppUser();

        Mockito.when(appUserRepository.findByUserName(username)).thenReturn(appUserEntity);
        Mockito.when(appUserMapper.map(appUserEntity)).thenReturn(appUser);
        //when
        AppUser result = appUserService.findByUsername(username);

        //then
        Assertions.assertEquals(appUser, result);
        Mockito.verify(appUserRepository).findByUserName(username);
        Mockito.verify(appUserMapper).map(appUserEntity);

    }
}
