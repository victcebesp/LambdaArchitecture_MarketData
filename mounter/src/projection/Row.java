package projection;

import org.eifer.box.schemas.DayAheadReport;
import org.eifer.box.schemas.IntradayReport;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Row {

    private String dayAheadPrice;
    private String dayAheadVolume;
    private String intradayWeightedPrice;
    private String intradayID3;
    private String type;
    private Instant instant;

    public Row(){
        dayAheadPrice = "";
        dayAheadVolume = "";
        intradayWeightedPrice = "";
        intradayID3 = "";
    }

    public String dayAheadPrice() {
        return dayAheadPrice;
    }

    public String dayAheadVolume() {
        return dayAheadVolume;
    }

    public String intradayWeightedPrice() {
        return intradayWeightedPrice;
    }

    public String intradayID3() {
        return intradayID3;
    }

    public String type() {
        return type;
    }

    public Instant instant() {
        return instant;
    }

    public Row instant(Instant instant, int index){

        if (type.equals("Hour"))
            this.instant = instant;
        else if (type.equals("Half-Hour"))
            this.instant = instant.plus(index * 30, MINUTES);
        else
            this.instant = instant.plus(index * 15, MINUTES);

        return this;
    }

    public Row type(String type) {
        this.type = type;
        return this;
    }

    public void updateDayAheadRowData(DayAheadReport dayAheadReport) {
        dayAheadVolume = removeUnit(dayAheadReport.volume());
        dayAheadPrice = removeUnit(dayAheadReport.price());
    }

    public void updateIntradayRowData(IntradayReport intradayReport) {
        intradayWeightedPrice = removeUnit(intradayReport.weightedPrice());
        intradayID3 = removeUnit(intradayReport.id3());
    }

    private String removeUnit(String fieldWithUnit) {
        int endIndex = fieldWithUnit.indexOf(" ");
        if (endIndex == -1) return "";
        return fieldWithUnit.substring(0, endIndex).replace(".", ",");
    }
}
