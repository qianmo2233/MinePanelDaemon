package com.qianmo.minepanel.Repository;

import com.qianmo.minepanel.Entity.FTPUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FTPUserRepository extends JpaRepository<FTPUser, String> {
}
