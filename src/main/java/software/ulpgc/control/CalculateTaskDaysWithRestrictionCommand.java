package software.ulpgc.control;

import software.ulpgc.model.Calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class CalculateTaskDaysWithRestrictionCommand implements Command {
    public static final String DATE_FROM = "from";
    public static final String DATE_TO = "to";
    private final Input input;
    private final Output output;
    private final LocalDate fromDate;
    private final LocalDate toDate;

    public CalculateTaskDaysWithRestrictionCommand(Input input, Output output) {
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
            if (!isExcluded(date)) count++;
            numberOfDays--;
        }
        this.output.setOutput(String.format("{\"days\": %d}", count));
    }

    private boolean isExcluded(LocalDate date) {
        return new Calendar()
                .getDaysOfWeekFrom(this.input.getInput("exclude").split(","))
                .contains(DayOfWeek.from(date));
    }

    private Iterable<LocalDate> getDatesFrom() {
        return new Calendar().getDatesFrom(fromDate);
    }
}
