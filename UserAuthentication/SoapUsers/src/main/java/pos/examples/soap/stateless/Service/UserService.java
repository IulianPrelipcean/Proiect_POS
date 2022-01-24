package pos.examples.soap.stateless.Service;

import org.springframework.stereotype.Service;
import pos.examples.soap.stateless.Model.User;
import pos.examples.soap.stateless.Model.UserRepository;
import stateless.soap.examples.pos.users.AddUserRequest;
import stateless.soap.examples.pos.users.GetUserByIdResponse;
import stateless.soap.examples.pos.users.GetUserResponse;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void addUser(AddUserRequest userInput){
        User user = new User(userInput.getUsername(), userInput.getPassword(), userInput.getRole());
        userRepository.save(user);
    }


    public GetUserByIdResponse getUserById(Integer userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent())
        {
            User userData = userOptional.get();
            GetUserByIdResponse userResponse = new GetUserByIdResponse();

            userResponse.setId(BigInteger.valueOf(userData.getId()));
            userResponse.setUsername(userData.getUsername());
            userResponse.setPassword(userData.getPassword());
            userResponse.setRole(userData.getRole());

            return userResponse;
        }
        else
        {
            throw new IllegalStateException("The user cannot be found!");
        }


    }


    // ----- ******* NU MERGE ---------
    public List<GetUserResponse> getUsers(){
        Optional<List<User>> userOptional = Optional.of(userRepository.findAll());

        System.out.println(userOptional.get());
        if(userOptional.isPresent())
        {
            List<User> userList = userOptional.get();
            List<GetUserResponse> getUserResponseList = new ArrayList<>(List.of());
            for(User u: userList){
                GetUserResponse userResponse = new GetUserResponse();
                userResponse.setId(BigInteger.valueOf(u.getId()));
                userResponse.setUsername(u.getUsername());
                userResponse.setPassword(u.getPassword());
                userResponse.setRole(u.getRole());


                getUserResponseList.add(userResponse);
            }
            return getUserResponseList;
        }
        else {
            throw new IllegalStateException("No existent user!");
        }
    }


    public void updateUser(Integer userId, String username){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent())
        {
            User userData = userOptional.get();
            userData.setPassword(username);
            userRepository.save(userData);
        }
        else
        {
            throw new IllegalStateException("The user cannot be found!");
        }
    }


    public void deleteUser(Integer userId){
        boolean exists = userRepository.existsById(userId);
        if(exists)
        {
           userRepository.deleteById(userId);
        }
        else
        {
            throw new IllegalStateException("The user cannot be found!");
        }
    }

}
