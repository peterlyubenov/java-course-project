package ml.peshka.myday.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "days")
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Min(value = 1, message = "You must rate your day 1 or higher")
    @Max(value = 5, message = "YOu must rate your day 5 or lower")
    @NotNull(message = "Please enter a rating")
    @Column(name = "rating")
    private int rating;

    @Column(name = "date")
    private String date;

    @Length(max = 500, message = "Description should be less than 500 characters")
    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public static String getDateFormat(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public String getFormattedDate() {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = parser.parse(this.date);
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy"); //06 July 2021

            return formatter.format(date);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "parse error";
    }

    public String getEmoji() {
        return new String[]{"😭", "😢", "😐", "🙂", "😀"}[this.getRating() - 1];
    }
}
