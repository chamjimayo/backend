package com.project.chamjimayo.repository.domain.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "restroom_manager")
@Getter
@ToString(exclude = "managerId")
@NoArgsConstructor
public class RestroomManager extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "manager_id")
  private Long managerId;

  // 화장실 아이디 (어느 화장실을 관리하는지)
  @OneToMany(mappedBy = "restroomManager")
  private List<Restroom> restrooms;

  // 전화번호
  @Column(name = "phone_number")
  private String phoneNumber;

  // 이름
  @Column(name = "name")
  private String name;

  // 이메일
  @Column(name = "email")
  private String email;
}
