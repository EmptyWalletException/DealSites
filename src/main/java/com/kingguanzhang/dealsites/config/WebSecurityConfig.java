package com.kingguanzhang.dealsites.config;

import com.kingguanzhang.dealsites.service.impl.CustomUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean//注入自定义的customUserServiceImpl;用于在下面来调用;
    UserDetailsService customUserServiceImpl(){
        return new CustomUserServiceImpl();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                   // .antMatchers("/**.css","**/**.js","**/*.jpg","**.gif","**.png","**.svg").permitAll()
                    .antMatchers("/ajax/**").permitAll()
                    .antMatchers("/css/**").permitAll()
                    .antMatchers("/js/**").permitAll()
                    .antMatchers("/images/**").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/index").permitAll()
                    .antMatchers("/index/**").permitAll()
                    .antMatchers("/common/**").permitAll()
                    .antMatchers("/buyer/**").hasAnyRole("USER","ADMIN")//官方文档中特别说明没有使用"ROLE_"前缀
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/seller/**").hasAnyRole("USER","ADMIN")
                    .antMatchers("/**").hasAnyRole("USER","ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login").defaultSuccessUrl("/index",true).failureUrl("/login?error=true")
                .and()
                .logout()
                    .logoutUrl("/logout")//触发注销操作的URL
                    .logoutSuccessUrl("/index")//注销之后跳转的URL
                   // .logoutSuccessHandler(logoutSuccessHandler)//如果指定了这个选项那么logoutSuccessUrl()的设置会被忽略
                    .invalidateHttpSession(true)//指定是否在注销时让HttpSession无效
                   // .addLogoutHandler(logoutHandler)//添加一个LogoutHandler.默认SecurityContextLogoutHandler会被添加为最后一个LogoutHandler
                   // .deleteCookies(cookieNamesToClear)//允许指定在注销成功时将移除的cookie。
                .and()
                .rememberMe().rememberMeParameter("remember-me").rememberMeCookieName("rm")
                .and()
                .httpBasic()
                .and()
                .csrf().disable();//这里如果不禁用csrf那么所有的post请求都会失效;

    }

    /**
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserServiceImpl()).passwordEncoder(new BCryptPasswordEncoder());//使用自定义的service来加载验证信息
                //在内存中创建一个角色,只在测试组件时使用
               /* .inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder()).withUser("user").password(new BCryptPasswordEncoder().encode("user")).roles("USER")
                 .and()
                .passwordEncoder(new BCryptPasswordEncoder()).withUser("admin").password(new BCryptPasswordEncoder().encode("admin")).roles("USER","ADMIN");
        */        //JDBC的验证。
                /*.jdbcAuthentication()
                    .dataSource(dataSource)
                    .withDefaultSchema()
                    .withUser("user").password("password").roles("USER").and()
                    .withUser("admin").password("password").roles("USER", "ADMIN");
                * */
    }



}
