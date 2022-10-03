package com.enda.cash.versement.service;

import com.enda.cash.UserDetails.Dao.Entity.AppUser;
import com.enda.cash.UserDetails.Dao.Repository.AppUsersRepository;
import com.enda.cash.UserDetails.Service.AccountService;
import com.enda.cash.UserDetails.Service.AccountServiceImpl;
import com.enda.cash._config.error.NotFoundException;
import com.enda.cash.banque.BanquesRepository;
import com.enda.cash.image.FileStorageService;
import com.enda.cash.log.LogEvent;
import com.enda.cash.versement.dao.entity.StatusVersement;
import com.enda.cash.versement.dao.entity.UploadFileResponse;
import com.enda.cash.versement.dao.entity.Versement;
import com.enda.cash.versement.dao.repository.UploadImageRipository;
import com.enda.cash.versement.dao.repository.VersementRepository;
import com.enda.cash.versement.dto.DtoHistoriqueVersement;
import com.enda.cash.versement.dto.ResponseHistoriqueVersement;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class VersementServiceImpl implements VersementServiceI {
    private VersementRepository versementRepository;
    private FileStorageService fileStorageService;
    private UploadImageRipository uploadImageRipository;
    private AppUsersRepository appUsersRepository;
    private VersementDataProviderT24 dataProviderT24;
    private BanquesRepository banqueRepository;
    private Environment env;
    private SendSmsVersement sendSmsVersement;
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Versement addVersement(String nmrbulletin, Double montant, LocalDateTime dateRemboursement, int CodePin, Long IdBanque, MultipartFile file) {

        Versement versement = new Versement();
        AppUser appUser = getcurrentUser();

        // test code Pin User correcte ou non

        if (appUser.getCode_pin() != CodePin) {
            throw new NotFoundException("Code Pin incorrecte");
        }

        String paramUrl = "?bnkAccount=" + IdBanque
                + "&verAmount=" + montant + "&VoucherNo=" + nmrbulletin + "&UserLoginName=" + appUser.getUsername() + "&dateversement="
                + dateRemboursement;


        Thread insertVersementT24 = new Thread(() -> dataProviderT24.RestTemplate_insert_Versement(paramUrl.strip()));
        insertVersementT24.start();


/*
            sendSmsVersement.RestTemplate_SendSmsVersement("test",appUser.getNom()+ " " + appUser.getPrenom());
*/
        versement.setNmrbulletin(nmrbulletin);
        versement.setMontant(montant);
        versement.setDateVersement(dateRemboursement);
        // insert image

        versement.setImageversement(imageVersement(file));
        // insert current user
        versement.setAp(getcurrentUser());
        // generate Id Transaction
        versement.setTransactionId(UUID.randomUUID().toString());
        // inset banque
        versement.setBanque(banqueRepository.getById(IdBanque));

        applicationEventPublisher.publishEvent(new LogEvent(AccountServiceImpl.class, "Créer Versement ", getcurrentUser()));

        return versementRepository.save(versement);

    }

    @Override
    public List<Versement> getVersementByNomAgenceAndDateVersement(Long idUser, String date) {
        return versementRepository.getVersementByIdAgentAndDateVersement(idUser, date);
    }

    @Override
    public ResponseHistoriqueVersement getHistoriqueVersementsAp() {

        Long IdUserAp = getcurrentUser().getId();

        List<DtoHistoriqueVersement> dtoHistoriqueVersements = new ArrayList<>();


        Double TotalVersementsHistorique = 0D;
        for (Versement versement : versementRepository.findVersementByIdUser(IdUserAp)) {
            dtoHistoriqueVersements.add(VersementToDto(versement));

            TotalVersementsHistorique += versement.getMontant();
        }

        ResponseHistoriqueVersement responseHistoriqueVersement = new ResponseHistoriqueVersement();

        responseHistoriqueVersement.setDtoHistoriqueVersements(dtoHistoriqueVersements);
        responseHistoriqueVersement.setTotalVersements(TotalVersementsHistorique);
        return responseHistoriqueVersement;
    }

    @Override
    public List<Versement> filterVersementsAp(Long IdUserAp) {

        return versementRepository.findVersementByIdUser(IdUserAp);
    }


    @Override
    public Versement updateStatutVersement(Long idVersement, StatusVersement statusVersement) {
        Versement versement = versementRepository.getById(idVersement);
        try {
            versement.setStatutVersement(statusVersement);

        } catch (NoSuchElementException e) {
            throw new NotFoundException(String.format("No Record with the Id [%s] was found in our database", idVersement));
        }

        applicationEventPublisher.publishEvent(new LogEvent(AccountServiceImpl.class, "Update Statut Versement  ", getcurrentUser()));

        return versementRepository.save(versement);
    }


    //traitement & insert  image
    private UploadFileResponse imageVersement(MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        String fileName = fileStorageService.storeFile(file, uuid);

        String fileDownloadUri = env.getProperty("base.url-image");


        fileDownloadUri = fileDownloadUri.concat(fileName);

        UploadFileResponse uploadFileResponse = new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize(), getcurrentUser(), "L'image a été ajouté par L'AP " + AccountService.currentUserName());

        return uploadImageRipository.save(uploadFileResponse);

    }

    // recuperer User Current by context de Sécurité
    private AppUser getcurrentUser() {

        String UserName = AccountService.currentUserName();
        AppUser appUser = appUsersRepository.findByUsername(UserName).get();
        return appUser;
    }

    DtoHistoriqueVersement VersementToDto(Versement versement) {
        DtoHistoriqueVersement dtoHistoriqueVersement = new DtoHistoriqueVersement();

        dtoHistoriqueVersement.setId(versement.getId());
        dtoHistoriqueVersement.setDateVersement(versement.getFilterDateVersement().toString());
        dtoHistoriqueVersement.setMontant(versement.getMontant());
        dtoHistoriqueVersement.setStatutVersement(versement.getStatutVersement());
        dtoHistoriqueVersement.setNmrbulletin(versement.getNmrbulletin());
        dtoHistoriqueVersement.setTransactionId(versement.getTransactionId());
        return dtoHistoriqueVersement;
    }
}


