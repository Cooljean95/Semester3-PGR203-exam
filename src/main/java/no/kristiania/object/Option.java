package no.kristiania.object;

public class Option {

    // These are fields that takes in the information as described
    private long idOption;
    private int idQuestion;
    private String option;

    // These are setters and getters that holds the information and returns it.
    public long getIdOption() {
        return idOption;
    }

    public void setIdOption(long idOption) {
        this.idOption = idOption;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    //This makes a string that holds the information in this object class
    @Override
    public String toString() {
        return "Option{" +
                "idOption=" + idOption +
                ", idQuestion=" + idQuestion +
                ", option='" + option + '\'' +
                '}';
    }
}
