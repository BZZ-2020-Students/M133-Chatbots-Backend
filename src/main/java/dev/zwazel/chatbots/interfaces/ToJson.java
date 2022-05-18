package dev.zwazel.chatbots.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ToJson {
    String toJson() throws JsonProcessingException;
}
