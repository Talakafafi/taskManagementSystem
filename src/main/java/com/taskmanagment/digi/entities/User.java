package com.taskmanagment.digi.entities;
/**
 * if we are thinking of this Project as a layers , User class is considered the deepest layer
 * It is the layer connected with the database
 * name it Step 1 in the building road
 */

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;


@Data  //having implicit @Getter, @Setter, @ToString, @EqualsAndHashCode
@Entity
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Table(name = "USERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class , property = "userId")
public class User {

    @Id
    @GeneratedValue(generator = "userIdGen", strategy = GenerationType.SEQUENCE)//very efficient and allows Hibernate to decide when to perform the insert statement
    @Column(name = "USER_ID")                                                   //Auto which is the default  For most popular databases, it selects GenerationType.SEQUENCE
    private Long userId;

    @Column(name = "USER_USERNAME")
    private String username;

    @Column(name = "USER_EMAIL")
    private String email;

    @ManyToMany(mappedBy = "users" )
    private Set<Task> tasks;


    public User(String username, String email) {
        this.setUsername(username);
        this.setEmail(email);
    }
}
