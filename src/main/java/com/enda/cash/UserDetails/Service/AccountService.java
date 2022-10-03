package com.enda.cash.UserDetails.Service;


import com.enda.cash.UserDetails.Dao.Entity.AppUser;
import com.enda.cash.UserDetails.Dao.Entity.DemandeUpdatePaswordUser;
import com.enda.cash.UserDetails.Dao.Entity.Role;
import com.enda.cash.UserDetails.Dao.Entity.RoleName;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public interface AccountService {

    public AppUser saveUser(AppUser users);

    public AppUser UpdateUser(AppUser users, Long idUser);

    public AppUser UpdatePssword(String Passewordusers, String OldPassewordUsers, Long idUser);

    public Role saveRole(Role role);

    public void addRoleToUser(String email, RoleName rolename);

    public AppUser findUserByUserName(String username);

    public List<AppUser> getAllUser();

    public List<Role> getAllRole();

    void reinitialiserPssword(String IdUser);

    AppUser reinitialiserCodePin(Long IdUser, int codePin);

    DemandeUpdatePaswordUser DemandeUpdatePasseWored(DemandeUpdatePaswordUser updatePaswordUser);

    List<DemandeUpdatePaswordUser> ListDemandeUpdatePasseWored();

    List<AppUser> getAllByRolesName();

    public Boolean getCodePin(int CodePinRequest);

    public static String currentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        return "not found User";
    }

    public AppUser getcurrentUser();


    AppUser UpdatePsswordFerst(String passewordusers, Long idUser);

    void disableUser(String UserName);

}
