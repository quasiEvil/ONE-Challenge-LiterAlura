package br.com.alura.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface IDataConverter {
    <T> T getData(String json, Class<T> clazz) throws JsonProcessingException;
}
