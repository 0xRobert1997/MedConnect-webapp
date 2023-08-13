package code.medconnect.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUserEntity, Integer> {

    AppUserEntity findByUserName(String userName);

}
