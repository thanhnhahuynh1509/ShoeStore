package com.dacs.adminmodules.repository;

import com.dacs.entitymodules.Admin;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AdminRepository extends PagingAndSortingRepository<Admin, Integer> {

    @Query("SELECT a FROM Admin a WHERE a.email LIKE ?1")
    Optional<Admin> findByEmail(String email);

    @Query("UPDATE Admin a SET a.enabled = ?2 WHERE a.id = ?1")
    @Modifying
    void changeStatus(Integer id, boolean status);

}
