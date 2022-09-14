package com.develop.project_auth.domain.repository;

import com.develop.project_auth.domain.model.User;
import java.util.Optional;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CustomJpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

}
