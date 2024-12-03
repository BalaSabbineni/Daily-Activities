package com.personal.dailyActivities.controller;

import com.personal.dailyActivities.auth.entity.UserRegister;
import com.personal.dailyActivities.auth.repository.UserRegisterRepository;
import com.personal.dailyActivities.dto.HobbiesRequest;
import com.personal.dailyActivities.dto.MonthlyHobbyProgressDTO;
import com.personal.dailyActivities.entity.Hobbies;
import com.personal.dailyActivities.enums.Category;
import com.personal.dailyActivities.repositroy.HobbiesRepository;
import com.personal.dailyActivities.service.HobbiesService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/")
public class HobbiesController {
    private final UserRegisterRepository userRepository;
    private final HobbiesRepository hobbiesRepository;
    private final HobbiesService hobbiesService;

    public HobbiesController(UserRegisterRepository userRepository, HobbiesRepository hobbiesRepository, HobbiesService hobbiesService) {
        this.userRepository = userRepository;
        this.hobbiesRepository = hobbiesRepository;
        this.hobbiesService = hobbiesService;
    }

    @PostMapping("createHobbies")
    public List<Hobbies> create(Authentication authentication, @RequestBody List<HobbiesRequest> request) {
        var user = getUser(authentication);
        /*
         * Instead of saving each activity or hobby one by one in the stream, consider batching the save operation with saveAll() for better performance.
         * we can use this return activitiesRepository.saveAll(hobbiesList);
         */
        return request.stream().map(req -> {
            Hobbies hobbies = new Hobbies();
            hobbies.setCategory(req.getCategory());
            hobbies.setLevel(req.getLevel());
            hobbies.setProgress(req.getProgress());
            hobbies.setDescription(req.getDescription());
            hobbies.setStartDate(req.getStartDate());
            hobbies.setUser(user);

            return hobbiesRepository.save(hobbies);
        }).toList();
    }


    @GetMapping("hobbies/{hobbyName}/users")
    public Object getUserHobbiesByName(Authentication authentication, @PathVariable String hobbyName) {
        var user = getUser(authentication);

        var hobbies = hobbiesRepository.findHobbiesByCategory(Category.valueOf(hobbyName));

        return hobbies.stream().collect(Collectors.groupingBy(Hobbies::getCategory,
                Collectors.mapping(Hobbies::getUser, Collectors.toList())));
        // OR create new DTO to customize User details.
//        return hobbies.stream()
//                .collect(Collectors.groupingBy(
//                        h -> h.getCategory().name(),
//                        Collectors.mapping(
//                                h -> new UserWithActivitiesDTO(h.getUser().getUsername(),
//                                        activitiesRepository.findActivitiesByUserId(h.getUser().getId())),
//                                Collectors.toList()
//                        )
//                ));
    }

    @GetMapping("{userId}/hobbies/progress")
    public MonthlyHobbyProgressDTO getMonthlyHobbiesProgress(@PathVariable Long userId, @RequestParam String month) {
        UserRegister user = userRepository.findById(Math.toIntExact(userId)).orElseThrow();
        List<Hobbies> hobbies = hobbiesRepository.findHobbiesByUserId(userId);

        return hobbiesService.monthlyHobbyProgress(user.getUserName(), month, hobbies);
    }


    private UserRegister getUser(Authentication authentication) {
        var userName = authentication.getName();
        var user = userRepository.findUserByUserName(userName);
        return user;
    }
}
