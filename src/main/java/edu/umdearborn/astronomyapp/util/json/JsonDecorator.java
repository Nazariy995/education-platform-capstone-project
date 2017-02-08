package edu.umdearborn.astronomyapp.util.json;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * @author Patrick Bremer
 *
 */
@JsonInclude(Include.NON_NULL)
public class JsonDecorator<T> {

  @JsonUnwrapped
  private T payload;

  private Map<String, Object> properties = new HashMap<>();

  public Object addProperty(String key, Object value) {
    return properties.put(key, value);
  }

  public T getPayload() {
    return payload;
  }

  @JsonAnyGetter
  public Map<String, Object> getProperties() {
    return properties;
  }

  public void setPayload(T payload) {
    this.payload = payload;
  }

  public void setProperties(Map<String, Object> properties) {
    this.properties = properties;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("JsonDecorator [payload=").append(payload).append(", properties=")
        .append(properties).append("]");
    return builder.toString();
  }

}
