package BANK;
import java.util.Date;
import java.util.regex.*;
public abstract class Person implements Message{
    String name = "";
    String lastName = "";
    String nationalCode = "";
    MyDate birthDate;
    String address = "";
    String phone = "";
    public Person() {
    }
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
    //get methods
    public String getName() {
        return name;
    }
    public String getLastName() {
        return lastName;
    }
    public MyDate getBirthDate() {
        return birthDate;
    }
    public String getNationalCode() {
        return nationalCode;
    }
    public String getAddress() {
        return address;
    }
    public String getPhone() {
        return phone;
    }
    //set methods

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthDate(MyDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public void setName(String name) {
        this.name = name;
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
    public static String decorator(String str){
        return "\n----------\n"+str+"\n----------\n";
    }

    /**
     * this function show all information of person
     * @return a string with all information
     */
    public String showAllInformation(){
        return  "\nname : " + name +
                "\nlast name : " + lastName+
                "\nnational code : "+ nationalCode+
                "\naddress : " + address+
                "\nphone : " + phone+
                "\nbirthday : " + birthDate;
    }
}
