package compression.model.vrp;

import lombok.Getter;

public class Client extends Location{
    @Getter
    private String name;
    @Getter
    public double amount;
    @Getter
    public double time;

    public Client(String name, double latitude, double longitude, double amount, double time){
        super(latitude, longitude);
        this.name = name;
        this.amount = amount;
        this.time = time;
    }
}
