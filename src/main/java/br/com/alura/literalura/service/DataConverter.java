package br.com.alura.literalura.service;

import br.com.alura.literalura.model.AuthorDTO;
import br.com.alura.literalura.model.BookDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class DataConverter implements IDataConverter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> clazz) throws JsonProcessingException {
        JsonNode node = objectMapper.readTree(json);
        JsonNode dataNode = node.get("results").get(0);

        if (clazz == BookDTO.class) {
            if (dataNode != null) {
                return objectMapper.treeToValue(dataNode, clazz);
            }
        } else if (clazz == AuthorDTO.class) {
            JsonNode authorsNode = dataNode.get("authors").get(0);
            if (authorsNode != null) {
                return objectMapper.treeToValue(authorsNode, clazz);
            }
        }
        return objectMapper.readValue(json, clazz);
    }
}
