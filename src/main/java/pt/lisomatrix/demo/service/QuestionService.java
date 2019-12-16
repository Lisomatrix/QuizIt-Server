package pt.lisomatrix.demo.service;

import ch.qos.logback.core.util.FileUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.util.internal.ResourcesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import pt.lisomatrix.demo.model.Chapter;
import pt.lisomatrix.demo.model.Question;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class QuestionService {

    private List<Question> questions = new ArrayList<>();
    private Map<Integer, Chapter> chapters = new HashMap<>();

    public QuestionService() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            ClassPathResource resource = new ClassPathResource("questions.json");

            byte[] bdata = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String data = new String(bdata, StandardCharsets.UTF_8);

            questions = mapper.readValue(data, new TypeReference<List<Question>>() {});

            organizeQuestions(questions);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Optional<Question> getQuestionById(int id) {
        for (Question question : questions) {
            if (question.getId() == id) {
                return Optional.of(question);
            }
        }

        return Optional.empty();
    }

    private void organizeQuestions(List<Question> questions) {
        questions.forEach(question -> {
            if (chapters.containsKey(question.getChapter())) {
                chapters.get(question.getChapter()).getQuestions().add(question);
            } else {
                List<Question> questionsList = new ArrayList<>();
                questionsList.add(question);

                chapters.put(question.getChapter(), new Chapter(question.getChapter(), questionsList));
            }
        });
    }

    public Chapter getFirstChapter() {
        return chapters.get(1);
    }

    public Optional<Chapter> hasNextChapter(int id) {
        if (!chapters.containsKey(id + 1)) {
            return Optional.empty();
        }

        return Optional.of(chapters.get(++id));
    }

    public Optional<Chapter> getChapterById(int id) {
        if (!chapters.containsKey(id)) {
            return Optional.empty();
        }

        return Optional.of(chapters.get(id));
    }

    public Question getFirst() {
        return questions.get(0);
    }

    public Optional<Question> hasNext(int id) {
        if (id + 1 >= questions.size()) {
            return Optional.empty();
        }

        return Optional.of(questions.get(++id));
    }
}
