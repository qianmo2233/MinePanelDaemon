package com.qianmo.minepanel.Repository;

import com.qianmo.minepanel.Entity.FTPUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FTPUserRepository extends JpaRepository<FTPUserEntity, String> {
}
