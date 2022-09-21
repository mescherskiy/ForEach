package ua.com.foreach.services.impl;

import org.springframework.stereotype.Service;
import ua.com.foreach.exceptions.NullEntityReferenceException;
import ua.com.foreach.model.entity.CustomUser;
import ua.com.foreach.repos.CustomUserRepository;
import ua.com.foreach.services.CustomUserService;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserServiceImpl implements CustomUserService {

    private final CustomUserRepository customUserRepository;

    public CustomUserServiceImpl(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Override
    public CustomUser create(CustomUser customUser) {
        var user  = customUserRepository.findByEmail(customUser.getEmail());
        if (user.isPresent()){
            throw new EntityExistsException("User already exists");
        }
        return customUserRepository.save(customUser);
    }

    @Override
    public void createByOauth2(CustomUser customUser) {
        var user  = customUserRepository.findByEmail(customUser.getEmail());
        if (!user.isPresent()) {
            customUserRepository.save(customUser);
        }
    }

    @Override
    public CustomUser readById(long id) {
        Optional<CustomUser> opt = customUserRepository.findById(id);
        if (opt.isPresent()){
            return opt.get();
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public CustomUser update(CustomUser customUser) {
        if (customUser == null){
            throw new NullEntityReferenceException();
        }
        var user = customUserRepository.findById(customUser.getId());
        if (user.isPresent()){
            return customUserRepository.save(customUser);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void delete(long id) {
        var user = customUserRepository.findById(id);
        if (user.isPresent()){
            customUserRepository.delete(user.get());
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public List<CustomUser> getAll() {
        return customUserRepository.findAll();
    }
}
