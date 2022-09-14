package com.develop.project_auth.domain.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "user_")
public class User {

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @CreationTimestamp
  @Column(nullable = false)
  private LocalDateTime creationDate;

  @ManyToMany
  @JoinTable(name = "user_group", joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "group_id"))
  private Set<Group> groups = new HashSet<>();

  public boolean passwordEquals(String password) {
    return getPassword().equals(password);
  }

  public boolean passwordNotEquals(String password) {
    return !passwordEquals(password);
  }

  public boolean removeGroup(Group group) {
    return getGroups().remove(group);
  }

  public boolean addGroup(Group group) {
    return getGroups().add(group);
  }

  public boolean isNew() {
    return getId() == null;
  }

}
