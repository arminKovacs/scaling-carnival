package com.codecool.shifterbackend.service;

import com.codecool.shifterbackend.controller.dto.ShiftAssignmentDetails;
import com.codecool.shifterbackend.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserService userService;
    private final ShiftService shiftService;

    @Bean
    public CommandLineRunner init() {
        return args -> {
            shiftService.registerNewShift("Morning", "06:00:00", "14:00:00");

            shiftService.registerNewShift("Afternoon", "14:00:00", "22:00:00");

            shiftService.registerNewShift("Evening", "22:00:00", "06:00:00");

            shiftService.registerNewShift("Holiday", "00:00:00", "24:00:00");

            userService.register("Taki bá", "taki", "István",
                    "Takács", "taki@freemail.hu", Set.of(Role.USER));

            userService.register("Gazadréti Betyár", "feri", "Ferenc",
                    "Vágási", "feri@freemail.hu", Set.of(Role.USER));

            userService.register("OG GG", "gabor", "Gábor",
                    "Gábor", "gg@freemail.hu", Set.of(Role.USER));

            userService.register("Lenke néni", "lenke", "Lenke",
                    "Takács", "lenke@freemail.hu",
                    Set.of(Role.USER, Role.SUPERVISOR));

            shiftService.assignShiftToUser(
                    5L,
                    new ShiftAssignmentDetails(
                            1L, "06:00:00", "14:00:00",
                            "2020-08-10", "2020-08-14"));
            shiftService.assignShiftToUser(
                    6L,
                    new ShiftAssignmentDetails(
                            2L, "14:00:00", "22:00:00",
                            "2020-08-10", "2020-08-14"));
            shiftService.assignShiftToUser(
                    7L,
                    new ShiftAssignmentDetails(
                            3L, "22:00:00", "06:00:00",
                            "2020-08-10", "2020-08-14"));
        };
    }
}
