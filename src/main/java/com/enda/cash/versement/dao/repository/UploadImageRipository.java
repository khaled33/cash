package com.enda.cash.versement.dao.repository;


import com.enda.cash.UserDetails.Dao.Entity.AppUser;
import com.enda.cash.versement.dao.entity.UploadFileResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadImageRipository extends JpaRepository<UploadFileResponse, Long> {

    UploadFileResponse getUploadFileResponseByAppUser(AppUser appUser);
}
