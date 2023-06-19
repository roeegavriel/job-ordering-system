package com.roee.joborderingsystem.repositories;

import com.roee.joborderingsystem.entities.Job;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends CrudRepository<Job, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM job WHERE id > :id ORDER BY id")
    List<Job> findPage(@Param("id") Long idCursor, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM job WHERE customer_id = :customer_id AND id > :id ORDER BY id")
    List<Job> findPageByCustomerId(@Param("customer_id") Long customerId, @Param("id") Long idCursor, Pageable pageable);
}
