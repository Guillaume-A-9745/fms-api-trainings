package fr.fms.security.service;

import fr.fms.security.dao.AppRoleRepository;
import fr.fms.security.dao.AppUserRepository;
import fr.fms.security.entity.AppRole;
import fr.fms.security.entity.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service @Transactional @Slf4j
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppRoleRepository appRoleRepository;


    @Override
    public AppUser saveUser(AppUser user) {
        String hashPw = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hashPw);
        log.info("Sauvegarde d'un nouvel utilisateur {} en base", user);
        return appUserRepository.save(user);
    }

    @Override
    public AppRole saveRole(AppRole role) {
        log.info("Sauvegarde d'un nouveau role en base");
        return appRoleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String rolename) {
        AppRole role = appRoleRepository.findByRolename(rolename);
        AppUser user = appUserRepository.findByUsername(username);
        user.getRoles().add(role);
        log.info("Association d'un role à un utilisateur");
    }

    @Override
    public AppUser findUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public ResponseEntity<List<AppUser>> listUsers() {
        return ResponseEntity.ok().body(appUserRepository.findAll());
    }
}
