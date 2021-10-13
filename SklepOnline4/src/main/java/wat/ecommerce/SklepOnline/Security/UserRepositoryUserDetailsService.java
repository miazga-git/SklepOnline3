package wat.ecommerce.SklepOnline.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import wat.ecommerce.SklepOnline.Data.UserRepository;
import wat.ecommerce.SklepOnline.Dto.UserCreateDto;
import wat.ecommerce.SklepOnline.Entity.User;

@Service
public class UserRepositoryUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public UserRepositoryUserDetailsService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    // Spring Security Authentication Manager wywołuje tę metodę
    // pobierania danych użytkownika z bazy danych podczas
    // uwierzytelniania danych użytkownika dostarczonych przez użytkownika
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow();
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException(
                "Użytkownik '" + username + "' nie został znaleziony.");
    }


    public void signUpUser(UserCreateDto userDto) {

        final String encryptedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptedPassword);
        User user= new User(userDto.getUsername(),userDto.getPassword());
        userRepository.save(user);
        final ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

    }

}
