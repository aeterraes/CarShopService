package aeterraes.security;

import aeterraes.entities.User;
import aeterraes.models.LoginStatus;
import aeterraes.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class AuthService {

    private List<User> authenticatedUsers = new ArrayList<>();

    /**
     * Attempt to log in a user with the provided email and password
     *
     * @param email    The email of the user trying to log in
     * @param password The password provided for authentication
     * @return The login status checking success or failure
     */
    public LoginStatus login(String email, String password) {
        Optional<User> userOpt = authenticatedUsers.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return LoginStatus.AUTHENTICATED;
            } else {
                return LoginStatus.INVALID_CREDENTIALS;
            }
        } else {
            return LoginStatus.USER_NOT_FOUND;
        }
    }

    /**
     * Register a new user if their email does not already exist in the system
     *
     * @param newUser The user to register
     * @return The registration status checking success or failure
     */
    public LoginStatus register(User newUser) {
        Optional<User> existingUserOpt = authenticatedUsers.stream()
                .filter(user -> user.getEmail().equals(newUser.getEmail()))
                .findFirst();

        if (existingUserOpt.isPresent()) {
            return LoginStatus.INVALID_CREDENTIALS;
        } else {
            authenticatedUsers.add(newUser);
            return LoginStatus.AUTHENTICATED;
        }
    }

    /**
     * Check if a user has access to a given role
     *
     * @param user         The user whose access is being checked
     * @param requiredRole The role required for access
     * @return True if the user has the required role
     */
    public boolean hasAccess(User user, Role requiredRole) {
        if (user == null) {
            return false;
        }

        return switch (requiredRole) {
            case ADMIN -> user.getRole() == Role.ADMIN;
            case MANAGER -> user.getRole() == Role.ADMIN || user.getRole() == Role.MANAGER;
            case USER -> user.getRole() == Role.ADMIN || user.getRole() == Role.MANAGER || user.getRole() == Role.USER;
        };
    }
}
