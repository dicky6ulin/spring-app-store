package com.enigma.enigmaboot.repository;

import com.enigma.enigmaboot.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>, JpaSpecificationExecutor {
    //Native Query
//    @Query("SELECT * from mst_customer c WHERE c.status = ?1", nativeQuery = true)

    //Jpa Query
    @Query("SELECT c FROM Customer c WHERE c.status = ?1")
    public List<Customer> findByStatus (String status);

    @Modifying
    @Transactional
    @Query("UPDATE Customer SET status = 'Pekerja Lepas' WHERE id = :id")
    void updateStatus (String id);

    //Security
//    Optional<Customer> findCustomerByUserName(String userName);
//    Boolean existsCustomerByUserName (String userName);
    Optional<Customer> findByUserName(String username);
    Boolean existsByUserName(String username);
}