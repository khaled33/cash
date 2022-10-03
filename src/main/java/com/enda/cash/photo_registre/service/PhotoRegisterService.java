package com.enda.cash.photo_registre.service;

import com.enda.cash.UserDetails.Service.AccountService;
import com.enda.cash.UserDetails.Service.AccountServiceImpl;
import com.enda.cash._config.error.NotFoundException;
import com.enda.cash.image.FileStorageService;
import com.enda.cash.log.LogEvent;
import com.enda.cash.photo_registre.dao.entity.PhotoRegister;
import com.enda.cash.photo_registre.dao.entity.UploadFileResponseRegisterImage;
import com.enda.cash.photo_registre.dao.repository.PhotoRegisterRepository;
import com.enda.cash.photo_registre.dao.repository.UploadFileResponseRegisterRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class PhotoRegisterService {
    private FileStorageService fileStorageService;
    private Environment env;
    private UploadFileResponseRegisterRepository uploadImageRipository;

    private final AccountService accountService;

    private final PhotoRegisterRepository photoRegisterRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    /*    public PhotoRegister addPhotoRegister(String file) {

            PhotoRegister photoRegister = new PhotoRegister();
            photoRegister.setAp(accountService.getcurrentUser());
            photoRegister.setDateCreation(LocalDateTime.now());
            photoRegister.setImageRegister(imageRegister(file));
            applicationEventPublisher.publishEvent(new LogEvent(AccountServiceImpl.class, "Créer Photo Register ", accountService.getcurrentUser()));

            return photoRegisterRepository.save(photoRegister);
        }*/
    public PhotoRegister addPhotoRegister(MultipartFile file) {

        PhotoRegister photoRegister = new PhotoRegister();
        photoRegister.setAp(accountService.getcurrentUser());
        photoRegister.setDateCreation(LocalDateTime.now());
        photoRegister.setImageRegister(imageRegister(file));
        applicationEventPublisher.publishEvent(new LogEvent(AccountServiceImpl.class, "Créer Photo Register ", accountService.getcurrentUser()));

        return photoRegisterRepository.save(photoRegister);
    }

    public List<PhotoRegister> getPhotoRegisterByAp_Username(String userName) {

        if (!photoRegisterRepository.findAllByAp_Username(userName).isPresent()) {
            throw new NotFoundException("Photo Register est vide");
        }
        return photoRegisterRepository.findAllByAp_Username(userName).get();
    }

    private UploadFileResponseRegisterImage imageRegister(MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        String fileName = fileStorageService.storeFile(file, uuid);

        String fileDownloadUri = env.getProperty("base.url-image");


        fileDownloadUri = fileDownloadUri.concat(fileName);

        UploadFileResponseRegisterImage uploadFileResponse = new UploadFileResponseRegisterImage(null, fileName, fileDownloadUri, file.getContentType(), "L'image a été ajouté par L'AP " + AccountService.currentUserName(), file.getSize());

        return uploadImageRipository.save(uploadFileResponse);

    }

/*
    private UploadFileResponseRegisterImage imageRegister(String base64) {

        final String[] base64Array = base64.split(",");
        String dataUir, data;
        if (base64Array.length > 1) {
            dataUir = base64Array[0];
            data = base64Array[1];
        } else {
            //Build according to the specific file you represent
            dataUir = "data:image/jpg;base64";
            data = base64Array[0];
        }
        MultipartFile file = new Base64ToMultipartFile(data, dataUir);

        String uuid = UUID.randomUUID().toString();
        String fileName = fileStorageService.storeFile(file, uuid);

        String fileDownloadUri = env.getProperty("base.url-image");


        fileDownloadUri = fileDownloadUri.concat(fileName);

        UploadFileResponseRegisterImage uploadFileResponse = new UploadFileResponseRegisterImage(null, fileName, fileDownloadUri, file.getContentType(), "L'image a été ajouté par L'AP " + AccountService.currentUserName(), file.getSize());

        return uploadImageRipository.save(uploadFileResponse);

    }
*/
}
