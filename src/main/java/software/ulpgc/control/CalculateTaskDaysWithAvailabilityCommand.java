package software.ulpgc.control;

import software.ulpgc.model.Calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class CalculateTaskDaysWithAvailabilityCommand implements Command{
    public static final String DATE_FROM = "from";
    public static final String DATE_TO = "to";
    private final Input input;
    private final Output output;
    private final LocalDate fromDate;
    private final LocalDate toDate;

    public CalculateTaskDaysWithAvailabilityCommand(Input input, Output output) {
        this.input = input;
        this.output = output;
        this.fromDate = LocalDate.parse(this.input.getInput(DATE_FROM));
        this.toDate = LocalDate.parse(this.input.getInput(DATE_TO));
    }

    @Override
    public void execute() {
        long numberOfDays = new Calendar().getNumberOfDaysBetween(fromDate, toDate);
        int count = 0;
        for (LocalDate date : getDatesFrom()) {
            if (numberOfDays < 0) break;
            if(isAvailable(date)) count++;
            numberOfDays--;
        }
        this.output.setOutput(String.format("{\"days\" : %d}", count));
    }

    private boolean isAvailable(LocalDate date) {
        return new Calendar()
                .getDaysOfWeekFrom(this.input.getInput("available").split(","))
                .contains(DayOfWeek.from(date));
    }

    private Iterable<LocalDate> getDatesFrom() {
        return new Calendar().getDatesFrom(fromDate);
    }
}