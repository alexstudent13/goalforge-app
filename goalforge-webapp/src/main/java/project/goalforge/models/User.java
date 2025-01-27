package project.goalforge.models;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;

//Mark class as a JPA entity using Entity annotation
@Entity
//Specify the table in the database to which it will map to using Table annotation
@Table(name = "Users")
public class User {
    //Mark this field as the primary key with an auto-increment strategy using ID and GeneratedValue annotation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    //Specify this column as unique and non-nullable in the database
    @Column(unique = true, nullable = false)
    private String email;

    //Specify this column as non-nullable but does not have to be unique in the database
    @Column(nullable = false)
    private String password;

    //Column annotation for storing reset password token
    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    //Regular columns and is nullable by default
    private String firstName;
    private String lastName;

    //Nullable column for the timestamp in which a user registers
    @Column(nullable = true)
    private Timestamp signUpDate;

    //Regular columns and is nullable by default
    private String gender;
    private Date dateOfBirth;


    /*
    Getters and setters for the created columns such as userID, email, password, first name,
    last name, sign up date, gender, and date of birth
    */
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Timestamp getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(Timestamp signUpDate) {
        this.signUpDate = signUpDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }
}

