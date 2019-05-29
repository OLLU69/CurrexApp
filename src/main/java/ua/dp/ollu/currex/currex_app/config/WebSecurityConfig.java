package ua.dp.ollu.currex.currex_app.config;

//@Configuration
//@EnableWebSecurity
public class WebSecurityConfig {
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/", "/login").permitAll()
//                .antMatchers("/user").access("hasRole('ROLE_USER')")
//                .antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().defaultSuccessUrl("/", false)
//                .loginPage("/login").permitAll()
//                .and()
//                .logout()
//                .permitAll();
//    }
//
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//
//        Collection<UserDetails> users = new ArrayList<>();
//        users.add(User.withUsername("user")
//                .password("password")
//                .roles("USER")
//                .build());
//        users.add(User.withUsername("admin")
//                .password("admin")
//                .roles("ADMIN")
//                .build());
//        return new InMemoryUserDetailsManager(users);
//    }
}
