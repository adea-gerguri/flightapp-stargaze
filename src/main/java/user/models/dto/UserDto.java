package user.models.dto;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import user.enums.UserType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
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
