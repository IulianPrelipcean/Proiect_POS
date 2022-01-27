package com.example.pos.Configuration.Interceptor;


import com.example.pos.ConfigurationWSDL.TokenVerificationRequest;
import com.example.pos.ConfigurationWSDL.TokenVerificationResponse;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
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


@Component
public class ValidateTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        System.out.println("first Request: " + request.getRequestURI());
        System.out.println("second Request: " + request.getRequestURL());

        if(request.getRequestURI().equals("/api/bookcollection/books")){
            System.out.println("special resuest\n\n");
            return true;
        }
        else{
            String token = request.getHeader("Authorization");

            System.out.println("Token: " + token);



            WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
            Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

            marshaller.setClassesToBeBound(TokenVerificationRequest.class, TokenVerificationResponse.class);
            webServiceTemplate.setMarshaller(marshaller);
            webServiceTemplate.setUnmarshaller(marshaller);

            final TokenVerificationRequest tokenVerificationRequest = new TokenVerificationRequest();
            tokenVerificationRequest.setToken(token.substring(7));


            try {
                TokenVerificationResponse tokenVerificationResponse = (TokenVerificationResponse) webServiceTemplate
                        .marshalSendAndReceive("http://localhost:8082/sample", tokenVerificationRequest, new WebServiceMessageCallback() {
                            @Override
                            public void doWithMessage(WebServiceMessage message) throws IOException {
                                TransportContext context = TransportContextHolder.getTransportContext();
                                HeadersAwareSenderWebServiceConnection connection = (HeadersAwareSenderWebServiceConnection) context.getConnection();
                                connection.addRequestHeader("Authorization", token);
                            }
                        });
                System.out.println("response token: " + tokenVerificationResponse.getStatus());
            } catch (WebServiceTransportException exception) {
                throw new IllegalStateException("Unauthorized request");
            }


//        http://localhost:8082/sample/users.wsdl

            return true;
        }
    }

}
