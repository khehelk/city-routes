package ru.khehelk.cityroutes.directoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khehelk.cityroutes.directoryservice.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}