package eu.chargetime.ocpp.model.securityext;

/*
   ChargeTime.eu - Java-OCA-OCPP

   MIT License

   Copyright (C) 2022 Mathias Oben <mathias.oben@enervalis.com>

   Permission is hereby granted, free of charge, to any person obtaining a copy
   of this software and associated documentation files (the "Software"), to deal
   in the Software without restriction, including without limitation the rights
   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
   copies of the Software, and to permit persons to whom the Software is
   furnished to do so, subject to the following conditions:

   The above copyright notice and this permission notice shall be included in all
   copies or substantial portions of the Software.

   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
   SOFTWARE.
*/

import eu.chargetime.ocpp.model.RequestWithId;
import eu.chargetime.ocpp.model.validation.StringMaxLengthValidationRule;
import eu.chargetime.ocpp.model.validation.Validator;
import eu.chargetime.ocpp.model.validation.ValidatorBuilder;
import eu.chargetime.ocpp.utilities.MoreObjects;
import java.time.ZonedDateTime;
import java.util.Objects;

public class SecurityEventNotificationRequest extends RequestWithId {

  private static final transient Validator typeValidator =
      new ValidatorBuilder()
          .addRule(new StringMaxLengthValidationRule(50))
          .setRequired(true)
          .build();

  private static final transient Validator techInfoValidator =
      new ValidatorBuilder().addRule(new StringMaxLengthValidationRule(255)).build();

  private String type;
  private ZonedDateTime timestamp;
  private String techInfo;

  /** Private default constructor for serialization purposes. */
  private SecurityEventNotificationRequest() {}

  /**
   * Handle required fields.
   *
   * @param type String. See {@link #setType(String)}
   * @param timestamp dateTime. See {@link #setTimestamp(ZonedDateTime)}
   */
  public SecurityEventNotificationRequest(String type, ZonedDateTime timestamp) {
    setType(type);
    setTimestamp(timestamp);
  }

  /**
   * Type of the security event.
   *
   * @return String
   */
  public String getType() {
    return type;
  }

  /**
   * Required. Type of the security event.
   *
   * @param type String
   */
  public void setType(String type) {
    typeValidator.validate(type);
    this.type = type;
  }

  /**
   * Date and time at which the event occurred.
   *
   * @return dateTime
   */
  public ZonedDateTime getTimestamp() {
    return timestamp;
  }

  /**
   * Required. Date and time at which the event occurred.
   *
   * @param timestamp dateTime
   */
  public void setTimestamp(ZonedDateTime timestamp) {
    this.timestamp = timestamp;
  }

  /**
   * Additional information about the occurred security event.
   *
   * @return String
   */
  public String getTechInfo() {
    return techInfo;
  }

  /**
   * Optional. Additional information about the occurred security event.
   *
   * @param techInfo String
   */
  public void setTechInfo(String techInfo) {
    techInfoValidator.validate(techInfo);
    this.techInfo = techInfo;
  }

  @Override
  public boolean transactionRelated() {
    return false;
  }

  @Override
  public boolean validate() {
    return typeValidator.safeValidate(type)
        && timestamp != null
        && techInfoValidator.safeValidate(techInfo);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SecurityEventNotificationRequest that = (SecurityEventNotificationRequest) o;
    return Objects.equals(type, that.type)
        && Objects.equals(timestamp, that.timestamp)
        && Objects.equals(techInfo, that.techInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, timestamp, techInfo);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("type", type)
        .add("timestamp", timestamp)
        .add("techInfo", techInfo)
        .add("isValid", validate())
        .toString();
  }
}
