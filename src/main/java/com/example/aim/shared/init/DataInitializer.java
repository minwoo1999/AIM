package com.example.aim.shared.init;

import com.example.aim.member.domain.UserEntity;
import com.example.aim.member.domain.MemberRole;
import com.example.aim.member.domain.repository.MemberRepository;
import com.example.aim.security.domain.SecurityEntity;
import com.example.aim.security.domain.repository.SecurityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MemberRepository userRepository;
    private final SecurityRepository securityRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeAdminUser();
        initializeSecurities();
    }

    private void initializeAdminUser() {
        if (userRepository.count() == 0) {
            UserEntity adminUser = UserEntity.builder()
                    .username("admin")
                    .passwordHash(passwordEncoder.encode("1234"))
                    .role(MemberRole.ADMIN)
                    .balance(BigDecimal.valueOf(1000000))
                    .build();
            userRepository.save(adminUser);
        }
    }

    private void initializeSecurities() {
        if (securityRepository.count() < 10) {
            List<SecurityEntity> securities = Arrays.asList(
                    SecurityEntity.builder().code("SEC001").name("Security 1").price(BigDecimal.valueOf(100.00)).build(),
                    SecurityEntity.builder().code("SEC002").name("Security 2").price(BigDecimal.valueOf(200.00)).build(),
                    SecurityEntity.builder().code("SEC003").name("Security 3").price(BigDecimal.valueOf(300.00)).build(),
                    SecurityEntity.builder().code("SEC004").name("Security 4").price(BigDecimal.valueOf(400.00)).build(),
                    SecurityEntity.builder().code("SEC005").name("Security 5").price(BigDecimal.valueOf(500.00)).build(),
                    SecurityEntity.builder().code("SEC006").name("Security 6").price(BigDecimal.valueOf(600.00)).build(),
                    SecurityEntity.builder().code("SEC007").name("Security 7").price(BigDecimal.valueOf(700.00)).build(),
                    SecurityEntity.builder().code("SEC008").name("Security 8").price(BigDecimal.valueOf(800.00)).build(),
                    SecurityEntity.builder().code("SEC009").name("Security 9").price(BigDecimal.valueOf(900.00)).build(),
                    SecurityEntity.builder().code("SEC010").name("Security 10").price(BigDecimal.valueOf(1000.00)).build()
            );
            securityRepository.saveAll(securities);
        }
    }
}
