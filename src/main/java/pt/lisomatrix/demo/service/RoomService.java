package pt.lisomatrix.demo.service;

import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;
import pt.lisomatrix.demo.model.Room;
import reactor.core.publisher.Mono;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    private final ReactiveRedisTemplate<String, Room> redisTemplate;
    private final ReactiveValueOperations<String, Room> reactiveValueOps;


    public RoomService(ReactiveRedisTemplate<String, Room> redisTemplate) {
        this.redisTemplate = redisTemplate;
        reactiveValueOps = redisTemplate.opsForValue();
    }

    @PreDestroy
    public void reset() {
        redisTemplate.keys("*")
                .filter(x -> x.startsWith("ROOM"))
                .flatMap(redisTemplate::delete)
                .subscribe();
    }

    public Mono<Room> create(String name, String participantId, int id) {
        Room newRoom = new Room(id, name, new ArrayList<>(), false, participantId);
        newRoom.getParticipants().add(participantId);
        Mono<Room> roomMono = Mono.just(newRoom);

        return reactiveValueOps.set("ROOM-" + id, newRoom)
                .flatMap(saved -> roomMono);
    }

    public Mono<List<Room>> getRooms() {
        return redisTemplate.keys("*")
                .filter(x -> x.startsWith("ROOM-"))
                .flatMap(reactiveValueOps::get)
                .filter(room -> !room.isHasStarted())
                .doOnError(Throwable::printStackTrace)
                .collectList();

    }

    public Mono<Room> findById(int id) {
        return reactiveValueOps.get("ROOM-" + id);
    }

    public Mono<Room> start(int id) {
        return redisTemplate.hasKey("ROOM-" + id)
                .flatMap(exists -> exists ? reactiveValueOps.get("ROOM-" + id) : Mono.empty())
                .map(room -> {
                    room.setHasStarted(true);
                    return room;
                })
                .doOnNext(this::insert);
    }

    public Mono<Room> joinParticipant(int id, String participantId) {
        return redisTemplate.hasKey("ROOM-" + id)
                .flatMap(exists -> exists ? reactiveValueOps.get("ROOM-" + id) : Mono.empty())
                .map(room -> {
                    room.getParticipants().add(participantId);
                    return room;
                })
                .doOnNext(this::insert);
    }

    public void removeParticipant(int id, String participantId) {
        findById(id)
                .map(room -> {
                    room.getParticipants().remove(participantId);
                    return room;
                })
                .subscribe(this::insert);
    }

    public void insert(Room room) {
        reactiveValueOps.set("ROOM-" + room.getId(), room)
                .subscribe(saved -> System.out.println("Room " + room.getName() + " was updated!"));
    }

    public void delete(int id) {
        reactiveValueOps.delete("ROOM-" + id).subscribe();
    }
}
