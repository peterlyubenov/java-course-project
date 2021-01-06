package ml.peshka.myday.helper;

import java.util.Comparator;

import javax.validation.ConstraintViolation;

import ml.peshka.myday.model.Day;

public class SortValidateViolations implements Comparator<ConstraintViolation<Day>> {
    @Override
    public int compare(ConstraintViolation<Day> o1, ConstraintViolation<Day> o2) {
        return o1.getMessage().compareTo(o2.getMessage());
    }
}
