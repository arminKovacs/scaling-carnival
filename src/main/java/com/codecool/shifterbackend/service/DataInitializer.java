package com.codecool.shifterbackend.service;

import com.codecool.shifterbackend.entity.Shift;
import com.codecool.shifterbackend.entity.ShifterUser;
import com.codecool.shifterbackend.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserService userService;
    private final ShiftService shiftService;

    @Bean
    public CommandLineRunner init(){
        return args -> {
            Shift morning = Shift.builder()
                    .name("Morning shift")
                    .startTime("06:00:00")
                    .endTime("14:00:00")
                    .startDate("2020-07-20")
                    .endDate("2020-07-22")
                    .build();

            Shift afternoon = Shift.builder()
                    .name("Afternoon shift")
                    .startTime("14:00:00")
                    .endTime("22:00:00")
                    .startDate("2020-07-20")
                    .endDate("2020-07-22")
                    .build();

            Shift evening = Shift.builder()
                    .name("Evening shift")
                    .startTime("22:00:00")
                    .endTime("06:00:00")
                    .startDate("2020-07-20")
                    .endDate("2020-07-22")
                    .build();

            Set<Shift> shiftList = new HashSet<>(Arrays.asList(morning, afternoon, evening));

            ShifterUser taki = userService.register("Taki bá", "taki", "István",
                                                    "Takács", "taki@freemail.hu", Set.of(Role.USER));

            ShifterUser feri = userService.register("Gazadréti Betyár", "feri", "Ferenc",
                                                    "Vágási", "feri@freemail.hu", Set.of(Role.USER));

            ShifterUser gabor = userService.register("OG GG", "gabor", "Gábor",
                                                "Gábor", "gg@freemail.hu", Set.of(Role.USER));

            shiftList.forEach(shift -> shift.setShifterUserId(taki));
            //shiftRepository.saveAll(shiftList);
        };
    }
}
