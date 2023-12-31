package com.project.chamjimayo.repository;


import com.project.chamjimayo.repository.domain.entity.Restroom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestroomJpaRepository extends JpaRepository<Restroom, Long> {

  @Query("SELECT DISTINCT r FROM Restroom r LEFT JOIN FETCH r.equipments WHERE r.publicOrPaid = :publicOrPaid")
  Optional<List<Restroom>> findPublicOrPaid(@Param("publicOrPaid") String publicOrPaid);

  Optional<Restroom> findRestroomByRestroomId(long restroomId);



  boolean existsRestroomByRestroomName(String restroomName);
}
