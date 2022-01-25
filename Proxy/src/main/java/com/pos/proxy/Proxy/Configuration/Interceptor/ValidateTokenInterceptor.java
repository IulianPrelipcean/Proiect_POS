package com.pos.proxy.Proxy.Configuration.Interceptor;

import com.pos.proxy.Proxy.ConfigurationWSDL.TokenVerificationRequest;
import com.pos.proxy.Proxy.ConfigurationWSDL.TokenVerificationResponse;
import com.pos.proxy.Proxy.Service.UserService;
import lombok.Data;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceTransportException;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.HeadersAwareSenderWebServiceConnection;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Data
@Component
public class ValidateTokenInterceptor implements HandlerInterceptor {

    //private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        //userService.setHttpServletRequest(request);

//        System.out.println("first Request: " + request.getRequestURI());
//        System.out.println("second Request: " + request.getRequestURL());
//
//        String token = request.getHeader("Authorization");
//
//        System.out.println("Token: " + token);
//
//
//
//        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
//        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//
//        marshaller.setClassesToBeBound(TokenVerificationRequest.class, TokenVerificationResponse.class);
//        webServiceTemplate.setMarshaller(marshaller);
//        webServiceTemplate.setUnmarshaller(marshaller);
//
//        final TokenVerificationRequest tokenVerificationRequest = new TokenVerificationRequest();
//        tokenVerificationRequest.setToken(token.substring(7));
//
//
//        try {
//            TokenVerificationResponse tokenVerificationResponse = (TokenVerificationResponse) webServiceTemplate
//                    .marshalSendAndReceive("http://localhost:8082/sample", tokenVerificationRequest, new WebServiceMessageCallback() {
//                        @Override
//                        public void doWithMessage(WebServiceMessage message) throws IOException {
//                            TransportContext context = TransportContextHolder.getTransportContext();
//                            HeadersAwareSenderWebServiceConnection connection = (HeadersAwareSenderWebServiceConnection) context.getConnection();
//                            connection.addRequestHeader("Authorization", token);
//                        }
//                    });
//            System.out.println("response token: " + tokenVerificationResponse.getStatus());
//        } catch (WebServiceTransportException exception) {
//            throw new IllegalStateException("Unauthorized request");
//        }


//        http://localhost:8082/sample/users.wsdl



        return true;
    }

}