package shop.kimkj.mytrip.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shop.kimkj.mytrip.controller.JwtAuthenticationEntryPoint;
import shop.kimkj.mytrip.controller.JwtAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtRequestFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests()
                // 인증과정 필요
                .antMatchers("/templates/write.html").authenticated()
                .antMatchers("/templates/tripsUpdate.html").authenticated()
                .antMatchers("/popular/place/bookmark/**").authenticated()
                .antMatchers("/popular/bookmark").authenticated()
                .antMatchers("/near/place/bookmark/**").authenticated()
                .antMatchers("/near/bookmark").authenticated()
                .antMatchers("/trips/place/**").authenticated()
                .antMatchers("/profile").authenticated()
                .antMatchers("/popular/bookmark").authenticated()
                .antMatchers("/own").authenticated()

                // 인증과정 필요 없이 모두 허용
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/sign-in").permitAll()
                .antMatchers("/sign-up/**").permitAll()
                .antMatchers("/**.html").permitAll()
                .antMatchers("/templates/**.html").permitAll()
                .antMatchers("/near/**").permitAll()
                .antMatchers("/popular/**").permitAll()
                .antMatchers("/trips/**").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/").permitAll()

                // 그 외 모든 요청은 인증과정 필요
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}