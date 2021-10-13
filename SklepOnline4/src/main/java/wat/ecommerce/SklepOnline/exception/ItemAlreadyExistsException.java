package wat.ecommerce.SklepOnline.exception;

public class ItemAlreadyExistsException extends RuntimeException{

    private String name;

    public ItemAlreadyExistsException(String name) {
        this.name = name;
    }

    @Override
    public String getMessage(){
        return "Item with name: " + name + " already exists.";}
}
