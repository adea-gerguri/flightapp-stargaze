package user.mappers;

import user.models.dto.CreateUserDto;
import user.models.dto.UserDto;
import user.models.UserEntity;

public class UserMapper {
    public static UserEntity toUser(CreateUserDto userDto){
        if(userDto!=null){
            UserEntity userEntity = new UserEntity();
            userEntity.setFirstName(userDto.getFirstName());
            userEntity.setLastName(userDto.getLastName());
            userEntity.setUsername(userDto.getUsername());
            userEntity.setEmail(userDto.getEmail());
            userEntity.setPassword(userDto.getPassword());
            userEntity.setBalance(userDto.getBalance());
            userEntity.setCity(userDto.getCity());
            userEntity.setCountry(userDto.getCountry());
            userEntity.setPhoneNumber(userDto.getPhoneNumber());
            userEntity.setDateOfBirth(userDto.getDateOfBirth());
            userEntity.setPassportNumber(userDto.getPassportNumber());
            return userEntity;
        }
        return null;
    }
    public static UserDto toUserDto(UserEntity userEntity){
        if(userEntity !=null){
            UserDto userDto = new UserDto();
            userDto.setFirstName(userEntity.getFirstName());
            userDto.setLastName(userEntity.getLastName());
            userDto.setUsername(userEntity.getUsername());
            userDto.setEmail(userEntity.getEmail());
            userDto.setPassword(userEntity.getPassword());
            userDto.setBalance(userEntity.getBalance());
            userDto.setCity(userEntity.getCity());
            userDto.setCountry(userEntity.getCountry());
            userDto.setPhoneNumber(userEntity.getPhoneNumber());
            userDto.setDateOfBirth(userEntity.getDateOfBirth());
            userDto.setPassportNumber(userEntity.getPassportNumber());
            return userDto;
        }
        return null;
    }
}
