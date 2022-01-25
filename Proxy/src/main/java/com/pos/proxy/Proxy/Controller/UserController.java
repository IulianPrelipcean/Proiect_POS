package com.pos.proxy.Proxy.Controller;


import com.pos.proxy.Proxy.Service.UserService;
import lombok.Data;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Data
@RestController
@RequestMapping(path="api/users")
public class UserController {

    private final UserService userService;
    private final HttpServletRequest httpServletRequest;


//    ResponseEntity<?>
    @PostMapping("authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody String credentials){


        //System.out.println("controller request: " + httpServletRequest.getRequestURI());

        JSONObject credentialsJSONObject = new JSONObject(credentials);

        ResponseEntity<?> authenticationResponse =  userService.createAuthenticationRequest(credentialsJSONObject);

        System.out.println("repsonse full: " + authenticationResponse.getStatusCode());

        return authenticationResponse;
    }


}
