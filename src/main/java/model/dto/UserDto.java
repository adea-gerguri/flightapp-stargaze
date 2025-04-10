package model.dto;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.enums.UserType;


import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID id;

    @NotBlank(message="Username should not be blank")
    private String username;

    @NotBlank(message="First Name should not be blank")
    private String firstName;

    @NotBlank(message="Last Name should not be blank")
    private String lastName;

    @NotBlank(message="Email should not be blank")
    private String email;

    @NotBlank(message="Password should be filled")
    private String password;

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    @NotBlank
    private String phoneNumber;

    @NotBlank(message="Passport Number should be filled")
    private String passportNumber;

    @Min(message="User should be at least 18 years old", value = 17)
    private String dateOfBirth;

    @NotBlank
    private UserType userType;

    @NotBlank
    private Double balance;



}
