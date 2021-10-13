package wat.ecommerce.SklepOnline.Service;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import wat.ecommerce.SklepOnline.Dto.BasketCreateDto;
import wat.ecommerce.SklepOnline.Entity.Basket;
import wat.ecommerce.SklepOnline.Data.BasketRepository;
import wat.ecommerce.SklepOnline.Data.DetailsRepository;
import wat.ecommerce.SklepOnline.Data.ItemRepository;
import wat.ecommerce.SklepOnline.Entity.Details;
import wat.ecommerce.SklepOnline.Dto.BasketDto;
import wat.ecommerce.SklepOnline.Dto.DetailsDto;
import wat.ecommerce.SklepOnline.Dto.ItemDto;
import wat.ecommerce.SklepOnline.Entity.Item;
import wat.ecommerce.SklepOnline.exception.BasketNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BasketService {

    private final BasketRepository basketRepo;
    private final ItemRepository itemRepo;
    private final DetailsRepository detailsRepo;

    public BasketService(BasketRepository basketRepo,ItemRepository itemRepo,DetailsRepository detailsRepo){
        this.basketRepo = basketRepo;
        this.itemRepo = itemRepo;
        this.detailsRepo = detailsRepo;
    }

    public List<BasketDto> findAllBaskets(){
        return basketRepo.findAll().stream().map(BasketDto::new).collect(Collectors.toList());
    }

    public BasketDto createBasketFromFront(BasketDto newBasketDto){

        List<ItemDto> newItemsDto = new ArrayList<>();
        newItemsDto = newBasketDto.getItems();
        List<Item> newItems = new ArrayList<>();
        for(ItemDto itemDto : newItemsDto){
            newItems.add( new Item(itemDto.getId(),itemDto.getName(),itemDto.getDescription(),itemDto.getPrice(),itemDto.getUrl(),itemDto.getQuantity()));
        }

        int quantity;
        List<Details> details = new ArrayList<>();
        Basket newBasket = new Basket(
                newItems,newBasketDto.getUser(),newBasketDto.getIsPaid(),newBasketDto.isDeleted(),newBasketDto.getState(),newBasketDto.getCity(),newBasketDto.getStreet(),newBasketDto.getZip(),newBasketDto.getNameAndSurname()
        );
        for (int i = 0; i < newItems.size(); i++) {
            quantity = newItems.get(i).getQuantity(); // tutaj zapisuje pomocniczo quantity przeslane [id:1, nazwa:Pepsi, quantity:3] czyli 3
            if (quantity >  itemRepo
                    .findById(newItems.get(i).getId()) // szukam po id oryginalnego cyzli [id:1, nazwa:Pepsi, quantity:1000]
                    .get().getQuantity()) {
                throw new ResourceNotFoundException("Not enough items.left");
            }
            newItems.get(i).setQuantity( //biore sobie przedmiot z koszyka [id:1, nazwa:Pepsi, quantity:3]
                    itemRepo
                            .findById(newItems.get(i).getId()) // szukam po id oryginalnego czyli [id:1, nazwa:Pepsi, quantity:1000]
                            .get().getQuantity() - quantity); // ustawiam w przeslanym obiekcie quantity jako roznica czyli [id:1, nazwa:Pepsi, quantity:1000-3=997]

            details.add(new Details(newBasket,newItems.get(i),quantity));//dodaje jako detale koszyka [obiekt koszyk, obiekt przedmiot - [id:1, nazwa:Pepsi, quantity:1000-3=997] oraz quantity ile kupil klient, czyli 3]
        }
        for (Details detail:details)//zapisuje wszystko
            detailsRepo.save(detail);
        for (Item updatedItem:newItems)
            itemRepo.save(updatedItem);//tutaj aktualizuje sie quantity, bo nie zmienilem nigdzie ID wiec zmienia sie tylko quantity, nie dodaje sie nowy obiekt


        return new BasketDto(basketRepo.save( newBasket ));
    }

    public BasketDto createBasket(BasketCreateDto newBasketDto){
        List<ItemDto> newItemsDto = new ArrayList<>();
        newItemsDto = newBasketDto.getItems();
        List<Item> newItems = new ArrayList<>();
        for(ItemDto itemDto : newItemsDto){
            newItems.add( new Item(itemDto.getName(),itemDto.getDescription(),itemDto.getPrice(),itemDto.getUrl(),itemDto.getQuantity()));
        }
        int quantity;
        for (int i = 0; i < newItems.size(); i++) {
            quantity = newItems.get(i).getQuantity();
            newItems.set(i, findByName(newItems.get(i).getName()));
            newItems.get(i).setQuantity(quantity);
        }
        Basket newBasket = new Basket(
                newItems,newBasketDto.getUser(),newBasketDto.getIsPaid(),newBasketDto.isDeleted(),newBasketDto.getState(),newBasketDto.getCity(),newBasketDto.getStreet(),newBasketDto.getZip(),newBasketDto.getNameAndSurname()
        );
        newBasket.setItems(newItems);
        return new BasketDto(basketRepo.save(newBasket));
    }
    private Item findByName(String name)
    {
        for (Item item:itemRepo.findAll())
        {
            if (item.getName().toUpperCase().equals(name.toUpperCase())) {
                return item;
            }
        }
        return null;
    }

    public List<DetailsDto> newBasketUser(String user){
        List<Basket> baskets = findBasketsByUsername(user);
        List<DetailsDto> detailsDto = new ArrayList<>();

        for (Basket basket:baskets) {
            findDetailsById(basket.getId()).forEach(detail -> detailsDto.add(new DetailsDto(detail)));
        }
        return detailsDto;
    }

    public List<DetailsDto> getDetailsById(Long id){
        return findDetailsById(id).stream().map(DetailsDto::new).collect(Collectors.toList());
    }

    public  BasketDto deleteOrder(Long id){
        Optional<Basket> basket = basketRepo.findById(id);
        basket.get().setDeleted(true);
        List<Details> details = new ArrayList<>();
        int quantity;
        Item item;
        findDetailsById(basket.get().getId()).forEach(detail -> details.add(detail));
        for (Details detail:details){
            item = itemRepo.findById(detail.getItem().getId()).get();
            item.setQuantity(item.getQuantity() + detail.getQuantity());
            itemRepo.save(item);
        }
        return new BasketDto(basketRepo.save(basket.get()));
    }
    public void updateBasket(Long id, int token, String state, String city, String street, String zip, String nameAndSurname){
        Optional<Basket> basket = basketRepo.findById(id);
        basket.get().setIsPaid(token);
        basket.get().setState(state);
        basket.get().setCity(city);
        basket.get().setStreet(street);
        basket.get().setZip(zip);
        basket.get().setNameAndSurname(nameAndSurname);
        basketRepo.save(basket.get());
    }

    private List<Details> findDetailsById(Long id)
    {
        List<Details> details = new ArrayList<Details>();
        detailsRepo.findAll().forEach(detail -> {
            if (detail.getBasket().getId().equals(id))
                details.add(detail);
        });
        return details;
    }
    private List<Basket> findBasketsByUsername(String name)
    {
        List<Basket> baskets = new ArrayList<Basket>();
        basketRepo.findAll().forEach(basket -> {
            if (basket.getUser().equals(name))
                baskets.add(basket);
        });
        return baskets;
    }

    public BasketDto getBasketById(Long basketId) {
        return basketRepo
                .findById(basketId)
                .map(basket -> new BasketDto(basket))
                .orElseThrow(() -> new BasketNotFoundException(basketId));
    }

}
