package market;

public enum Type {

    HOURLY("Hour"), QUARTERHOURLY("Quarter-Hour"), HALFHOURLY("Half-Hour");

    private final String type;

    Type (String type) {this.type = type;}

    public String type(){return this.type;}

}
