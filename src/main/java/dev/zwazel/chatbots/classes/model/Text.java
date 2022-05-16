package dev.zwazel.chatbots.classes.model;

import lombok.*;

/**
 * Text class
 *
 * @author Zwazel
 * @since 0.2
 */
@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
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
    @NonNull
    private String text;

    /**
     * How often this exact text has been used
     *
     * @since 0.2
     */
    @Builder.Default
    private Integer amountUsed = 0;
}
