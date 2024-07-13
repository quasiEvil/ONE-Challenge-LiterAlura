package br.com.alura.literalura.service;

import br.com.alura.literalura.model.AuthorDTO;
import br.com.alura.literalura.model.BookDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for converting JSON data to Java objects.
 */
@Service
public class DataConverter implements IDataConverter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converts a JSON string to a Java object of the specified class.
     *
     * @param json the JSON string to be converted
     * @param clazz the class of the object to be created
     * @return an instance of the specified class, populated with data from the JSON string
     * @throws JsonProcessingException if there is an error parsing the JSON string
     *
     * Example:
     * ```java
     * String json = "{\"title\":\"Book Title\",\"author\":{\"name\":\"Author Name\"}}";
     * BookDTO book = dataConverter.getData(json, BookDTO.class);
     * ```
     */
    @Override
    public <T> T getData(String json, Class<T> clazz) throws JsonProcessingException {
        JsonNode node = objectMapper.readTree(json);
        JsonNode dataNode = getNode(node, clazz);

        if (dataNode!= null) {
            return objectMapper.treeToValue(dataNode, clazz);
        } else {
            throw new JsonMappingException("Erro ao parse JSON data para: " + clazz.getSimpleName());
        }
    }

    /**
     * Retrieves a JsonNode from the given node, based on the specified class.
     *
     * @param node the JsonNode to search
     * @param clazz the class to determine the node path
     * @return the JsonNode corresponding to the specified class, or null if not found
     */
    private JsonNode getNode(JsonNode node, Class<?> clazz) {
        if (clazz == BookDTO.class) {
            return getNode(node, "results", 0);
        } else if (clazz == AuthorDTO.class) {
            JsonNode resultsNode = getNode(node, "results", 0);
            return resultsNode!= null? getNode(resultsNode, "authors", 0) : null;
        } else {
            return null;
        }
    }

    /**
     * Retrieves a JsonNode from the given node, based on the specified field name and index.
     *
     * @param node the JsonNode to search
     * @param fieldName the name of the field to search for
     * @param index the index of the field to retrieve
     * @return the JsonNode corresponding to the specified field and index, or null if not found
     */
    private JsonNode getNode(JsonNode node, String fieldName, int index) {
        JsonNode fieldNode = node.get(fieldName);
        return fieldNode!= null && fieldNode.size() > index? fieldNode.get(index) : null;
    }
}