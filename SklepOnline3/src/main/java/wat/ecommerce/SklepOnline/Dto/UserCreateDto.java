package wat.ecommerce.SklepOnline.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserCreateDto {

    private String username;
    private String password;

    public UserCreateDto( String username, String password){
        this.username = username;
        this.password = password;
    }
}
