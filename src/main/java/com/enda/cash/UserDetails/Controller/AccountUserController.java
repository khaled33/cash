package com.enda.cash.UserDetails.Controller;

import com.enda.cash.UserDetails.Dao.Entity.AppUser;
import com.enda.cash.UserDetails.Dao.Entity.DemandeUpdatePaswordUser;
import com.enda.cash.UserDetails.Dao.Entity.Role;
import com.enda.cash.UserDetails.Dao.Entity.RoleName;
import com.enda.cash.UserDetails.Dao.Repository.AppUsersRepository;
import com.enda.cash.UserDetails.Service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;


@RestController
@Tag(name = "User Resource", description = "API permettant la gestion des utilisateurs")
public class AccountUserController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AppUsersRepository appUsersRepository;

    @Operation(summary = "Register user")
    @PostMapping("/users/register")
    public ResponseEntity<AppUser> Register(@Valid @RequestBody AppUser appUser) {

        return new ResponseEntity<>(accountService.saveUser(appUser), HttpStatus.CREATED);

    }

    @Operation(summary = "Update user")
    @PutMapping("/users/updateUser/{idUser}")
    public ResponseEntity<AppUser> UpdateUser(@Valid @RequestBody AppUser appUser, @PathVariable Long idUser) {

        return new ResponseEntity<>(accountService.UpdateUser(appUser, idUser), HttpStatus.CREATED);

    }


    @GetMapping("/users/getUserByUserName/{UserName}")
    public ResponseEntity<AppUser> Login(@PathVariable String UserName) {

        return new ResponseEntity<>(accountService.findUserByUserName(UserName), HttpStatus.OK);


    }

    @Operation(summary = "Update Password user")
    @PutMapping("/users/updatePassword/{idUser}/{oldPasseword}/{Passewordusers}")
    public ResponseEntity<AppUser> UpdatePssword(@PathVariable String Passewordusers, @PathVariable String oldPasseword, @PathVariable Long idUser) {
        return new ResponseEntity<>(accountService.UpdatePssword(Passewordusers, oldPasseword, idUser), HttpStatus.CREATED);
    }

    @Operation(summary = "Update Password user")
    @PutMapping("/users/updatePassword/{idUser}/{Passewordusers}")
    public ResponseEntity<AppUser> UpdatePsswordFerst(@PathVariable String Passewordusers, @PathVariable Long idUser) {
        return new ResponseEntity<>(accountService.UpdatePsswordFerst(Passewordusers, idUser), HttpStatus.CREATED);
    }

    @Operation(summary = "Get All Users")
    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getAllUser() {
        return new ResponseEntity<>(accountService.getAllUser(), HttpStatus.OK);
    }

    @Operation(summary = "Add Role To User")
    @PostMapping(value = "/users/{Username}/{rolename}")
    public ResponseEntity<Void> addRoleToUser(@RequestParam(name = "Username") String Username, @RequestParam(name = "rolename") RoleName rolename) {

        accountService.addRoleToUser(Username, rolename);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Reset Pssword User")
    @PutMapping(value = "/users/reinitialiserPssword/{UserName}")
    public ResponseEntity<Void> reinitialiserPssword(@PathVariable(name = "UserName") String UserName) {
        accountService.reinitialiserPssword(UserName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Reset  Pin")
    @PutMapping(value = "/users/reinitialiserCodePin/{idUser}/{pin}")
    public ResponseEntity<AppUser> reinitialiserPin(@PathVariable(name = "idUser") long idUser,
                                                    @PathVariable(name = "pin")
                                                    @Min(value = 10000, message = "Le code pin doit être 5 chiffre")
                                                    @Max(value = 99999, message = "Le code pin doit être 5 chiffre") int pin) {


        return new ResponseEntity<>(accountService.reinitialiserCodePin(idUser, pin), HttpStatus.OK);
    }

    @Operation(summary = "List Role")
    @GetMapping("/users/listRole")
    public ResponseEntity<List<Role>> getAllRol() {
        return new ResponseEntity<>(accountService.getAllRole(), HttpStatus.OK);
    }

    @Operation(summary = "List AP")
    @GetMapping("/users/ap")
    public ResponseEntity<List<AppUser>> getAllAP() {
        return new ResponseEntity<>(accountService.getAllByRolesName(), HttpStatus.OK);
    }


    @Operation(summary = "rest Pin BU ID USER")
    @GetMapping("/users/restPin/{id}")
    public ResponseEntity<Boolean> restPin(@PathVariable Long id) {
        AppUser byId = appUsersRepository.findById(id).get();
        byId.setPwd_changed(false);
        appUsersRepository.save(byId);
        return new ResponseEntity<>(true, HttpStatus.OK);

    }


    @Operation(summary = "Demande Update Passe Word AP")
    @PostMapping("/users/demandeUpdatePasseWored")
    public ResponseEntity<DemandeUpdatePaswordUser> demandeUpdatePasseWored(@RequestBody DemandeUpdatePaswordUser updatePaswordUser) {
        return new ResponseEntity<>(accountService.DemandeUpdatePasseWored(updatePaswordUser), HttpStatus.CREATED);

    }

    @Operation(summary = "List  Demandes Update Password En Attente")
    @GetMapping("/users/listDemandeUpdatePassword")
    public ResponseEntity<List<DemandeUpdatePaswordUser>> ListDemandeUpdatePasseWored() {
        return new ResponseEntity<>(accountService.ListDemandeUpdatePasseWored(), HttpStatus.OK);
    }

    @Operation(summary = "Désactivés  User")
    @PutMapping(value = "/users/disableUser/{UserName}")
    public ResponseEntity<Void> disableUser(@PathVariable String UserName) {
        accountService.disableUser(UserName);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
