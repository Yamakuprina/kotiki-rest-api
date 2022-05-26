package ru.itmo.kotiki.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itmo.kotiki.dao.entity.Owner;
import ru.itmo.kotiki.dao.entity.User;
import ru.itmo.kotiki.dao.repository.UserRepository;
import ru.itmo.kotiki.service.userDetails.KotikiUserDetails;

import java.util.List;


public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new KotikiUserDetails(user);
    }
}
