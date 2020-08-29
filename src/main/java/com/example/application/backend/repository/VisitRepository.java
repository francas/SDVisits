package com.example.application.backend.repository;

import com.example.application.backend.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Integer> {

    @Query(value = "select * FROM visit" +
            " where code = ?1" +
            " AND canceled_date IS NULL" +
            " AND completed_date IS NULL",
            nativeQuery = true)
    Visit findByActiveCode(String code);

    @Query(value = "select * FROM visit" +
            " where specialist_id = ?1" +
            " AND canceled_date IS NULL" +
            " AND completed_date IS NULL" +
            " ORDER BY created_date ASC" +
            " LIMIT 6",
            nativeQuery = true)
    List<Visit> find6ActiveBySpecialist(Long specialistId);

    @Query(value = "select AVG(TIMESTAMPDIFF(MINUTE, accepted_date, completed_date)) FROM visit" +
            " where specialist_id = ?1" +
            " AND canceled_date IS NULL",
            nativeQuery = true)
    Integer getSpecialistAverageVisitTimeMinutes(Long specialistId);

    @Query(value = "select COALESCE(AVG(TIMESTAMPDIFF(MINUTE, accepted_date, completed_date)), 7) FROM visit" +
            " WHERE canceled_date IS NULL",
            nativeQuery = true)
    Integer getGlobalAverageVisitTimeMinutes();

    @Query(value = "select COUNT(1) FROM visit" +
            " where specialist_id = ?1" +
            " AND created_date <= ?2" +
            " AND canceled_date IS NULL" +
            " AND completed_date IS NULL",
            nativeQuery = true)
    int countEarlierSpecialistWaitingVisits(Long specialistId, Date created_date);

    @Query(value = "select COALESCE(accepted_date, NOW()) FROM visit" +
            " where specialist_id = ?1" +
            " AND canceled_date IS NULL" +
            " AND completed_date is NULL" +
            " ORDER BY created_date ASC" +
            " LIMIT 1",
            nativeQuery = true)
    Date getSpecialistCurrentVisitAcceptedDate(Long specialistId);

}