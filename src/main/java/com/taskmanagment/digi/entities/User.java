package com.taskmanagment.digi.entities;
/**
 * if we are thinking of this Project as a layers , User class is considered the deepest layer
 * It is the layer connected with the database
 * name it Step 1 in the building road
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data  //having implicit @Getter, @Setter, @ToString, @EqualsAndHashCode
@Entity
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Table(name = "USERS")
public class User {
    private Integer userId;
    private String username;
    private String email;

    @Id
    @GeneratedValue(generator = "userIdGen",strategy =GenerationType.AUTO )
    @Column(name = "USER_ID")
    public Integer getUserId() {
        return userId;
    }

    @Column(name = "USER_NAME")
    public String getUsername() {
        return username;
    }

    @Column(name = "USER_EMAIL")
    public String getEmail() {
        return email;
    }


}
