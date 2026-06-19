package com.pranavlearn.springboot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SoftwareEngineerRepository extends JpaRepository<SoftwareEngineer, Integer> {
//    @Query(
//            value = "select * from software_engineer where id=:id", nativeQuery = true
//    )
//    SoftwareEngineer findEngineerByID(@Param("id") Integer id);
}
