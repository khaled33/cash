package com.enda.cash.log;

import com.enda.cash.UserDetails.Dao.Entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String message;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private AppUser user;
    private LocalDateTime dateAction;
}
