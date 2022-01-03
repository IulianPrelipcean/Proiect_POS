package pos.examples.soap.stateless.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import pos.examples.soap.stateless.Exception.TokenException;
import pos.examples.soap.stateless.JWTConfig.JwtAuthenticationEntryPoint;
import pos.examples.soap.stateless.JWTConfig.JwtRequestFilter;

@EnableWs
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebServiceConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    @SuppressWarnings({"unchecked", "rawtypes"})
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext){
        MessageDispatcherServlet result = new MessageDispatcherServlet();
        result.setApplicationContext(applicationContext);
        result.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(result, "/authenticate/*", "/sample/*");
    }

    @Bean(name = "users")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema usersSchema){
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("UsersPort");
        result.setLocationUri("/authenticate");
        result.setTargetNamespace("http://pos.examples.soap.stateless/Users");
        result.setSchema(usersSchema);

        return result;
    }

//    @Bean(name = "users2")
//    public DefaultWsdl11Definition defaultWsdl11Definition_2(XsdSchema usersSchema){
//        DefaultWsdl11Definition result = new DefaultWsdl11Definition();
//
//        result.setPortTypeName("UsersPort");
//        result.setLocationUri("/sample");
//        result.setTargetNamespace("http://pos.examples.soap.stateless/Users");
//        result.setSchema(usersSchema);
//
//        return result;
//    }


    @Bean
    public XsdSchema userSchema(){
        return new SimpleXsdSchema(new ClassPathResource("Users.xsd"));
    }


    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder

//        auth
//                .inMemoryAuthentication()
//                .withUser("user").password("{noop}pass").roles("USER")
//                .and()
//                .withUser("admin").password("{noop}pass").roles("ADMIN");

        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example
        httpSecurity.csrf().disable()

                //.authorizeRequests().anyRequest().permitAll();
                // dont authenticate this particular request
                .authorizeRequests().antMatchers("/authenticate").permitAll().
                // all other requests need to be authenticated
                        anyRequest().authenticated().and().
                 //make sure we use stateless session; session won't be used to
                 //store user's state.
                        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        TokenException tokenException = jwtRequestFilter.getTokenException();
//        System.out.println("token config run: " + tokenException.getTokenExpire());

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }



}