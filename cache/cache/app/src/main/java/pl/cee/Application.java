package pl.cee;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import pl.cee.controller.LoginController;
import pl.cee.model.State;
import pl.cee.service.*;
import pl.cee.util.IgniteCacheMapView;

import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;
import java.net.SocketException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@SpringBootApplication()
@ImportResource("classpath:config-ignite.xml")
@EnableSpringHttpSession
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
                                           AccountsProvider accountsProvider,
                                           SessionIdGenerator sessionIdGenerator,
                                           SelfAddressProvider selfAddressProvider) {
        IgniteCache<String, State> cache = ignite.cache("appSessionCache");

        ExpiryPolicy expiryPolicy = new CreatedExpiryPolicy(new Duration(TimeUnit.MINUTES, 10));
        IgniteCache<String, State> expiringCache = cache.withExpiryPolicy(expiryPolicy);

        return new LoginController(expiringCache, accountsProvider, sessionIdGenerator, selfAddressProvider);
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

    @Bean
    public SessionRepository sessionRepository(Ignite ignite) {
        IgniteCache<String, ExpiringSession> igSsc = ignite.cache("springSessionCache");
        IgniteCacheMapView<String, ExpiringSession> igSscMapView = new IgniteCacheMapView<>(igSsc);
        return new MapSessionRepository(igSscMapView);
    }
}
