package app.events;

import app.exception.DomainException;
import app.subscription.model.Subscription;
import app.subscription.service.SubscriptionService;
import app.user.model.User;
import app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class SubscriptionEventListener {
    private final UserRepository userRepository;
    private final SubscriptionService subscriptionService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleUserRegistered(UserRegisteredEvent event) {

        User user = userRepository.findById(event.getUserId())
                .orElseThrow(() -> new DomainException("User not found"));
        Subscription defaultSubscription = subscriptionService.makeDefaultSubscription();
        user.setSubscription(defaultSubscription);
        defaultSubscription.setUser(user);
        userRepository.save(user);

    }
}
