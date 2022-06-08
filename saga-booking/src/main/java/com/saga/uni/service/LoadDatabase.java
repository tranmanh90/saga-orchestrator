package com.saga.uni.service;

import com.saga.uni.entity.Room;
import com.saga.uni.repository.RoomRepository;
import com.saga.uni.vo.RoomStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class LoadDatabase {

    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());

    @Bean
    CommandLineRunner initDatabase(RoomRepository repository) {
        return args -> {
            if (repository.findAll().size() == 0) {
                log.info("Preloading " + repository.save(new Room("100", null, "1000", RoomStatus.FREE)));
                log.info("Preloading " + repository.save(new Room("200", null, "1000", RoomStatus.FREE)));
                log.info("Preloading " + repository.save(new Room("300", null, "1000", RoomStatus.FREE)));
                log.info("Preloading " + repository.save(new Room("400", null, "1000", RoomStatus.FREE)));
                log.info("Preloading " + repository.save(new Room("500", null, "2000", RoomStatus.FREE)));
            }
        };
    }
}