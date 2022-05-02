package collection;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class Human implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name; //Поле не может быть null, Строка не может быть пустой
    private Long age; //Значение поля должно быть больше 0
    private LocalDate birthday;

    public Human(String name, Date birthday){
        this.name = name;
        this.birthday = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.age = (long)Period.between(this.birthday,LocalDate.now()).getYears();
    }

    public int compareTo(Human o){
        if (o == null){
            return 1;
        }
        int r = 0;
        if (this.name!=null)
            r = this.name.compareTo(o.name);
        if (r==0){
            if (this.birthday!=null)
                r = this.birthday.compareTo(o.birthday);
            else if (o.birthday!=null)
                r = -o.birthday.compareTo(this.birthday);
        }
        return r;
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday.toString() +
                '}';
    }
}

