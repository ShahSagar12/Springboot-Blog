package com.rest.treeleaf.users.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.rest.treeleaf.users.entity.User;
import com.rest.treeleaf.users.entity.UserRole;

import java.io.IOException;
import java.util.stream.Collectors;

public class UserSerializer extends StdSerializer<User> {
    public UserSerializer() {
        this(null);
    }

    public UserSerializer(Class<User> userClass) {
        super(userClass);
    }

    @Override
    public void serialize(
            User user, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", user.getId());
        jgen.writeStringField("email", user.getEmail());
        jgen.writeStringField("firstName", user.getFirstName());
        jgen.writeStringField("lastName", user.getLastName());
        jgen.writeStringField("mobileNumber", user.getMobileNumber());
        jgen.writeObjectField("roles", user.getUserRoles().stream()
                .map(UserRole::getRole)
                .collect(Collectors.toList())
        );
    }
}
