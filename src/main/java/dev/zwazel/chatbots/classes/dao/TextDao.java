package dev.zwazel.chatbots.classes.dao;

import dev.zwazel.chatbots.classes.model.Text;

/**
 * DAO for Text objects.
 *
 * @author Zwazel
 * @since 0.3
 */
public class TextDao extends Dao<Text, String> {
    /**
     * Default constructor.
     *
     * @since 0.3
     */
    public TextDao() {
        super(Text.class);
    }
}
