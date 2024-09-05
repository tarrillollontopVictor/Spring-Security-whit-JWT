package com.apiUser.Entity;


import com.apiUser.Enum.PermissionEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "as_permission")
public class PermissionEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long idPermission;
   @Enumerated(EnumType.STRING)
   private PermissionEnum namePermission;


}
