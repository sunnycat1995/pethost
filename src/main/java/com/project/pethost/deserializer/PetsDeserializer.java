package com.project.pethost.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.project.pethost.dbo.PetDbo;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PetsDeserializer extends StdDeserializer<Set<PetDbo>> {

    public PetsDeserializer() {
        this(null);
    }

    public PetsDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Set<PetDbo> deserialize(
            final JsonParser jsonparser,
            final DeserializationContext context)
            throws IOException, JsonProcessingException {
        final JsonNode node = jsonparser.getCodec().readTree(jsonparser);
        final Iterator<JsonNode> arrayNodeIterator = node.elements();

        final Set<PetDbo> result = new HashSet<>();

        while (arrayNodeIterator.hasNext()) {
            final Long id = (Long) (node.get("id")).numberValue();
            final String name = node.get("name").asText();
            final PetDbo petDbo = new PetDbo();
            result.add(petDbo);
            //final LocalDate userId =(node.get("birthdate")).asText();
        }

        return result;

    }
}
