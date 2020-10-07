package com.exercise.StreamApi.Repository;

import com.exercise.StreamApi.entity.Bitcoin;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BitcoinRepository extends CrudRepository<Bitcoin,Long> {

  @Query("select a from Bitcoin a where a.createAt = :creationDateTime")
  Optional<Bitcoin> findBitcoinByCreateAt(
      @Param("creationDateTime") Date creationDateTime);

  @Query(value = "from Bitcoin t where createAt BETWEEN :startDate AND :endDate")
  List<Bitcoin> findByCreateAtBetween(@Param("startDate")Date startDate,@Param("endDate")Date endDate);


}
