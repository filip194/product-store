package com.demo.productstore.security.user.db;

import com.demo.productstore.security.UserType;
import com.demo.productstore.security.role.db.RoleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "product", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "username"}, name = "user_id_and_username_unique")
})
@Data
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_users_generator")
    @SequenceGenerator(name = "pk_users_generator", sequenceName = "pk_users_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "external_id")
    private UUID externalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private UserType type;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private Integer age;

    @Column(name = "created")
    @CreationTimestamp
    private Timestamp created;

    @Column(name = "updated")
    @UpdateTimestamp
    private Timestamp updated;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "user_roles", schema = "product",
            joinColumns = @JoinColumn(name = "user_entity_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "user_entity_id_fk_user_id")),
            inverseJoinColumns = @JoinColumn(name = "role_entity_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "role_entity_id_fk_role_id")))
    private Collection<RoleEntity> roles = new HashSet<>();

}
