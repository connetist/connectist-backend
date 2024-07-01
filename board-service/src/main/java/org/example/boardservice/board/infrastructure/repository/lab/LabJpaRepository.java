package org.example.boardservice.board.infrastructure.repository.lab;

import org.example.boardservice.board.domain.Lab;
import org.example.boardservice.board.infrastructure.entity.LabEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LabJpaRepository extends JpaRepository<LabEntity,String> {

    List<LabEntity> findAllBySchool(String school);
    List<LabEntity> findAllBySchoolAndMajor(String school, String major);
    @Query("SELECT l FROM LabEntity l WHERE "
            + "(:school IS NULL OR l.school = :school) AND "
            + "(:major IS NULL OR l.major = :major) AND "
            + "(:professor IS NULL OR l.professor = :professor)")
    List<Lab> findLabsByConditions(@Param("school") String school,
                                   @Param("major") String major,
                                   @Param("professor") String professor);
}
