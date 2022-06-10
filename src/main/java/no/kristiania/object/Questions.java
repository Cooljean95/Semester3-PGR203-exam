package no.kristiania.object;

public class Questions {

    // These are fields that takes in the information as described
    private Long IdQuestion;
    private String title;
    private String text;
    private String lowL;
    private String highL;

    // These are setters and getters that holds the information and returns it.
    public Long getIdQuestion() {
        return IdQuestion;
    }

    public void setIdQuestion(Long idQuestion) {
        IdQuestion = idQuestion;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLowL() {
        return lowL;
    }

    public void setLowL(String lowL) {
        this.lowL = lowL;
    }

    public String getHighL() {
        return highL;
    }

    public void setHighL(String highL) {
        this.highL = highL;
    }

    //This makes a string that holds the information in this object class
    @Override
    public String toString() {
        return "Questions{" +
                "IdQuestion=" + IdQuestion +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", lowL='" + lowL + '\'' +
                ", highL='" + highL + '\'' +
                '}';
    }
}
