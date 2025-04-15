package user.models.dto;

import jakarta.validation.constraints.NotBlank;
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

    private String dateOfBirth;


    private UserType userType;

    @NotBlank
    private Double balance;

    public boolean isValid(){
        return username!=null && firstName!=null && lastName !=null && email!=null && password!=null && city!=null && country!=null && phoneNumber!=null && passportNumber!=null && dateOfBirth!=null && userType != null;
    }
}
