package dk.kea.dat16j.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * Created by Chris on 13-Nov-17.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final DataSource dataSource;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService, DataSource dataSource) {
        this.userDetailsService = userDetailsService;
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
//                .failureUrl("/login?error=loginError")
//                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll();
//                .deleteCookies("JSESSIONID")
//                .logoutSuccessUrl("/login")
//                .and()
//                .exceptionHandling()
//                .accessDeniedPage("/403")
//                .and()
//                .csrf().disable();

        // For later usages
//        http.authorizeRequests()
//                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//                .and()
//                .formLogin().loginPage("/login").failureUrl("/login?error")
//                .usernameParameter("username").passwordParameter("password")
//                .and()
//                .logout().logoutSuccessUrl("/login?logout")
//                .and()
//                .exceptionHandling().accessDeniedPage("/403")
//                .and()
//                .csrf();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(
                "select username,password,enabled from account where username=?")
                .authoritiesByUsernameQuery(
                        "select b.username, a.role_name from account_role a, account b where b.username=? and a.id=b.account_role_id");
//        auth
//                .inMemoryAuthentication()
//                .withUser("student").password("").roles(AccountRoles.STUDENT.getRole());
//        auth
//                .inMemoryAuthentication()
//                .withUser("administrator").password("").roles(AccountRoles.ADMINISTRATOR.getRole());
//        auth
//                .inMemoryAuthentication()
//                .withUser("teacher").password("").roles(AccountRoles.TEACHER.getRole());
    }


    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
