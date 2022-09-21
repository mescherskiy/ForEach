package ua.com.foreach.services;

import ua.com.foreach.model.entity.CustomUser;

import java.util.List;

public interface CustomUserService {
    CustomUser create(CustomUser customUser);

    void createByOauth2(CustomUser customUser);
    CustomUser readById(long id);
    CustomUser update(CustomUser customUser);
    void delete(long id);
    List<CustomUser> getAll();
}
