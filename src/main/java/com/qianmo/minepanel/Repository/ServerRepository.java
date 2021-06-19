package com.qianmo.minepanel.Repository;

import com.qianmo.minepanel.Entity.ServerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<ServerEntity, Integer> {
}
