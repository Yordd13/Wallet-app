package app.subscription.service;

import app.subscription.model.Subscription;
import app.subscription.model.SubscriptionType;
import app.subscription.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }


    @Transactional
    public Subscription makeDefaultSubscription() {
        Subscription defaultSub = Subscription.builder()
                .type(SubscriptionType.FREE)
                .price(BigDecimal.ZERO)
                .startDateTime(LocalDateTime.now())
                .build();
        subscriptionRepository.save(defaultSub);
        return defaultSub;
    }

}
