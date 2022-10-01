package ua.com.foreach.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import ua.com.foreach.models.CustomUser;
import ua.com.foreach.repos.CustomUserRepository;

import java.util.List;

@Service
public class GeneralService {
    private final CustomUserRepository customUserRepository;

    public GeneralService(CustomUserRepository customUserRepository) {
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
        CustomUser user = customUserRepository.findByLogin(email);
        if (user == null)
            return;

        user.setFirstName(firstName);
        user.setLastName(lastName);

        customUserRepository.save(user);
    }

}

