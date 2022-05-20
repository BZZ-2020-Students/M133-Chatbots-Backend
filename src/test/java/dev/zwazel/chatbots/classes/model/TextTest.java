package dev.zwazel.chatbots.classes.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TextTest {
    @Test
    public void testTextNeeded() {
        assertThrows(NullPointerException.class,
                () -> Text.builder()
                        .build()
        );
    }

    @Test
    public void testSuccessfulBuild() {
        Text text = Text.builder().text("text").build();

        assertEquals(0, text.getAmountUsed());
        assertEquals("text", text.getText());
    }

    @Test
    public void testSuccessfulBuildWithAmountUsed() {
        Text text = Text.builder().text("text").amountUsed(1).build();

        assertEquals(1, text.getAmountUsed());
        assertEquals("text", text.getText());
    }

    @Test
    public void testNoArgConstructor() {
        Text text = new Text();

        assertEquals(0, text.getAmountUsed());
        assertNull(text.getText());
    }
}