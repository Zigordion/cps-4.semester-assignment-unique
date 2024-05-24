package cps.BusinessLogic;

import cps.Util.Data.SSETopic;
import jakarta.annotation.PreDestroy;
import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
class SseService {

    private final Map<SSETopic, Queue<SseEmitter>> topicEmittersMap = new ConcurrentHashMap<>();

    void addEmitter(SSETopic topic, SseEmitter emitter) {
        topicEmittersMap
                .computeIfAbsent(topic, k -> new ConcurrentLinkedQueue<>())
                .add(emitter);
        emitter.onCompletion(() -> removeEmitter(topic, emitter));
        emitter.onTimeout(() -> removeEmitter(topic, emitter));
    }

    <T> void sendToClients(SSETopic topic, T data) {
        Queue<SseEmitter> emitters = topicEmittersMap.getOrDefault(topic, new ConcurrentLinkedQueue<>());
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(data);
            } catch (IOException e) {
                emitter.complete();
                removeEmitter(topic, emitter);
            }
        }
    }

    private void removeEmitter(SSETopic topic, SseEmitter emitter) {
        Queue<SseEmitter> emitters = topicEmittersMap.get(topic);
        if (emitters != null) {
            emitters.remove(emitter);
            if (emitters.isEmpty()) {
                topicEmittersMap.remove(topic);
            }
        }
    }

    @PreDestroy
    void cleanUp() {
        // Complete and remove all emitters
        for (Queue<SseEmitter> emitters : topicEmittersMap.values()) {
            for (SseEmitter emitter : emitters) {
                emitter.complete();
            }
        }
        topicEmittersMap.clear();
    }
}
