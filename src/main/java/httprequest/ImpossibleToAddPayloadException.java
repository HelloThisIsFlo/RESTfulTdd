package httprequest;

public class ImpossibleToAddPayloadException extends Exception{

    public ImpossibleToAddPayloadException() {
        super("Please only add payload to PUT httprequest");
    }
}
