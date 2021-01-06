package ml.peshka.myday.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ml.peshka.myday.model.Day;
import ml.peshka.myday.model.User;
import ml.peshka.myday.repository.DayRepository;

@Service
public class DayService {

    @Autowired
    DayRepository dayRepository;

    @Autowired
    UserService userService;

    public Day getById(int id) {
        try {
            return dayRepository.findById(id).get();
        } catch(NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }       
    }


    public Day create(String description, java.sql.Date date, int rating) {
        Day day = new Day();
        day.setUser(userService.findLoggedInUser());
        day.setDate(date);
        day.setRating(rating);
        day.setDescription(description);

        return day;
    }

    /**
     * Get your own day by id. This method validates that the day is owned by the
     * person requesting to see it
     * 
     * @param id The id of the day
     * @return The Day entity
     * @throws UserNotLoggedInException
     */
    public Day getOwnById(int id) {
        Day day = getById(id);

        User user = userService.findLoggedInUser();
        if(day.getUser().getId() == user.getId()) {
            return day;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find a Day with the given id");
        }
    }

    /**
     * Get all days owned by the currently logged in user
     * @return The user's entries
     * @throws UserNotLoggedInException
     */
    public List<Day> getOwnDays() {
        User user = userService.findLoggedInUser();

        return dayRepository.findAllByUser(user, Sort.by("date").ascending());
    }

    /**
     * Save the entity in the database
     * @param day The entity to save
     */
    public void saveDay(Day day) {
        dayRepository.save(day);
    }

    /**
     * Delete a day entry
     * @param day The entitiy to delete
     */
    public void deleteDay(Day day) {
        dayRepository.delete(day);
    }
}