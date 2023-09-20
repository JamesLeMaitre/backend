package bj.gouv.sineb.choreography.util;

import reactor.kafka.receiver.ReceiverOffset;

public record Record<T>(String key,
                        T message,
                        ReceiverOffset acknowledgement) {
}
