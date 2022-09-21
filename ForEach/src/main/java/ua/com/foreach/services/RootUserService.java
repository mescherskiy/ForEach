package ua.com.foreach.services;

import ua.com.foreach.model.entity.CustomUser;
import ua.com.foreach.model.entity.RootUser;

import java.util.List;

public interface RootUserService {
    RootUser create(RootUser rootUser);
    RootUser readById(long id);
    RootUser update(RootUser customUser);
    void delete(long id);
    List<RootUser> getAll();
}
