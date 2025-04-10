package model.mapper;

import jakarta.enterprise.context.RequestScoped;
import model.dto.UserDto;
import model.view.User;

@RequestScoped
public class UserMapper {
    public User toUser(UserDto userDto){
        if(userDto!=null){
            User user = new User();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setBalance(userDto.getBalance());
            user.setCity(userDto.getCity());
            user.setCountry(userDto.getCountry());
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setDateOfBirth(userDto.getDateOfBirth());
            user.setPassportNumber(userDto.getPassportNumber());
            return user;
        }
        return null;
    }
    public UserDto toUserDto(User user){
        if(user!=null){
            UserDto userDto = new UserDto();
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setUsername(user.getUsername());
            userDto.setEmail(user.getEmail());
            userDto.setPassword(user.getPassword());
            userDto.setBalance(user.getBalance());
            userDto.setCity(user.getCity());
            userDto.setCountry(user.getCountry());
            userDto.setPhoneNumber(user.getPhoneNumber());
            userDto.setDateOfBirth(user.getDateOfBirth());
            userDto.setPassportNumber(user.getPassportNumber());
            return userDto;
        }
        return null;
    }
}
