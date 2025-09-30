package com.demo.productstore.security.role.db;

import com.demo.productstore.security.user.db.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

@Entity
@Table(name = "roles", schema = "product", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"role_id", "name"}, name = "role_id_and_name_unique")
})
@Data
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_roles_generator")
    @SequenceGenerator(name = "pk_roles_generator", sequenceName = "pk_roles_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "external_id")
    private UUID externalId;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Collection<UserEntity> users = new HashSet<>();

    @Column(name = "created")
    @CreationTimestamp
    private Timestamp created;

    @Column(name = "updated")
    @UpdateTimestamp
    private Timestamp updated;

    @Column(name = "deleted")
    private Timestamp deleted;

}
