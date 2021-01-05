package ml.peshka.myday.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ml.peshka.myday.UserNotLoggedInException;
import ml.peshka.myday.model.Day;
import ml.peshka.myday.model.User;
import ml.peshka.myday.service.DayService;
import ml.peshka.myday.service.UserService;

@Controller
public class DaysController {

    @Autowired
    UserService userService;

    @Autowired
    DayService dayService;

    @GetMapping("/days")
    public String myDaysList(Model model) {
        try {
            List<Day> days = dayService.getOwnDays();
            model.addAttribute("days", days);
            return "days/index";
        } catch (UserNotLoggedInException e) {
            return "redirect:/login"; 
        }
    }

    @GetMapping("/days/{id}")
    public String myDay(Model model, @PathVariable("id") int id) {
        try {
            Day day = dayService.getOwnById(id);
            model.addAttribute("day", day);
            return "days/read";
        } catch(UserNotLoggedInException e) {
            return "redirect:/login";
        }
    }

    @GetMapping("/days/{id}/edit")
    public String editDay(Model model, @PathVariable("id") int id) {
        try {
            Day day = dayService.getOwnById(id);
            model.addAttribute("day", day);
            return "days/edit";
        } catch(UserNotLoggedInException e) {
            return "redirect:/login";
        }
    }

    @PostMapping("/days/{id}/edit")
    public String updateDay(
        Model model, 
        @PathVariable("id") int id, 
        @RequestParam("rating") int rating, 
        @RequestParam("description") String description, 
        @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date date
        ) {

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            try {
                Day day = dayService.getOwnById(id);
                day.setRating(rating);
                day.setDate(new java.sql.Date(date.getTime()));
                day.setDescription(description);

                Set<ConstraintViolation<Day>> violations = validator.validate(day);
                if (violations.size() == 0) {
                    dayService.saveDay(day);
                }
                else {
                    model.addAttribute("errors", violations);
                    model.addAttribute("day", day);
                    return "days/edit";
                }

                return "redirect:/days/" + day.getId();
            } catch(UserNotLoggedInException e) {
                return "redirect:/login";
            }
    }
}
