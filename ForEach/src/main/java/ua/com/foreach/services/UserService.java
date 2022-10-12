package ua.com.foreach.services;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foreach.dto.UserDTO;
import ua.com.foreach.models.CustomUser;
import ua.com.foreach.repos.CustomUserRepository;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final CustomUserRepository customUserRepository;

    @Transactional(readOnly = true)
    public CustomUser findByLogin(String login) {
        return customUserRepository.findByEmail(login).get();
    }

    @Transactional(readOnly = true)
    public List<CustomUser> getAll() {
        return customUserRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CustomUser getById(Long id) {
        return customUserRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public UserDTO findDtoByLogin(String login) {
        return customUserRepository.findByEmail(login).get().toDTO();
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllDto() {
        return customUserRepository.findAll().stream().map(CustomUser::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public UserDTO getDtoById(Long id) {
        return customUserRepository.findById(id).get().toDTO();
    }


    @Transactional
    public UserDTO updateUser(String firstName, String lastName, String email) {
        customUserRepository.flush();

        customUserRepository.updateUser(firstName, lastName, email);

        return customUserRepository.findByEmail(email).get().toDTO();
    }

    @Transactional(readOnly = true)
    public List<CustomUser> findByPattern(String pattern, Pageable pageable) {
        return customUserRepository.findByPattern(pattern, pageable);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findDtoByPattern(String pattern, Pageable pageable) {
        return customUserRepository.findByPattern(pattern, pageable).stream().map(CustomUser::toDTO).toList();
    }


    public static UserDetails getCurrentUser() {
        return (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
    @Transactional
    public void updateAvatar(CustomUser user, String fileName) throws IOException {

        user.setAvatarFileName(fileName);
        customUserRepository.save(user);
    }
}

