package com.netcracker.wizardapp.serializeservice;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.netcracker.wizardapp.domain.Page;

import java.io.IOException;

public class ToPageSerializer extends StdSerializer<Page> {

    public ToPageSerializer() {
        this(null);
    }

    public ToPageSerializer(Class<Page> t) {
        super(t);
    }

    @Override
    public void serialize(
            Page value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("name", value.getName());
        jgen.writeEndObject();
    }
}
