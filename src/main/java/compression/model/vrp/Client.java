package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Client {
    @Getter
    private Long id;
    @Getter
    public Double amount;
    @Getter
    public Double time;
    @Getter
    private Location location;

    @Override
    public String toString(){
        return "ID: "+id+" Amount: "+amount+" Time: "+ time + " Location: " + location;
    }
}
