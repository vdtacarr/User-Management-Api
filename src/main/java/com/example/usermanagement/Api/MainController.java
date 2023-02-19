package com.example.usermanagement.Api;

import com.example.usermanagement.Dto.UserDto;
import com.example.usermanagement.Entities.User;
import com.example.usermanagement.Repository.Abstract.UserRepository;
import com.example.usermanagement.Util.Absract.IHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.certpath.OCSPResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller // This means that this class is a Controller
@RequestMapping(path="/api") // This means URL's start with /demo (after Application path)
public class MainController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;
   @Autowired
   private IHelper helper;
    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody User addNewUser (@RequestBody User user) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        return userRepository.save(user);
    }
    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteUser(@PathVariable("id") Integer id ){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.delete(user.get());
            return "deleted";
        }
        return "böyle bi kullanıcı bulunamadı.";

    }
    @PostMapping (path="/update")
    public @ResponseBody Object updateUser(@RequestBody User _user){
        Optional<User> user = userRepository.findById(_user.getId());

        if(user.isPresent()){
            User orgUser = user.get();
            orgUser.setEmail(_user.getEmail());
            orgUser.setName(_user.getName());
            return  userRepository.save(orgUser);
        }
        return OCSPResponse.ResponseStatus.INTERNAL_ERROR;

    }

    @GetMapping(path = "/all")
    public @ResponseBody List<UserDto> getAllUsers() {
        List<User> userList = helper.iteratorToList(userRepository.findAll());
        List<UserDto> users = userList.stream().map(el -> new UserDto(
                el.getName(),
                el.getEmail()
        )).collect(Collectors.toList());
        return users;
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody User  getOneUser(@PathVariable("id") Integer id) {
        // This returns a JSON or XML with the users
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
       return null;
    }
}