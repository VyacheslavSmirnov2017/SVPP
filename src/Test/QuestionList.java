package Test;

import Test.Question;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class QuestionList {
    private List<Question> list = new ArrayList<>();
    private String filePath;

    public QuestionList(String filePath) {
        this.filePath = filePath;
        generateList();
    }

    private void generateList()
    {
        List<String> answers;
        String task;
        String rightAnswer;
        try {
            File fXmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Question");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    answers = new ArrayList<>();
                    task = eElement.getElementsByTagName("task").item(0).getTextContent();
                    answers.add(eElement.getElementsByTagName("answer1").item(0).getTextContent());
                    answers.add(eElement.getElementsByTagName("answer2").item(0).getTextContent());
                    answers.add(eElement.getElementsByTagName("answer3").item(0).getTextContent());
                    answers.add(eElement.getElementsByTagName("answer4").item(0).getTextContent());
                    Collections.shuffle(answers);
                    rightAnswer = eElement.getElementsByTagName("rightAnswer").item(0).getTextContent();
                    list.add(new Question(task, answers, rightAnswer));
                }
            }

            Collections.shuffle(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int size() {
        return list.size();
    }

    public boolean add(Question question) {
        return list.add(question);
    }

    public Question get(int index) {
        return list.get(index);
    }

    public Question remove(int index) {
        return list.remove(index);
    }
}
