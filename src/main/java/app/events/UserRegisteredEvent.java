package app.events;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserRegisteredEvent {
    private UUID userId;

    public UserRegisteredEvent(UUID userId) {
        this.userId = userId;
    }
}
