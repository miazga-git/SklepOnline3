package wat.ecommerce.SklepOnline.exception;

public class BasketNotFoundException extends RuntimeException{
    private Long basketId;

    public BasketNotFoundException(Long basketId) {
        this.basketId = basketId;
    }

    @Override
    public String getMessage() {
        return "Basket with id: " + basketId + " doesn't exists.";
    }
}
