package ml.peshka.myday.controller;

import java.util.ArrayList;
import java.util.Collections;
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

import ml.peshka.myday.helper.SortValidateViolations;
import ml.peshka.myday.model.Day;
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
        List<Day> days = dayService.getOwnDays();
        model.addAttribute("days", days);
        return "days/index";
    }

    @GetMapping("/days/{id}")
    public String myDay(Model model, @PathVariable("id") int id) {
        Day day = dayService.getOwnById(id);
        model.addAttribute("day", day);
        return "days/read";
    }

    @GetMapping("/days/add")
    public String addDay(Model model) {
        model.addAttribute("day", new Day());
        return "days/create";
    }

    @PostMapping("/days/add")
    public String createDay(Model model, @RequestParam("rating") int rating, @RequestParam("description") String description, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date date) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Day day = dayService.create(description, new java.sql.Date(date.getTime()), rating);
        Set<ConstraintViolation<Day>> violations = validator.validate(day);

        if(violations.size() == 0) {
            dayService.saveDay(day); 
            return "redirect:/days";
        }
        else {
            List<ConstraintViolation<Day>> errors = new ArrayList<ConstraintViolation<Day>>(violations);
            model.addAttribute("errors", errors);
            model.addAttribute("day", day);
            return "days/create";
        }
    }

    @GetMapping("/days/{id}/edit")
    public String editDay(Model model, @PathVariable("id") int id) {
        model.addAttribute("day", dayService.getOwnById(id));
        return "days/edit";
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

            Day day = dayService.getOwnById(id);
            day.setRating(rating);
            day.setDate(new java.sql.Date(date.getTime()));
            day.setDescription(description);

            Set<ConstraintViolation<Day>> violations = validator.validate(day);
            if (violations.size() == 0) {
                dayService.saveDay(day);
            }
            else {
                List<ConstraintViolation<Day>> errors = new ArrayList<ConstraintViolation<Day>>(violations);

                Collections.sort(errors, new SortValidateViolations());

                model.addAttribute("errors", errors);
                model.addAttribute("day", day);
                return "days/edit";
            }

            return "redirect:/days/" + day.getId();
    }

    @GetMapping("/days/{id}/delete")
    public String delete(Model model, @PathVariable("id") int id) {
        Day day = dayService.getOwnById(id);
        model.addAttribute("day", day);
        return "days/delete";
    }

    @PostMapping("/days/{id}/delete")
    public String destroy(Model model, @PathVariable("id") int id) {
        Day day = dayService.getOwnById(id);
        dayService.deleteDay(day);
        return "redirect:/days";
    }
}
