package pos.examples.soap.stateless.endpoints;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext){
        MessageDispatcherServlet result = new MessageDispatcherServlet();

        result.setApplicationContext(applicationContext);

        result.setTransformWsdlLocations(true);

        return new ServletRegistrationBean(result, "/sample/*");
    }   

    @Bean(name = "users")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema usersSchema){
        DefaultWsdl11Definition result = new DefaultWsdl11Definition();

        result.setPortTypeName("UsersPort");
        result.setLocationUri("/sample");
        result.setTargetNamespace("http://pos.examples.soap.stateless/Users");
        result.setSchema(usersSchema);

        return result;
    }

    @Bean
    public XsdSchema calculatorSchema(){
        return new SimpleXsdSchema(new ClassPathResource("Users.xsd"));
    }

}