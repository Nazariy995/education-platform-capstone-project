package edu.umdearborn.astronomyapp.config.sanitiser;

import java.io.IOException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.NonTypedScalarSerializerBase;

public class SanatizedStringSeralizer extends NonTypedScalarSerializerBase<String> {

  private static final long          serialVersionUID = -4084762863288732797L;
  private static final PolicyFactory POLICY_FACTORY   = Sanitizers.BLOCKS.and(Sanitizers.FORMATTING)
      .and(Sanitizers.IMAGES).and(Sanitizers.LINKS).and(Sanitizers.STYLES);

  public SanatizedStringSeralizer() {
    super(String.class);
  }

  @Override
  public void serialize(String value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {

    gen.writeString(StringEscapeUtils.unescapeHtml3(POLICY_FACTORY.sanitize(value)));
  }
}
