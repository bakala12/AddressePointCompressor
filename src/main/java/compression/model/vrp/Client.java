package compression.model.vrp;

import lombok.Getter;

public class Client extends Location{
    @Getter
    private long id;
    @Getter
    public double amount;
    @Getter
    public double time;

    public Client(long id, double latitude, double longitude, double amount, double time){
        super(latitude, longitude);
        this.id = id;
        this.amount = amount;
        this.time = time;
    }
}
