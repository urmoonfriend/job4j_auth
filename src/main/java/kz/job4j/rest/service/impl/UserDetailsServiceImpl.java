package kz.job4j.rest.service.impl;

import kz.job4j.rest.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PersonRepository users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userOpt = users.findByLogin(username);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new User(userOpt.get().getLogin(), userOpt.get().getPassword(), emptyList());

    }
}