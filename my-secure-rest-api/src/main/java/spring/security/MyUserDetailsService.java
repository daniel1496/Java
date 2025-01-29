package spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
 * Simplified authentication flow
 * 1. The request is passed to the filter chain
 * 2. Spring calls MyUserDetailsService::loadUserByUsername passing in the username from the request header
 * 3. If the username exists in your DB then a UserDetails object (incl. encrypted password) is returned
 * 4. If the username does not exist in your DB then an exception is thrown and Spring sets 401 on the response
 * 5. Spring encrypts the password from the request header
 * 6. Spring compares the encrypted passwords (from the header and DB)
 * 7. If the passwords match then Spring adds an Authentication object to the SecurityContext and carries on
 * 8. If the passwords don't match then Spring sets 401 on the response
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repo.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("No such user");
        } else {
            return new MyUserDetails(user.get());
        }
    }
}
