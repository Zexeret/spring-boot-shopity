package com.ecommerce.shopity.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Enumerated(EnumType.STRING) // Make the enum value to string rather that an integer
    private AppRole roleName;

    public Role(AppRole appRole) {
        this.roleName = appRole;
    }
}
