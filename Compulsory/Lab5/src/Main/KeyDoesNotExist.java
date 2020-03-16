package Main;

public class KeyDoesNotExist extends Exception{
    KeyDoesNotExist(String errorMessage){
        super(errorMessage);
    }
}
