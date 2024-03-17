package com.example.taskManagement.config;


import com.example.taskManagement.model.User;
import com.example.taskManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    private static User currentUser;

    //To get current login-user's details
    public static User getCurrentUser() {
        return currentUser;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(username);
        if(user ==null)
        {
            throw new UsernameNotFoundException("Invalid username !!!");
        }

        currentUser=user;

        //step 2
        return new UserDetailsCreator(user);
    }
}
