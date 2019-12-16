package pt.lisomatrix.demo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;

import pt.lisomatrix.demo.model.Participant;
import pt.lisomatrix.demo.model.TopScore;
import reactor.core.publisher.Flux;

import javax.annotation.PreDestroy;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.*;

@Service
public class TopScoreService {

    private final ReactiveRedisTemplate<String, TopScore> redisTemplate;
    private ReactiveValueOperations<String, TopScore> reactiveValueOps;

    private final ParticipantService participantService;

    private List<TopScore> topScoresList = new ArrayList<>();

    private Logger logger = LoggerFactory.getLogger(TopScoreService.class);

    public TopScoreService(ReactiveRedisTemplate<String, TopScore> redisTemplate, ParticipantService participantService) {
        this.redisTemplate = redisTemplate;
        this.participantService = participantService;
        this.reactiveValueOps = redisTemplate.opsForValue();

        redisTemplate.keys("*")
                .filter(x -> x.startsWith("T_"))
                .flatMap(reactiveValueOps::get)
                .doOnError(Throwable::printStackTrace)
                .collectList()
                .subscribe(topScores -> topScoresList = topScores);
    }

    private void sortTopScores() {
        topScoresList.sort((o1, o2) -> Float.compare(o2.getScore(), o1.getScore()));
    }

    public void addTopScore(String id, float score) {
        participantService.get(id).subscribe(participant -> {
            int position = isTopScoreAlready(participant.getName());

            if (position != -1) {
                if (topScoresList.get(position).getScore() < score) {
                    topScoresList.remove(position);
                    topScoresList.add(new TopScore(participant.getName(), (int) score));
                    sortTopScores();
                }
            } else {
                topScoresList.add(new TopScore(participant.getName(), (int) score));
                sortTopScores();
            }

            if (topScoresList.size() > 9) {
                topScoresList = topScoresList.subList(0, 10);
            }
        });

        saveToRedis();
    }

    public List<TopScore> getTopScores() {
        return topScoresList;
    }

    @PreDestroy
    public void saveToRedis() {
        Flux.fromIterable(topScoresList)
                .subscribe(score -> reactiveValueOps.set("T_" + score.getName(), score));
    }

    private int isTopScoreAlready(String username) {
        for (int i = 0; i < topScoresList.size() - 1; i++) {
            if (topScoresList.get(i).getName().equals(username)) {
                return i;
            }
        }

        return -1;
    }
}
