package Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Question {
    private String task;
    private List<String> answers;
    private String rightAnswer;

    Question(String task, List<String> answers, String rightAnswer) {
        this.task = task;
        this.answers = answers;
        this.rightAnswer = rightAnswer;
    }

    String getTask() {
        return task;
    }

    List<String> getAnswers() {
        return answers;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "task='" + task + '\'' +
                ", answers=" + answers +
                ", rightAnswer='" + rightAnswer + '\'' +
                '}';
    }
}
