package wat.ecommerce.SklepOnline.exception;

public class ItemNotFoundException extends RuntimeException{
    private Long itemId;

    public ItemNotFoundException(Long itemId) {
        this.itemId = itemId;
    }

    @Override
    public String getMessage() {
        return "Item with id: " + itemId + " doesn't exists.";
    }
}
