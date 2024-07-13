package br.com.alura.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

/**
 * Interface for converting JSON data to Java objects.
 */

@Service
public interface IDataConverter {
    /**
     * Converts a JSON string to a Java object of the specified class.
     *
     * @param json  the JSON string to convert
     * @param clazz the class of the object to convert to
     * @return the converted object
     * @throws JsonProcessingException if the JSON data cannot be parsed
     */
    <T> T getData(String json, Class<T> clazz) throws JsonProcessingException;
}