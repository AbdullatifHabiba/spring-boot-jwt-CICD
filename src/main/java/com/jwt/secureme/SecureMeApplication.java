package com.jwt.secureme;

import com.jwt.secureme.dto.AdminDetails;
import com.jwt.secureme.dto.SystemRoles;
import com.jwt.secureme.dto.UserRequest;
import com.jwt.secureme.service.RoleService;
import com.jwt.secureme.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties(AdminDetails.class)
@Slf4j
public class SecureMeApplication implements ApplicationRunner {

    private final UserService userService;
    private final RoleService roleService;
    private final AdminDetails adminDetails;

    public static void main(String[] args) {
        SpringApplication.run(SecureMeApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
    // Admin setup
        try {

            var user = userService.addNewUser(
                    UserRequest.builder()
                            .name(adminDetails.name())
                            .username(adminDetails.username())
                            .password(adminDetails.password()).build()
            );

            if (null == user.getRoles()) {
                addRole();
            }
        } catch (Exception e) {
            log.warn("Adding user failed {}", e.getMessage());
        }
    }

    private void addRole() {
        try {

            if (!roleService.getRole(SystemRoles.ADMIN.name()).isPresent()) {
                roleService.addRole(SystemRoles.ADMIN.name());
            }

            userService.addUserRole("msweelam", SystemRoles.ADMIN.name());
        } catch (Exception e) {
            log.warn("Adding roles failed {}", e.getMessage());
        }
    }
}
