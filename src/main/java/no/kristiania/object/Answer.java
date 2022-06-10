package no.kristiania.object;

public class Answer {

    // These are fields that takes in the information as described
    private Long idAnswer;
    private int questionId;
    private String answer;

    // These are setters and getters that holds the information and returns it.
    public Long getIdAnswer() {
        return idAnswer;
    }

    public void setIdQuestion(Long idAnswer) {
        this.idAnswer = idAnswer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    //This makes a string that holds the information in this object class
    @Override
    public String toString() {
        return "Answer{" +
                "Id_answer=" + idAnswer +
                ", question_Id=" + questionId +
                ", answer='" + answer + '\'' +
                '}';
    }
}
