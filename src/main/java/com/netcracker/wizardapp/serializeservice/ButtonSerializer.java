package com.netcracker.wizardapp.serializeservice;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.netcracker.wizardapp.domain.Button;
import com.netcracker.wizardapp.domain.Page;

import java.io.IOException;

public class ButtonSerializer extends StdSerializer<Button> {
    public ButtonSerializer() {
        this(null);
    }

    public ButtonSerializer(Class<Button> t) {
        super(t);
    }

    @Override
    public void serialize(
            Button value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("name", value.getName());
        jgen.writeEndObject();
    }
}
