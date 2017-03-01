package edu.umdearborn.astronomyapp.util.json;

import java.io.IOException;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class AnswerValueSeralizer extends JsonSerializer<String> {

  private static final Pattern NUMERIC_PATTERN = Pattern.compile("^#-?\\d+\\.?\\d*((e|E)-?\\d+)?&[\\s\\S]+$");

  @Override
  public void serialize(String value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException, JsonProcessingException {
    if (value != null) {
      gen.writeStartObject();
      if (NUMERIC_PATTERN.matcher(value).matches()) {
        String[] parts = value.split("&");
        parts[0] = parts[0].substring(1);   // Remove proceeding #
        gen.writeStringField("answer", parts[0]);
        gen.writeStringField("unit", parts[1]);
      } else {
        gen.writeStringField("answer", value);
      }
      gen.writeEndObject();
    }
  }

}
