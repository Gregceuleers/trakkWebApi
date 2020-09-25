package com.trakknamur.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true, // enable pre/post annotations
        securedEnabled = true, // enable @Secured
        jsr250Enabled = true // use of @RoleAllowed
)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
}
