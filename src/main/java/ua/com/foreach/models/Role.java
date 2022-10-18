package ua.com.foreach.models;

import java.util.Set;

public enum Role {
    USER(Set.of(Permission.USERS_READ)),
    ADMIN(Set.of(Permission.USERS_READ, Permission.USERS_WRITE)),
    MENTOR(Set.of(Permission.USERS_READ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

}
