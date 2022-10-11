package ua.com.foreach.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foreach.model.CustomUser;
import ua.com.foreach.repos.CustomUserRepository;

import java.io.IOException;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foreach.models.CustomUser;
import ua.com.foreach.repos.CustomUserRepository;

import java.util.List;

@Service
public class UserService {
    private final CustomUserRepository customUserRepository;

    public UserService(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Transactional(readOnly = true)
    public CustomUser findByLogin(String login) {
        return customUserRepository.findByLogin(login);
    }

    @Transactional(readOnly = true)
    public List<CustomUser> getAll() {
        return customUserRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CustomUser getById(Long id) {
        return customUserRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updateUser(String email, String firstName, String lastName) {
        CustomUser user = customUserRepository.findByEmail(email).get();
        if (user == null)
            return;

        user.setFirstName(firstName);
        user.setLastName(lastName);

        customUserRepository.save(user);
    }

    @Transactional
    public void updateAvatar(CustomUser user, String fileName) throws IOException {

        user.setAvatarFileName(fileName);
        userRepository.save(user);
    }
}

