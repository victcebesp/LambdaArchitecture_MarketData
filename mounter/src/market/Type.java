package market;

public enum Type {

    HOURLY {
        @Override
        public String type(){
            return "Hour";
        }
    },
    QUARTERHOURLY {
        @Override
        public String type(){
            return "Quarter-Hour";
        }
    },
    HALFHOURLY {
        @Override
        public String type(){
            return "Half-Hour";
        }
    };

    public abstract String type();

}
