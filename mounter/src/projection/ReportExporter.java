package projection;

import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;

public class ReportExporter {

    public static String mapReportStreamToString(Stream<Map.Entry<String, Report>> reports){
        return reports.map(Map.Entry::getValue)
                .map(ReportExporter::exportToCsv)
                .collect(Collectors.joining());
    }

    private static String exportToCsv(Report report) {
        return quarterHoursToCSV(report) + halfHoursToCSV(report) + hourToCSV(report);
    }

    private static String quarterHoursToCSV(Report report) {
        return Arrays.stream(report.quarterHours()).map(r -> rowToCSV(report, r)).collect(Collectors.joining());
    }

    private static String halfHoursToCSV(Report report) {
        return Arrays.stream(report.halfHours()).map(r -> rowToCSV(report, r)).collect(Collectors.joining());
    }

    private static String hourToCSV(Report report) {
        return rowToCSV(report, report.hour());
    }

    private static String rowToCSV(Report report, Row row){
        return report.priceZone() + ";" +
                date(row.instant()) + ";" +
                from(row.instant()) + ";" +
                to(row.instant(), row.type()) + ";" +
                row.type() + ";" +
                row.dayAheadPrice() + ";" +
                row.dayAheadVolume() + ";" +
                row.intradayWeightedPrice() + ";" +
                row.intradayID3() + ";\n";
    }

    private static String date(Instant instant) {
        return instant.toString().substring(0, instant.toString().indexOf("T"));
    }

    private static String from(Instant instant) {
        return instant.toString().substring(instant.toString().indexOf("T") + 1, instant.toString().indexOf("Z"));
    }

    private static String to(Instant instant, String type) {

        String hourTo;

        if (type.equals("Hour")) hourTo = instant.plus(1, HOURS).toString();
        else if (type.equals("Half-Hour")) hourTo = instant.plus(30, MINUTES).toString();
        else hourTo = instant.plus(15, MINUTES).toString();

        return hourTo.substring(hourTo.indexOf("T") + 1, hourTo.indexOf("Z"));
    }

}
