package wat.ecommerce.SklepOnline.Rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wat.ecommerce.SklepOnline.WebControllers.AuthenticationBean;

@CrossOrigin(origins = "*")
@RestController
public class AuthenticationController {

    @GetMapping(path = "/api/basicauth")
    public AuthenticationBean authenticate() {
        return new AuthenticationBean("You are authenticated");
    }
}