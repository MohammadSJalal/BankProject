public abstract class Person {
    protected String name;
    protected String familyName;
    protected Date birthDate;
    protected String nationalCode;
    protected String phoneNumber;
    protected String address;

    public Person(String name, String familyName, Date birthDate, String nationalCode, String phoneNumber, String address) {
        if (!isValidNationalCode(nationalCode)) {
            throw new IllegalArgumentException("کد ملی باید 10 رقمی باشد.");
        }
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("شماره تلفن همراه باید 11 رقمی و با صفر شروع شود.");
        }

        this.name = name;
        this.familyName = familyName;
        this.birthDate = birthDate;
        this.nationalCode = nationalCode;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    private boolean isValidNationalCode(String code) {
        if (code == null || code.length() != 10) return false;
        for (char c : code.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    private boolean isValidPhoneNumber(String phone) {
        if (phone == null || phone.length() != 11) return false;
        if (phone.charAt(0) != '0') return false;
        for (char c : phone.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    public String getName() { return name; }
    public String getFamilyName() { return familyName; }
    public Date getBirthDate() { return birthDate; }
    public String getNationalCode() { return nationalCode; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }

    @Override
    public String toString() {
        return "Name: " + name + " " + familyName + "\n" +
                "Birth Date: " + birthDate + "\n" +
                "National Code: " + nationalCode + "\n" +
                "Phone: " + phoneNumber + "\n" +
                "Address: " + address;
    }
}
