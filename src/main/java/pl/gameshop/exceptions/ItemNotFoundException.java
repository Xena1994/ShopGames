package pl.gameshop.exceptions;

public class ItemNotFoundException extends RuntimeException{

    public ItemNotFoundException() {
        super(String.format("Pozycja nie istnieje"));
    }

    public ItemNotFoundException(Long id){
        super(String.format("Pozycja o id %d nie istnieje", id));
    }
}
