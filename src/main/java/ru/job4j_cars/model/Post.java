package ru.job4j_cars.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.job4j_cars.repository.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "auto_post")
@Table(name = "auto_post")
@Data
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="description")
    private String description;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="created")
    private LocalDateTime created;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "price_id")
    private List<PriceHistory> messengers = new ArrayList<>();


}
