package com.pos.proxy.Proxy.Service;


import com.pos.proxy.Proxy.Configuration.Interceptor.ValidateTokenInterceptor;
import com.pos.proxy.Proxy.ConfigurationWSDL.AuthenticationRequest;
import com.pos.proxy.Proxy.ConfigurationWSDL.AuthenticationResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceTransportException;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.HeadersAwareSenderWebServiceConnection;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Data
@Service
public class UserService {

    private HttpServletRequest httpServletRequest;

    //ResponseEntity<?>

    public ResponseEntity<?> createAuthenticationRequest(JSONObject credentialsJSONObject){


        //System.out.println("credden: " + credentialsJSONObject.get("username"));


        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

        marshaller.setClassesToBeBound(AuthenticationRequest.class, AuthenticationResponse.class);
        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setUnmarshaller(marshaller);

        final AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        try {
            authenticationRequest.setUser(credentialsJSONObject.get("username").toString());
            authenticationRequest.setPassword(credentialsJSONObject.get("password").toString());
        }catch(JSONException e){
            JSONObject responseJSON = new JSONObject();
            responseJSON.put("status", "UNAUTHORIZED");
            responseJSON.put("token", "");
            return new ResponseEntity<>(responseJSON.toString(), HttpStatus.UNAUTHORIZED);
        }

        try {
            AuthenticationResponse authenticationResponse = (AuthenticationResponse) webServiceTemplate
                    .marshalSendAndReceive("http://localhost:8082/authenticate", authenticationRequest, new WebServiceMessageCallback() {
                        @Override
                        public void doWithMessage(WebServiceMessage message) throws IOException {
                            TransportContext context = TransportContextHolder.getTransportContext();
                            HeadersAwareSenderWebServiceConnection connection = (HeadersAwareSenderWebServiceConnection) context.getConnection();
                        }
                    });

            System.out.println("response token: " + authenticationResponse.getToken());
            System.out.println("response status: " + authenticationResponse.getStatus());

            JSONObject responseJSON = new JSONObject();
            responseJSON.put("status", authenticationResponse.getStatus());
            responseJSON.put("token", authenticationResponse.getToken());

            if(authenticationResponse.getStatus().equals("OK"))
            {
                System.out.println("in status ok");
                return new ResponseEntity<>(responseJSON.toString(), HttpStatus.OK);
            }
            else{
                System.out.println("in status forbidden");
                return new ResponseEntity<>(responseJSON.toString(), HttpStatus.UNAUTHORIZED);
            }
        } catch (WebServiceTransportException exception) {

            JSONObject responseJSON = new JSONObject();
            responseJSON.put("status", "UNAUTHORIZED");
            responseJSON.put("token", "nop");

            System.out.println("in catch forbidden");
            return new ResponseEntity<>(responseJSON.toString(), HttpStatus.UNAUTHORIZED);
            //throw new IllegalStateException("Unauthorized request");
        }



    }


}
