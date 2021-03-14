public class Validation {

    private String sourceString; //input

    public Validation(String sourceString) {
        this.sourceString = sourceString;
    }

    public String formatting() {
        sourceString = sourceString.replace(" ", "")
                .replace("(-", "(0-")
                .replace(",-", ",0-");

        return sourceString;
    }
}
