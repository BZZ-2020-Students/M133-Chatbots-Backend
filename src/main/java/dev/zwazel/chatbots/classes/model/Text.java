package dev.zwazel.chatbots.classes.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Text class
 *
 * @author Zwazel
 * @since 0.2
 */
@Getter
@Setter
public class Text {
    /**
     * Text ID
     *
     * @since 0.2
     */
    private String id;

    /**
     * Text
     *
     * @since 0.2
     */
    private String text;

    /**
     * How often this exact text has been used
     *
     * @since 0.2
     */
    private Integer amountUsed;

    /**
     * default constructor
     *
     * @author Zwazel
     * @since 0.2
     */
    public Text() {
    }

    /**
     * Constructor with ID, text and amountUsed
     *
     * @param id         the ID
     * @param text       the text
     * @param amountUsed how often this text has been used already
     * @author Zwazel
     * @since 0.2
     */
    public Text(String id, String text, Integer amountUsed) {
        this.id = id;
        this.text = text;
        this.amountUsed = amountUsed;
    }

    /**
     * constructor without ID
     *
     * @param text       the text
     * @param amountUsed how often this text has been used already
     * @author Zwazel
     * @since 0.2
     */
    public Text(String text, Integer amountUsed) {
        this.text = text;
        this.amountUsed = amountUsed;
    }

    /**
     * Constructor with only the text, the amount used is set to 0
     *
     * @param text the text
     * @author Zwazel
     * @since 0.2
     */
    public Text(String text) {
        this.text = text;
        this.amountUsed = 0;
    }
}
