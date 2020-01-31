package adf.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import adf.homework.repository.UserRepository;
import adf.homework.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return new UserDetailsImpl(userRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException(String.format("Username %s not found", username))));
    }

}
