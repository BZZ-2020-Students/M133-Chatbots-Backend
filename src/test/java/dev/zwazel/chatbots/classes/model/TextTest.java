package dev.zwazel.chatbots.classes.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextTest {
    @Test
    void testTextNeeded() {
        assertThrows(NullPointerException.class,
                () -> Text.builder()
                        .build()
        );
    }

    @Test
    void testSuccessfulBuild() {
        Text text = Text.builder().text("text").build();

        assertEquals(0, text.getAmountUsed());
        assertEquals("text", text.getText());
    }

    @Test
    void testSuccessfulBuildWithAmountUsed() {
        Text text = Text.builder().text("text").amountUsed(1).build();

        assertEquals(1, text.getAmountUsed());
        assertEquals("text", text.getText());
    }

    @Test
    void testNoArgConstructor() {
        Text text = new Text();

        assertEquals(0, text.getAmountUsed());
        assertEquals(null, text.getText());
    }
}