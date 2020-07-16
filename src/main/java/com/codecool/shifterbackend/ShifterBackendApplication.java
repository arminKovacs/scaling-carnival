package com.codecool.shifterbackend;

import com.codecool.shifterbackend.entity.Shift;
import com.codecool.shifterbackend.entity.ShifterUser;
import com.codecool.shifterbackend.repository.ShiftRepository;
import com.codecool.shifterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ShifterBackendApplication {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    public static void main(String[] args) {
        SpringApplication.run(ShifterBackendApplication.class, args);
    }

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

            ShifterUser taki = ShifterUser.builder()
                    .username("Taki Bá")
                    .email("taki@freemail.hu")
                    .shifts(shiftList)
                    .build();

            ShifterUser feri = ShifterUser.builder()
                    .username("Vágási Feri")
                    .email("feri@freemail.hu")
                    .build();

            ShifterUser gabor = ShifterUser.builder()
                    .username("Gábor Gábor")
                    .email("gg@freemail.hu")
                    .build();

            userRepository.saveAll(Arrays.asList(taki, feri, gabor));
            shiftList.forEach(shift -> shift.setShifterUserId(taki));
            shiftRepository.saveAll(shiftList);
        };
    }
}
