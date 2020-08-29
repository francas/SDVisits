package com.example.application.backend.service;

import com.example.application.backend.entity.Specialist;
import com.example.application.backend.repository.SpecialistRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Service
public class SpecialistService {
    private static final Logger LOGGER = Logger.getLogger(SpecialistService.class.getName());
    private final SpecialistRepository specialistRepository;

    public SpecialistService(SpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
    }

    public List<Specialist> findAll() {
        return specialistRepository.findAll();
    }

    public long count() {
        return specialistRepository.count();
    }

    public void delete(Specialist specialist) {
        specialistRepository.delete(specialist);
    }

    public void save(Specialist specialist) {
        if (specialist == null) {
            LOGGER.log(Level.SEVERE,
                    "Specialist is null. Are you sure you have connected your form to the application?");
            return;
        }
        specialistRepository.save(specialist);
    }

    public String getCurrentUserFullName() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return specialistRepository.findByEmail(username).toString();
    }

   @PostConstruct
    public void populateTestData() {
        if (specialistRepository.count() == 0) {
            Random r = new Random(0);
            specialistRepository.saveAll(
                    Stream.of("Gabrielle Patel", "Brian Robinson", "Eduardo Haugen")
                            .map(name -> {
                                String[] split = name.split(" ");
                                Specialist specialist = new Specialist();
                                specialist.setFirstName(split[0]);
                                specialist.setLastName(split[1]);
                                String email = (specialist.getFirstName() + "." + specialist.getLastName() + "@nfq.com").toLowerCase();
                                specialist.setEmail(email);
                                specialist.setPassword("$2y$12$3cWf0U1KvAVRU.pY6drTqOY480kNW4RZqa5XO9w537.wK53lW1nBC");
                                return specialist;
                            }).collect(Collectors.toList()));
        }
    }
}
