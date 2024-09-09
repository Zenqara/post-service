package dkcorp.post_service.publisher;

public interface MessagePublisher<T> {
    void publish(T event);
}
