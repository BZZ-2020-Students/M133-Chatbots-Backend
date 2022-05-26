package dev.zwazel.chatbots.classes.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Text.
 *
 * @author Zwazel
 * @since 1.1.0
 */
public class TextTest {
    /**
     * Tests if a NullPointerException is thrown when the text is not specified.
     *
     * @author Zwazel
     * @since 1.1.0
     */
    @Test
    public void testTextNeeded() {
        assertThrows(NullPointerException.class,
                () -> Text.builder()
                        .build()
        );
    }

    /**
     * Tests if the Text is built correctly without an amount used specified.
     *
     * @author Zwazel
     * @since 1.1.0
     */
    @Test
    public void testSuccessfulBuild() {
        Text text = Text.builder().text("text").build();

        assertEquals(0, text.getAmountUsed());
        assertEquals("text", text.getText());
    }

    /**
     * Tests if the Text is built correctly with an amount used specified.
     *
     * @author Zwazel
     * @since 1.1.0
     */
    @Test
    public void testSuccessfulBuildWithAmountUsed() {
        Text text = Text.builder().text("text").amountUsed(1).build();

        assertEquals(1, text.getAmountUsed());
        assertEquals("text", text.getText());
    }

    /**
     * Tests if the no argument constructor works correctly.
     *
     * @author Zwazel
     * @since 1.1.0
     */
    @Test
    public void testNoArgConstructor() {
        Text text = new Text();

        assertEquals(0, text.getAmountUsed());
        assertNull(text.getText());
    }
}