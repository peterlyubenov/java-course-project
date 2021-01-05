package ml.peshka.myday.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ml.peshka.myday.model.Day;
import ml.peshka.myday.model.User;

@Repository
public interface DayRepository extends JpaRepository<Day, Integer> {
    Day getByDate(Date date);

    List<Day> findAllByUser(User user, Sort sort);
}
