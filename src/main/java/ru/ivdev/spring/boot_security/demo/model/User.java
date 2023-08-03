package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

/*
UserDetails - обертка над сущностью.
Не используется непосредственно Spring Security в целях безопасности.
Хранит информацию о пользователе, которая позже инкапсулируется в объекты Authentication.

Entity Graphs помогает визуализировать отношения между указанным объектом и всеми объектами,
связанными с этим объектом, на основе общих атрибутов.
Another way to describe a query in JPA 2.1.
*/


//связь по полю roles класса User
@Entity
@Table(name = "users")
@NamedEntityGraph(name = "User.roles",
        attributeNodes = @NamedAttributeNode("roles"))
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    private String username;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "age")
    private long age;
    @Column(name = "pass")
    private String password;


    //связь с таблицей ролей
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rolesOfUsers",
            joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "id"))
    //для множества ролей используем hashSet
    //переопределим hashCode для сущности
    private Set<Role> roles = new LinkedHashSet<>();


    public User() {}

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String firstName, String lastName, long age, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.username = username;
        this.password = password;
    }

    public User(String firstName, String lastName, long age, String username, String password, Role... role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.username = username;
        this.password = password;
        this.roles = Set.of(role);
    }

    //методы интерфейса UserDetails
    ////коллекция прав для конкретного юзера для доступа к опр. страницам
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       // return Collections.singletonList(new SimpleGrantedAuthority(User.getRoles()));
        return this.roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    //методы User
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(id, user.id)) return false;
        if (!Objects.equals(firstName, user.firstName)) return false;
        if (!Objects.equals(lastName, user.lastName)) return false;
        if (!Objects.equals(age, user.age)) return false;
        if (!Objects.equals(username, user.username)) return false;
        if (!Objects.equals(password, user.password)) return false;
        return Objects.equals(roles, user.roles);
    }

    //stackoverflow.com/questions/2827612/bit-shifting-in-effective-java-hashcode-implementation
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = id != null ? id.hashCode() : 0;
        result = prime * result + (username != null ? username.hashCode() : 0);
        result = prime * result + (firstName != null ? firstName.hashCode() : 0);
        result = prime * result + (lastName != null ? lastName.hashCode() : 0);
        result = prime * result + (int) (age ^ (age >>> 32));
        result = prime * result + (password != null ? password.hashCode() : 0);
        result = prime * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }
}

