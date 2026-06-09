package entity;

import annotations.*;

@GenerateValidator
public class User {
    private Integer id;

    @NotNull
    @Email
    private String email;

    @NotNull
    @MaxLength(50)
    @MinLength(2)
    private String firstName;

    @NotNull
    @MaxLength(50)
    @MinLength(2)
    private String lastName;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
