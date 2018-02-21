package org.mcast.dc.model;

/**
 * Created by Darren on 04/12/2016.
 */

public class User {
    private String alias;
    private String email;
    private Integer age;
    private Boolean isMale;
    private Boolean isRightHanded;

    public User(String alias, String email, int age, boolean isMale, boolean isRightHanded) {
        super();
        this.alias = alias;
        this.email = email;
        this.age = age;
        this.isMale = isMale;
        this.isRightHanded = isRightHanded;
    }

    public String getAlias() {
        return alias;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() { return age; }

    public boolean getIsMale() { return isMale; }

    public boolean getIsRightHanded() { return isRightHanded; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (age != user.age) return false;
        if (!alias.equals(user.alias)) return false;
        if (!email.equals(user.email)) return false;
        if (!isMale.equals(user.isMale)) return false;
        return isRightHanded.equals(user.isRightHanded);

    }

    @Override
    public int hashCode() {
        int result = alias.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + age;
        result = 31 * result + isMale.hashCode();
        result = 31 * result + isRightHanded.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "org.mcast.dc.model.User{" +
                "alias='" + alias + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", isMale=" + isMale +
                ", isRightHanded=" + isRightHanded +
                '}';
    }
}
