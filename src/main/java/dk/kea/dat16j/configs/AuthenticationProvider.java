package dk.kea.dat16j.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

/**
 * Created by Chris on 10-Nov-17.
 */
@Configuration
public class AuthenticationProvider {

    private final Environment env;

    @Autowired
    public AuthenticationProvider(Environment env) {
        this.env = env;
    }

    @Bean(name = "userDetailsService")
    public UserDetailsService userDetailsService() {
        JdbcDaoImpl jdbcImpl = new JdbcDaoImpl();
        jdbcImpl.setDataSource(dataSource());
        // FIXME: 10-Nov-17 in case my queries don't work
//        jdbcImpl.setUsersByUsernameQuery("select username,password, enabled from users where username=?");
//        jdbcImpl.setAuthoritiesByUsernameQuery("select b.username, a.role from user_roles a, users b where b.username=? and a.userid=b.userid");
        jdbcImpl.setUsersByUsernameQuery("select username,password,enabled from account where username=?");
        jdbcImpl.setAuthoritiesByUsernameQuery("select b.username, a.role_name from account_role a, account b where b.username=? and a.id=b.account_role_id");
        return jdbcImpl;
    }

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl(env.getProperty("spring.datasource.url"));
        driverManagerDataSource.setUsername(env.getProperty("spring.datasource.username"));
        driverManagerDataSource.setPassword(env.getProperty("spring.datasource.password"));
        return driverManagerDataSource;
    }
}
