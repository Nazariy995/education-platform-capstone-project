package edu.umdearborn.astronomyapp.util.jsondecorator;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * @author Patrick Bremer
 *
 */
@JsonInclude(Include.NON_NULL)
public class JsonDecorator {

  @JsonUnwrapped
  private Object payload;

  @JsonUnwrapped
  private Map<Object, Object> properties = new HashMap<>();

  public Object getPayload() {
    return payload;
  }

  public void setPayload(Object payload) {
    this.payload = payload;
  }

  public Map<Object, Object> getProperties() {
    return properties;
  }

  public void setProperties(Map<Object, Object> properties) {
    this.properties = properties;
  }

  public Object addProperty(Object key, Object value) {
    return properties.put(key, value);
  }

}
