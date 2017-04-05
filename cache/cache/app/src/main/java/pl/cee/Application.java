package pl.cee;

import org.apache.ignite.cache.websession.WebSessionFilter;
import org.apache.ignite.startup.servlet.ServletContextListenerStartup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import pl.cee.controller.LoginController;
import pl.cee.service.AccountsProvider;
import pl.cee.service.AccountsProviderImpl;
import pl.cee.service.SelfAddressProvider;
import pl.cee.service.SelfAddressProviderImpl;

import javax.servlet.ServletContextListener;
import java.net.SocketException;

@SpringBootApplication()
public class Application {

    public static void main(String[] args) {
        System.setProperty("IGNITE_QUIET", "false");
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public LoginController loginController(AccountsProvider accountsProvider,
                                           SelfAddressProvider selfAddressProvider) {
        return new LoginController(accountsProvider, selfAddressProvider);
    }

    @Bean
    public AccountsProvider accountsProvider() {
        return new AccountsProviderImpl();
    }

    @Bean
    public SelfAddressProvider selfAddressProvider(Environment environment) throws SocketException {
        return new SelfAddressProviderImpl(environment);
    }

    /*
        <listener>
            <listener-class>org.apache.ignite.startup.servlet.ServletContextListenerStartup</listener-class>
        </listener>
    */
    @Bean
    public ServletContextListener igniteListener() {
        return new ServletContextListenerStartup();
    }

    /*
        <filter>
            <filter-name>IgniteWebSessionsFilter</filter-name>
            <filter-class>org.apache.ignite.cache.websession.WebSessionFilter</filter-class>
        </filter>

        <filter-mapping>
            <filter-name>IgniteWebSessionsFilter</filter-name>
            <url-pattern>/*</url-pattern>
        </filter-mapping>
    */
    @Bean
    public FilterRegistrationBean igniteFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("IgniteWebSessionsFilter");
        registration.setFilter(new WebSessionFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

    /*
        <context-param>
           <param-name>IgniteConfigurationFilePath</param-name>
           <param-value>config/default-config.xml </param-value>
        </context-param>

        <context-param>
           <param-name>IgniteWebSessionsCacheName</param-name>
           <param-value>partitioned</param-value>
        </context-param>
    */
    @Bean
    public ServletContextInitializer igniteContextParam() {
        return servletContext -> {
            servletContext.setInitParameter("IgniteConfigurationFilePath", "config-ignite.xml");
            servletContext.setInitParameter("IgniteWebSessionsCacheName", "someCache");
        };
    }
}
