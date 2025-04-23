package user.models.dto;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @NotNull private String username;
    @NotNull private String firstName;
    @NotNull private String lastName;
    @NotNull private String email;
    @NotNull private String password;
    @NotNull private String street;
    @NotNull private String city;
    @NotNull private String country;
    @NotNull private String phoneNumber;
    @NotNull private String passportNumber;
    @NotNull private String dateOfBirth;
    @NotNull private UserType userType;
    @NotNull private double balance;
}
