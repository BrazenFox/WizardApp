package com.netcracker.wizardapp.serializeservice;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.netcracker.wizardapp.domain.Wizard;
import com.netcracker.wizardapp.domain.User;

import java.io.IOException;

public class WizardSerializer extends StdSerializer<Wizard> {
    public WizardSerializer(){
        this(null);
    }
    public WizardSerializer(Class<Wizard> t) {
        super(t);
    }

    @Override
    public void serialize(Wizard value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("name", value.getName());
        jgen.writeObjectField("creator",new User(value.getCreator().getUsername(), ""));
        //jgen.writeStringField("creator", value.getCreator().getUsername());
        jgen.writeEndObject();
    }

}
