package project.DB.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import project.DB.server.service.UserDetailService;
import project.DB.server.utils.JwtAuthenticationEntryPoint;
import project.DB.server.utils.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizeHandler;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(this.userDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http    .csrf().disable()
                .cors().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizeHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                // .antMatchers(HttpMethod.GET,"/comunicados","/tarifas").permitAll()
                // .antMatchers(HttpMethod.POST,"/token").permitAll()
                // .antMatchers(HttpMethod.GET,"/consumos/**","/comunicados/**","/**/**").hasAuthority("SOCIO")
                // .antMatchers(HttpMethod.POST,"/reclamos","/solicitudes").hasAnyAuthority("SOCIO")
                // .antMatchers(HttpMethod.GET,"/**/**").hasAuthority("CAJERO")
                // .antMatchers(HttpMethod.POST,"/reclamos").hasAnyAuthority("CAJERO")
                // .antMatchers(HttpMethod.PUT,"/usuarios/**").hasAuthority("CAJERO")
                // .antMatchers(HttpMethod.GET,"/**/**").hasAuthority("LECTURADOR")
                // .antMatchers(HttpMethod.POST,"/reclamos").hasAnyAuthority("LECTURADOR")
                // .antMatchers(HttpMethod.PUT,"/usuarios/**").hasAuthority("LECTURADOR")
                // .antMatchers("/**/**").hasAuthority("ADMIN")

                .antMatchers("comunicados/user","/cobros/**","/medidores/**","/usuarios/**","/roles/**").hasAnyAuthority("ADMIN")
                .anyRequest()
                .authenticated();

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

}