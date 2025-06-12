package BANK;
import java.util.Date;
import java.util.regex.*;
public abstract class Person {
    String name = "";
    String lastName = "";
    String nationalCode = "";
    MyDate birthDate;
    String address = "";
    String phone = "";
    public Person(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
        this.birthDate = new MyDate(2025,1,1);
    }
    public Person(String name, String lastName, MyDate birthDate,String nationalCode, String address, String phone) {
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.nationalCode = nationalCode;
        this.address = address;
        this.phone = phone;
    }
    public String validNameOrLastName(String name){
        for(int i = 0; i < name.length(); i++){
            if(!Character.isLetter(name.charAt(i))) throw new IllegalArgumentException("Name or last name contains invalid characters");
        }
        return name;
    }
    public MyDate validBirthDate(MyDate birthDate){
        return birthDate;
    }
    public String validPhoneOrNantionalCode(String phone){
        if (!phone.matches("\\d+")) throw new IllegalArgumentException("Phone number contains invalid characters");
        return phone;
    }
}
