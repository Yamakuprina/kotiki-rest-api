package ru.itmo.kotiki.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmo.kotiki.dao.entity.Owner;
import ru.itmo.kotiki.dao.entity.User;
import ru.itmo.kotiki.dao.repository.OwnerRepository;
import ru.itmo.kotiki.dao.repository.UserRepository;
import ru.itmo.kotiki.service.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserStorageServiceImpl implements UserStorageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void saveUser(UserDto userDto){
        User user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getRole(), userDto.isEnabled());
        if (userDto.getOwner() != null) {
            user.setOwner(userDto.getOwner());
        }
        String passwordEncoded = encoder.encode(userDto.getPassword());
        user.setPassword(passwordEncoded);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDto> getAllUsers(){
        List<User> allUsers =  userRepository.findAll();
        return usersToUserDtos(allUsers);
    }

    @Override
    public void setOwnerToUser(String userId, String ownerId) {
        User user = userRepository.findById(userId).orElseThrow();
        Owner owner = ownerRepository.findById(ownerId).orElseThrow();
        user.setOwner(owner);
        userRepository.save(user);
    }

    private List<UserDto> usersToUserDtos(List<User> users){
        List<UserDto> userDtos = new ArrayList<>();
        for (User user:users) {
            UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), user.isEnabled());
            userDtos.add(userDto);
        }
        return userDtos;
    }
}
