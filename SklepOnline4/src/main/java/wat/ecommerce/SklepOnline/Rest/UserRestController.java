package wat.ecommerce.SklepOnline.Rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.ecommerce.SklepOnline.Data.UserRepository;
import wat.ecommerce.SklepOnline.Dto.UserCreateDto;
import wat.ecommerce.SklepOnline.Security.UserRepositoryUserDetailsService;

@CrossOrigin(origins = "*")
@RestController
public class UserRestController {
    private final UserRepository userRepository;
    private final UserRepositoryUserDetailsService userRepositoryUserDetailsService;

    public UserRestController(UserRepository userRepository, UserRepositoryUserDetailsService userRepositoryUserDetailsService){
        this.userRepository = userRepository;
        this.userRepositoryUserDetailsService = userRepositoryUserDetailsService;
    }

    @GetMapping("/api/checkuser")
    String findUser(){
        return "You're verified";
    }

    @PostMapping("/api/adduser")
    public ResponseEntity<Void> addNewUser(@RequestBody UserCreateDto user){
        userRepositoryUserDetailsService.signUpUser(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
