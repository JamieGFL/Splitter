package propra2.splitter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityKonfiguration {

    @Bean
    public SecurityFilterChain configure(HttpSecurity chainbuilder) throws Exception{
        chainbuilder.authorizeHttpRequests(configurer -> configurer.anyRequest().authenticated())
                .logout()
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .oauth2Login();
        return chainbuilder.build();
    }


}
