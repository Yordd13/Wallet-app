package app.subscription.model;

import app.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionType type;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @OneToOne(mappedBy = "subscription")
    private User user;
}