package entity;

public class Main {
    public static void main(String[] args) throws Exception {
        User user = new User();
        user.setId(2);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@doe.com");

        UserValidator validator = new UserValidator();
        validator.validate(user);
    }
}
