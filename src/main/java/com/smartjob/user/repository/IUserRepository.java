package com.smartjob.user.repository;


import com.smartjob.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends CrudRepository<User, UUID> {
    @Query(nativeQuery = true, value = "select * from user_bank where email = ?")
    Optional<User> finByEmail(String email);

}
