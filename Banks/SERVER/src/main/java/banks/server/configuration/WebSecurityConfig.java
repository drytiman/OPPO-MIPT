package banks.server.configuration;

import banks.server.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    ClientRepository clientRepository;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/lk_bank", "/verification", "/commission_percent", "/commission", "/percent/debit", "/percent/deposit", "/canceled_show", "/canceled_operation" ).hasRole("BANK")
                .antMatchers("/lk_client", "/add_info", "/open_debit", "/open_credit", "/open_deposit", "/open", "/top_up", "/operations", "/show_operations", "/canceled").hasRole("CLIENT")
                .antMatchers("/clients", "/lk_cb", "/registration/bank", "/calc", "/calcAll").hasRole("CB")
                .antMatchers("/", "/home", "/registration", "/login", "/banks", "/registration/client").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance()).usersByUsernameQuery("select login, password, 'true' from allrole where login=?").authoritiesByUsernameQuery("select login, type from allrole where login=?");

    }
}