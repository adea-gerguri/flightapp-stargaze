package user.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import user.enums.UserType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateUserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String street;
    private String city;
    private String country;
    private String phoneNumber;
    private String passportNumber;
    private String dateOfBirth;
    private UserType userType;
    private Double balance;
}
