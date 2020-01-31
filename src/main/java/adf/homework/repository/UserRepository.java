package adf.homework.repository;

import java.util.Optional;

import adf.homework.domain.User;

public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByUsername(String username);

}
