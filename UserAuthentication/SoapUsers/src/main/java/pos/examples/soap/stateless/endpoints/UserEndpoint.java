package pos.examples.soap.stateless.endpoints;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pos.examples.soap.stateless.Model.UserRepository;
import pos.examples.soap.stateless.Service.UserService;
import stateless.soap.examples.pos.users.*;

import java.math.BigInteger;
import java.util.List;


@Endpoint
public class UserEndpoint {
    private static final String NAMESPACE_URI = "http://pos.examples.soap.stateless/Users";
    private final UserService userService;

    public UserEndpoint(UserService userService)
    {
        this.userService = userService;
    }



    // add a new user
    @PayloadRoot(namespace = NAMESPACE_URI, localPart="addUserRequest")
    @ResponsePayload
    public AddUserResponse addUser(@RequestPayload AddUserRequest userInput){
        AddUserResponse result = new AddUserResponse();

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
        userResp.setFirstName("ceva");
        userResp.setLastName("last");
        userResp.setAge(BigInteger.valueOf(2));

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