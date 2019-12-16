package pt.lisomatrix.demo.service;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;
import pt.lisomatrix.demo.model.Participant;
import reactor.core.publisher.Mono;

@Service
public class ParticipantService {

    private final ReactiveRedisTemplate<String, Participant> redisTemplate;
    private final ReactiveValueOperations<String, Participant> reactiveValueOps;

    public ParticipantService(ReactiveRedisTemplate<String, Participant> redisTemplate) {
        this.redisTemplate = redisTemplate;
        reactiveValueOps = redisTemplate.opsForValue();
    }

    public Mono<Participant> get(String id) {
        return redisTemplate.hasKey("P_" + id)
                .flatMap(exists -> exists ? reactiveValueOps.get("P_" + id) : Mono.empty());
    }

    public Mono<Participant> create(String id, String name) {
        Participant participant = new Participant(id, name);

        Mono<Participant> participantMono = Mono.just(participant);

        return reactiveValueOps.set("P_" + participant.getId(), participant)
                .flatMap(saved -> participantMono);
    }

    public void remove(String id) {
        if (id != null) {
            reactiveValueOps.delete("P_" + id).subscribe(deleted -> System.out.println("Participant with id " + id + " deleted"));
        }
    }
}
