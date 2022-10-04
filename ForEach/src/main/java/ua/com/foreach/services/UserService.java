package ua.com.foreach.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foreach.model.CustomUser;
import ua.com.foreach.repos.CustomUserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final CustomUserRepository userRepository;

    @Transactional(readOnly = true)
    public CustomUser findByLogin(String login) {
        return userRepository.findByEmail(login).
                orElseThrow(() -> new IllegalStateException("user not found!"));
    }


    @Transactional(readOnly = true)
    public List<CustomUser> getAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CustomUser getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    @Transactional
    public void updateUser(String email, String firstName, String lastName) {
        CustomUser user = userRepository.findByEmail(email).
                orElseThrow(() -> new IllegalStateException("user not found!"));

        user.setFirstName(firstName);
        user.setLastName(lastName);

        userRepository.save(user);
    }

}
