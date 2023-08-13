package code.medconnect.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class AppUserService {
    AppUserRepository appUserRepository;
    AppUserMapper appUserMapper;


    @Transactional
    public AppUser createUser(AppUser appUser){
        AppUserEntity appUserEntity = appUserMapper.map(appUser);
        AppUserEntity saved = appUserRepository.save(appUserEntity);
        AppUser savedAppUser = appUserMapper.map(saved);
        log.info("User saved: " + appUserEntity);
        return savedAppUser;


    }
}
