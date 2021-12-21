package dev.ifrs;

public class NextActionState {
    private String title;
    private Boolean completed = Boolean.FALSE;

    public NextActionState(final String title,
            final Boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
