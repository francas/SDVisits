package com.example.application.backend.service;

import com.example.application.backend.entity.Visit;
import com.example.application.backend.repository.SpecialistRepository;
import com.example.application.backend.repository.VisitRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class VisitService {
    private static final Logger LOGGER = Logger.getLogger(VisitService.class.getName());
    private final VisitRepository visitRepository;
    private final SpecialistRepository specialistRepository;

    public VisitService(VisitRepository visitRepository, SpecialistRepository specialistRepository) {
        this.visitRepository = visitRepository;
        this.specialistRepository = specialistRepository;
    }

    public List<Visit> findAll() {
        return visitRepository.findAll();
    }

    public List<Visit> findCurrentUserNext6Visits() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return visitRepository.find6ActiveBySpecialist(specialistRepository.findByEmail(username).getId());
    }

    public Visit findByCode(String code) { return visitRepository.findByActiveCode(code); }


    public Date getExpectedAcceptTime(Visit visit) {
        Long specialistId = visit.getSpecialist().getId();
        Integer averageVisitTimeMinutes = visitRepository.getSpecialistAverageVisitTimeMinutes(specialistId);
        if (averageVisitTimeMinutes == null) {
            averageVisitTimeMinutes = visitRepository.getGlobalAverageVisitTimeMinutes();
        }
        int earlierVisits = visitRepository.countEarlierSpecialistWaitingVisits(specialistId, visit.getCreatedDate()) - 1;
        if (earlierVisits > 0) {
            Date currentVisitAcceptedDate = visitRepository.getSpecialistCurrentVisitAcceptedDate(specialistId);
            return DateUtils.addMinutes(currentVisitAcceptedDate, averageVisitTimeMinutes * earlierVisits);
        }
        return visit.getCreatedDate();
    }

    public String generateUniqueCode() {
        String code;
        do {
            code = RandomStringUtils.randomAlphanumeric(6).toUpperCase();
        }
        while (visitRepository.findByActiveCode(code) != null);
        return code;

    }

    public long count() {
        return visitRepository.count();
    }

    public void save(Visit visit) {
        if (visit == null) {
            LOGGER.log(Level.SEVERE,
                    "Visit is null. Are you sure you have connected your form to the application?");
            return;
        }
        visitRepository.save(visit);
    }
}
