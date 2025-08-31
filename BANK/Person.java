import java.util.Date;

public class Person {
    private String name;
    private String family;
    private Date birthDate;
    private String nationalCode;
    private String phone;
    private String address;

    public Person(String name, String family, Date birthDate,
                  String nationalCode, String phone, String address) {
        this.name = name;
        this.family = family;
        this.birthDate = birthDate;
        this.nationalCode = nationalCode;
        this.phone = phone;
        this.address = address;
    }

    // --------- Getter ها ---------
    public String getName() { return name; }
    public String getFamily() { return family; }
    public Date getBirthDate() { return birthDate; }
    public String getNationalCode() { return nationalCode; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    // --------- Setter ها ---------
    public void setName(String name) { this.name = name; }
    public void setFamily(String family) { this.family = family; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
    public void setNationalCode(String nationalCode) { this.nationalCode = nationalCode; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return name + " " + family + " | کد ملی: " + nationalCode +
                " | تولد: " + birthDate + " | تلفن: " + phone;
    }
}
