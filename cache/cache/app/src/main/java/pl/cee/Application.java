package pl.cee;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import pl.cee.controller.LoginController;
import pl.cee.service.*;

import java.net.SocketException;
import java.security.SecureRandom;
import java.util.Random;

@SpringBootApplication()
@ImportResource("classpath:config-ignite.xml")
public class Application {

    public static void main(String[] args) {
        System.setProperty("IGNITE_QUIET", "false");
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Ignite ignite(IgniteConfiguration ic) {
        return Ignition.start(ic);
    }

    @Bean
    public LoginController loginController(Ignite ignite,
                                           AccountsProvider ap,
                                           SessionIdGenerator sig,
                                           SelfAddressProvider sap) {
        return new LoginController(ignite, ap, sig, sap);
    }

    @Bean
    public AccountsProvider accountsProvider() {
        return new AccountsProviderImpl();
    }

    @Bean
    public SessionIdGenerator sessionIdGenerator(Random random) {
        return new SessionIdGeneratorImpl(random);
    }

    @Bean
    public SelfAddressProvider selfAddressProvider(Environment environment) throws SocketException {
        return new SelfAddressProviderImpl(environment);
    }

    @Bean
    public Random random() {
        return new SecureRandom();
    }
}
