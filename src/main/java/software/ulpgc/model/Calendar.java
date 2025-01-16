package software.ulpgc.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public record Calendar() {
    public Iterable<LocalDate> getDatesFrom(LocalDate from) {
        return new Iterable<LocalDate>() {

            @Override
            public Iterator<LocalDate> iterator() {
                return new Iterator<LocalDate>() {
                    private LocalDate current = from;

                    @Override
                    public boolean hasNext() {
                        return true;
                    }

                    @Override
                    public LocalDate next() {
                        LocalDate next = current;
                        current = current.plusDays(1);
                        return next;
                    }
                };
            }
        };
    }

    public long getNumberOfDaysBetween(LocalDate fromDate, LocalDate toDate) {
        return ChronoUnit.DAYS.between(fromDate, toDate);
    }

    public List<DayOfWeek> getDaysOfWeekFrom(String[] days) {
        return Arrays.stream(days)
                .map(DayOfWeek::valueOf)
                .toList();
    }
}
