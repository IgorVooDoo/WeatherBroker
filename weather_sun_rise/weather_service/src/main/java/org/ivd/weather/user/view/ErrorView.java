package org.ivd.weather.user.view;

/**
 * Представление ошибки
 */
public class ErrorView {
    /**
     * Описание ошибки
     */
    private String text;

    public ErrorView(String text) {
        this.text = text;
    }

    public ErrorView() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
