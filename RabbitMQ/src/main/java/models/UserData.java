package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData implements Serializable {
    private String name;
    private String surname;
    private Integer passport;
    private Integer inn;
    private Integer age;
    private String date;
}
