package ee.mihkel.webshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private String personalCode;
    private String phone;
    private PersonRole role;

    // @ManyToOne
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

//    @CreationTimestamp läbi Hibernate-i (andmebaas annab ajatempli)
    @CreatedDate
    private LocalDateTime creationDate;

//    @UpdateTimestamp
    @LastModifiedDate
    private LocalDateTime updatedDate;
}
