package project.goalforge.scope;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import project.goalforge.models.User;

import java.sql.Date;

public interface UserRepository extends JpaRepository<User, Long> {
    //Method for finding user by their ID in the database using JPA query
    User findByUserID(Long userID);

    // Custom query to find user by email using JPQL
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    // Custom query to find user by resetPasswordToken using JPQL
    @Query("SELECT u FROM User u WHERE u.resetPasswordToken = ?1")
    User findByResetPasswordToken(String token);
}
