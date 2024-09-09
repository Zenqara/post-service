package dkcorp.post_service.event;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EventTypeTest {
    @Test
    void eventType_shouldContainAllValues() {
        assertEquals(4, EventType.values().length, "Enum should have 4 values");

        assertEquals(EventType.CREATED, EventType.valueOf("CREATED"));
        assertEquals(EventType.READ, EventType.valueOf("READ"));
        assertEquals(EventType.UPDATED, EventType.valueOf("UPDATED"));
        assertEquals(EventType.DELETED, EventType.valueOf("DELETED"));
    }

    @Test
    void eventType_valuesShouldNotBeNull() {
        for (EventType eventType : EventType.values()) {
            assertNotNull(eventType, "Enum value should not be null");
        }
    }
}
