package com.tass.userservice.entities;

import com.tass.common.model.base.BaseEntity;
import com.tass.common.model.myenums.UserStatus;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(columnDefinition = "text")
    private String avatar;
    private String fullName;
    private String email;
    private String gender;
    private String phone;
    @Column(columnDefinition = "text")
    private String address;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = EAGER)
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private Role role;

    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;

}
