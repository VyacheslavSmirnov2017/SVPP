package Result;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Result {

    private static Result instance;
    private StringProperty studentID;
    private StringProperty testName;
    private double result;
    private StringProperty date;

    public Result(String studentID, String testName, double result, String date) {
        this.studentID = new SimpleStringProperty(studentID);
        this.testName = new SimpleStringProperty(testName);
        this.result = result;
        this.date = new SimpleStringProperty(date);
    }


    @Override
    public String toString() {
        return "Result{" +
                "studentID='" + String.valueOf(studentID) + '\'' +
                ", testName='" + String.valueOf(testName) + '\'' +
                ", result=" + result +
                ", date='" + String.valueOf(date) + '\'' +
                '}';
    }

    private Result() {
        this.studentID = new SimpleStringProperty("");
        this.testName = new SimpleStringProperty("");
        this.result = 0;
        this.date = new SimpleStringProperty("");
    }

    public void setStudentID(String studentID) {
        this.studentID.set(studentID);
    }

    public void setTestName(String testName) {
        this.testName.set(testName);
    }

    public static Result getInstance() {
        if(instance == null)
        {
            instance = new Result();
        }
        return instance;

    }

    public String getStudentID() {
        return studentID.get();
    }

    public void setResult(double result) {
        this.result = result;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public StringProperty studentIDProperty() {
        return studentID;
    }

    public String getTestName() {
        return testName.get();
    }

    public StringProperty testNameProperty() {
        return testName;
    }

    public double getResult() {
        return result;
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }
}
