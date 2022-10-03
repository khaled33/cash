package com.enda.cash.UserDetails.Service;

import com.enda.cash.UserDetails.Dao.Entity.*;
import com.enda.cash.UserDetails.Dao.Repository.AppUsersRepository;
import com.enda.cash.UserDetails.Dao.Repository.DemandeUpdatePaswordUserRepository;
import com.enda.cash.UserDetails.Dao.Repository.RolesRepository;
import com.enda.cash._config.error.ConflictException;
import com.enda.cash._config.error.NotFoundException;
import com.enda.cash.agence.AgenceRepository;
import com.enda.cash.log.LogEvent;
import com.enda.cash.remboursement.service.smsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@Scope("prototype")
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AppUsersRepository appUsersRepository;
    @Autowired
    @Qualifier("roleApp")
    private RolesRepository rolesRepository;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private AgenceRepository agenceRepository;
    @Autowired
    private DemandeUpdatePaswordUserRepository demandeUpdatePaswordUserRepository;

    @Autowired
    private smsSender smsSender;

    @Override
    public AppUser saveUser(AppUser users) {

        if (appUsersRepository.findByUsername(users.getUsername()).isPresent()) {
            throw new ConflictException("User-Name Existe " + users.getUsername());
        } else if (appUsersRepository.findByEmail(users.getEmail()).isPresent()) {
            throw new ConflictException("E-mail Existe " + users.getEmail());
        }

        String userName = generateUserName(users.getNom(), users.getPrenom(), users.getRoles().get(0).getRoleName());


        String hashPw = bCryptPasswordEncoder.encode("12345678");
        users.setPassword(hashPw);
        users.setCreated(new Date());
        users.setUsername(userName);
        users.setEnable(true);

//        users.setAgence(agenceRepository.getById(idAgence));
        users.setPwd_changed(false);
        users.setPin_changed(false);
        users.setCode_pin(12345);
        AppUser save = appUsersRepository.save(users);

        applicationEventPublisher.publishEvent(new LogEvent(AccountServiceImpl.class, "Register User " + users.getUsername(), getcurrentUser()));

        return save;
    }

    @Override
    public AppUser UpdateUser(AppUser users, Long idUser) {
        AppUser appUser = appUsersRepository.getById(idUser);
        users.setId(idUser); // not Updated attribute
        users.setUsername(appUser.getUsername()); // not Updated attribute
        users.setEmail(appUser.getEmail()); // not Updated attribute
        users.setRoles(appUser.getRoles()); // not Updated attribute
        users.setCreated(appUser.getCreated()); // not Updated attribute
        users.setPassword(appUser.getPassword()); // not Updated attribute
        users.setModified(new Date()); // not Updated attribute

/*
        if (appUser.getUsername().equals(appUsersRepository.findByUsername(users.getUsername()).get())){


        }*/
    /*    if (appUsersRepository.findByUsername(users.getUsername()).isPresent()) {
         throw new ConflictException("User-Name Existe " + users.getUsername());
        } else if (appUsersRepository.findByEmail(users.getEmail()).isPresent()) {
            throw new ConflictException("E-mail Existe " + users.getEmail());

        }*/
        applicationEventPublisher.publishEvent(new LogEvent(AccountServiceImpl.class, "Update User " + users.getUsername(), getcurrentUser()));
        return appUsersRepository.save(users);
    }

    @Override
    public AppUser UpdatePssword(String PassewordUsers, String OldPassewordUsers, Long idUser) {
        AppUser appUser = appUsersRepository.getById(idUser);

        boolean ValidetPassword = bCryptPasswordEncoder.matches(OldPassewordUsers, appUser.getPassword());

        if (!ValidetPassword) {
            throw new ConflictException("Mot de Passe Incorrecte ");
        }
        String hashPw = bCryptPasswordEncoder.encode(PassewordUsers);

        appUser.setModified(new Date());
        appUser.setPwd_changed(true);
        appUser.setPassword(hashPw);
        applicationEventPublisher.publishEvent(new LogEvent(AccountServiceImpl.class, "Update Pssword User " + appUser.getUsername(), getcurrentUser()));
        return appUsersRepository.save(appUser);
    }

    @Override
    public Role saveRole(Role role) {
        return rolesRepository.save(role);
    }

    @Override
    public void addRoleToUser(String Username, RoleName rolename) {
        try {
            Role role = rolesRepository.findByRoleName(rolename).get();
            AppUser appUser = findUserByUserName(Username);
            appUser.getRoles().add(role);
            appUsersRepository.save(appUser);
            applicationEventPublisher.publishEvent(new LogEvent(AccountServiceImpl.class, "add Role To User " + appUser.getUsername(), getcurrentUser()));

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No Record with the role [%s] was found in our database", rolename));
        }

    }

    @Override
    public AppUser findUserByUserName(String UserName) {
        try {
            return appUsersRepository.findByUsername(UserName).get();
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No Record with the  [%s] was found in our database", UserName));
        }
    }

    @Override
    public List<AppUser> getAllUser() {
        return appUsersRepository.findAll();
    }

    @Override
    public List<Role> getAllRole() {
        return rolesRepository.findAll();
    }

    @Override
    public void reinitialiserPssword(String UserName) {

        AppUser appUser = findUserByUserName(UserName);

        DemandeUpdatePaswordUser updatePaswordUser = demandeUpdatePaswordUserRepository.findByUserNameAndStatusDemende(appUser.getUsername(),StatusDemende.EN_ATTENTE.name()).get();
        updatePaswordUser.setStatusDemende(StatusDemende.TRAITER);
        demandeUpdatePaswordUserRepository.save(updatePaswordUser);

        String hashPw = bCryptPasswordEncoder.encode("12345678");
        appUser.setModified(new Date());
        appUser.setPassword(hashPw);
        appUser.setPwd_changed(false);


        applicationEventPublisher.publishEvent(new LogEvent(AccountServiceImpl.class, "reinitialiser Pssword by User " + appUser.getUsername(), getcurrentUser()));

        appUsersRepository.save(appUser);

        String msg = "Votre Mot de Passe a été Réinitialisé avec Succès" +
                "\n Mot de Passe Temporaire: " + "12345678";

        smsSender.SendingSMSDMD(appUser.getTel().toString(), msg);
    }

    @Override
    public AppUser reinitialiserCodePin(Long IdUser, int codePin) {
        try {

            AppUser appUser = appUsersRepository.getById(IdUser);
            appUser.setModified(new Date());
            appUser.setCode_pin(codePin);
            appUser.setPin_changed(true);
            applicationEventPublisher.publishEvent(new LogEvent(AccountServiceImpl.class, "modifier  Pin User " + appUser.getUsername(), getcurrentUser()));

            return appUsersRepository.save(appUser);

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No Record with the Id [%s] was found in our database", IdUser));
        }


    }

    @Override
    public DemandeUpdatePaswordUser DemandeUpdatePasseWored(DemandeUpdatePaswordUser updatePaswordUser) {
        String userName = updatePaswordUser.getUserName();
        this.findUserByUserName(updatePaswordUser.getUserName());

        if (demandeUpdatePaswordUserRepository.findByUserNameAndStatusDemende(userName,StatusDemende.EN_ATTENTE.name()).isPresent()){
            updatePaswordUser.setDateDomande(LocalDate.now());
            updatePaswordUser.setStatusDemende(StatusDemende.EN_ATTENTE);
            return  updatePaswordUser ;

        }

        updatePaswordUser.setDateDomande(LocalDate.now());
        updatePaswordUser.setStatusDemende(StatusDemende.EN_ATTENTE);

        return demandeUpdatePaswordUserRepository.save(updatePaswordUser);
    }

    @Override
    public List<DemandeUpdatePaswordUser> ListDemandeUpdatePasseWored() {
        return demandeUpdatePaswordUserRepository.findAByStatusDemende(StatusDemende.EN_ATTENTE.name());
    }

    @Override
    public List<AppUser> getAllByRolesName() {
        try {
            return appUsersRepository.getAllByRoles_RoleName(RoleName.AP).get();
        } catch (NoSuchElementException e) {
            throw new NotFoundException(String.format("No Record with the Id [%s] was found in our database", RoleName.AP));
        }

    }

    @Override
    public AppUser getcurrentUser() {

        String UserName = AccountService.currentUserName();
        AppUser appUser = appUsersRepository.findByUsername(UserName).get();
        return appUser;
    }

    @Override
    public AppUser UpdatePsswordFerst(String passewordusers, Long idUser) {
        AppUser appUser = appUsersRepository.getById(idUser);


        String hashPw = bCryptPasswordEncoder.encode(passewordusers);

        appUser.setModified(new Date());
        appUser.setPwd_changed(true);
        appUser.setPassword(hashPw);
        applicationEventPublisher.publishEvent(new LogEvent(AccountServiceImpl.class, "Ferst Update Pssword User " + appUser.getUsername(), getcurrentUser()));
        return appUsersRepository.save(appUser);
    }

    @Override
    public void disableUser(String UserName) {
        AppUser appUser = findUserByUserName(UserName);
        appUser.setEnable(false);
        appUsersRepository.save(appUser);
    }

    @Override
    public Boolean getCodePin(int CodePinRequest) {
        AppUser appUser = getcurrentUser();
        if (appUser.getCode_pin() == CodePinRequest)
            return true;
        else
            return false;
    }

    private String generateUserName(String Nom, String Prenom, RoleName roleName) {
        return switch (roleName) {
            case AP -> (roleName.name() + Nom.concat(".").concat(Prenom.substring(0, 3))).toUpperCase();
            case METIER, ADMIN -> (Nom.concat(".").concat(Prenom.substring(0, 3))).toUpperCase();
            default -> throw new NotFoundException("Unexpected value: " + roleName);
        };
    }
}
