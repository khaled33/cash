package com.enda.cash.photo_registre.dao.repository;

import com.enda.cash.photo_registre.dao.entity.UploadFileResponseRegisterImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileResponseRegisterRepository extends JpaRepository<UploadFileResponseRegisterImage, Long> {
}
