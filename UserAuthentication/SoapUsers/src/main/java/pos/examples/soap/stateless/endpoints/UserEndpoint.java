package pos.examples.soap.stateless.endpoints;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pos.examples.soap.stateless.Exception.TokenException;
import pos.examples.soap.stateless.JWTConfig.JwtRequestFilter;
import pos.examples.soap.stateless.JWTConfig.JwtTokenUtil;
import pos.examples.soap.stateless.Model.UserRepository;
import pos.examples.soap.stateless.Service.JwtUserDetailsService;
import pos.examples.soap.stateless.Service.UserService;
import stateless.soap.examples.pos.users.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;



import java.math.BigInteger;
import java.util.List;


@Endpoint
public class UserEndpoint {
    private static final String NAMESPACE_URI = "http://pos.examples.soap.stateless/Users";
    private final UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    public UserEndpoint(UserService userService)
    {
        this.userService = userService;
    }


    // check and generate a token
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    //@PreAuthorize("permitAll()")
    @PayloadRoot(namespace = NAMESPACE_URI, localPart="authenticationRequest")
    @ResponsePayload
    public AuthenticationResponse createAuthenticationToken(@RequestPayload AuthenticationRequest authenticationRequest){

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

//        String encodedPassword = bCryptPasswordEncoder.encode("password");
//        System.out.println("password: " + encodedPassword);


        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUser());
            if (userDetails != null && userDetails.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                System.out.println("\n\n========= in create token -- user verification ADMIN ==========\n\n");
            }
            else{
                System.out.println("\n\n========= in create token -- user verification  NOT ADMIN ==========\n\n");
            }

            String token = jwtTokenUtil.generateToken(userDetails);
            authenticationResponse.setStatus("OK");
            authenticationResponse.setToken(token);

        }catch(UsernameNotFoundException e){
            System.out.println("\n\n========= in catch token -- invalid user ==========\n\n");
            authenticationResponse.setStatus("FORBIDDEN");
            authenticationResponse.setToken("");
        }

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUser(), authenticationRequest.getPassword()));
        }catch (DisabledException e) {
            //throw new IllegalStateException("user disabled");
            authenticationResponse.setStatus("UNAUTHORIZED");
            authenticationResponse.setToken("");
        } catch (BadCredentialsException e) {
            //throw new IllegalStateException("invalid credentials");
            authenticationResponse.setStatus("UNAUTHORIZED");
            authenticationResponse.setToken("");
        }

        return authenticationResponse;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart="tokenVerificationRequest")
    @ResponsePayload
    public TokenVerificationResponse verifyToken(@RequestPayload TokenVerificationRequest tokenVerificationRequest){
        TokenVerificationResponse tokenVerificationResponse = new TokenVerificationResponse();

        tokenVerificationResponse.setStatus("token valid");
        //System.out.println("\n========= verify token function==========\n");

        return tokenVerificationResponse;
    }



    // add a new user
    @PayloadRoot(namespace = NAMESPACE_URI, localPart="addUserRequest")
    @ResponsePayload
    public AddUserResponse addUser(@RequestPayload AddUserRequest userInput){
        AddUserResponse result = new AddUserResponse();

        // encode the password before saving it into database
        String encodedPassword = bCryptPasswordEncoder.encode(userInput.getPassword());
        userInput.setPassword(encodedPassword);

        userService.addUser(userInput);

        String response = "User added successfully !";

        result.setResult(response);

        return result;
    }

    // get user By Id
    @PayloadRoot(namespace = NAMESPACE_URI, localPart="getUserByIdRequest")
    @ResponsePayload
    public GetUserByIdResponse getUserById(@RequestPayload GetUserByIdRequest userInput){
        GetUserByIdResponse userResponse;

        userResponse = userService.getUserById(userInput.getId());

        return userResponse;
    }


    // -----****  NU MERGE -----
    // get ALL the users
    @PayloadRoot(namespace = NAMESPACE_URI, localPart="getUserRequest")
    @ResponsePayload
    public List<GetUserResponse> getUser(){
        List<GetUserResponse> userResponse;

        userResponse = userService.getUsers();

        GetUserResponse userResp = new GetUserResponse();
        userResp.setId(BigInteger.valueOf(1));
        userResp.setUsername("ceva");
        userResp.setPassword("last");
        userResp.setRole("ADMIN");

        return userResponse;
    }


    // update a user
    @PayloadRoot(namespace = NAMESPACE_URI, localPart="updateUserByIdRequest")
    @ResponsePayload
    public UpdateUserByIdResponse updateUser(@RequestPayload UpdateUserByIdRequest userInput){
        UpdateUserByIdResponse userResponse = new UpdateUserByIdResponse();

        userService.updateUser(userInput.getId(), userInput.getFirstName());
        userResponse.setResult("user updated successfully!");

        return userResponse;
    }


    // delete a user
    @PayloadRoot(namespace = NAMESPACE_URI, localPart="deleteUserByIdRequest")
    @ResponsePayload
    public DeleteUserByIdResponse deleteUser(@RequestPayload DeleteUserByIdRequest userInput){
        DeleteUserByIdResponse userResponse = new DeleteUserByIdResponse();

        userService.deleteUser(userInput.getId());
        userResponse.setResult("user deleted successfully!");

        return userResponse;
    }



}