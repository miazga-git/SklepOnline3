package wat.ecommerce.SklepOnline.Rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wat.ecommerce.SklepOnline.Dto.BasketCreateDto;
import wat.ecommerce.SklepOnline.Dto.BasketDto;
import wat.ecommerce.SklepOnline.Dto.DetailsDto;
import wat.ecommerce.SklepOnline.Service.BasketService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class BasketRestController {

    private final BasketService basketService;

    public BasketRestController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("/basketinfo")
    ResponseEntity<List<BasketDto>> findAllBaskets(){
        return new ResponseEntity<>(basketService.findAllBaskets(), HttpStatus.OK);
    }

    @GetMapping("/basketinfo/{basketId}")
    public ResponseEntity<BasketDto> getBasketById(@PathVariable Long basketId) {
        return new ResponseEntity<>(basketService.getBasketById(basketId), HttpStatus.OK);
    }

    @PostMapping("/api/basketfromfront")
    public ResponseEntity<BasketDto> addNewBasketFromFrontend(@RequestBody BasketDto newBasketDto) {
        return new ResponseEntity<>(basketService.createBasketFromFront(newBasketDto), HttpStatus.CREATED);
    }

    @PostMapping("/api/basketinfo")
    public ResponseEntity<BasketDto> createBasket(@RequestBody BasketCreateDto newBasketDto){
        return new ResponseEntity<>(basketService.createBasket(newBasketDto),HttpStatus.CREATED);
    }

    @GetMapping("/api/allUsersCarts/{user}")
    ResponseEntity<List<DetailsDto>> newBasketUser(@PathVariable String user){
        return new ResponseEntity<>(basketService.newBasketUser(user), HttpStatus.OK);
    }

    @GetMapping("/api/details/{id}")
    public ResponseEntity<List<DetailsDto>> getDetails(@PathVariable Long id){
        return new ResponseEntity<>(basketService.getDetailsById(id),HttpStatus.OK);
    }

    @PutMapping("/api/order/payment/{id}/{token}/{state}/{city}/{street}/{zip}/{nameAndSurname}")
    public ResponseEntity<Void> payForOrder(@PathVariable Long id, @PathVariable int token, @PathVariable String state, @PathVariable String city, @PathVariable String street,@PathVariable String zip, @PathVariable String nameAndSurname){
        basketService.updateBasket(id,token,state,city,street,zip,nameAndSurname);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/api/order/resignation/{id}")
    public ResponseEntity<BasketDto> deleteOrder(@PathVariable Long id){
        return new ResponseEntity<>(basketService.deleteOrder(id),HttpStatus.OK);
    }







}


