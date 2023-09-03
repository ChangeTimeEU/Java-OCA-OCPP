package eu.chargetime.ocpp.model.securityext.types;

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

import eu.chargetime.ocpp.model.Validatable;
import eu.chargetime.ocpp.model.validation.OCPPSecurityExtDatatypes;
import eu.chargetime.ocpp.model.validation.Validator;
import eu.chargetime.ocpp.model.validation.ValidatorBuilder;
import eu.chargetime.ocpp.utilities.MoreObjects;
import java.time.ZonedDateTime;
import java.util.Objects;

public class LogParametersType implements Validatable {
  private static final transient Validator remoteLocationValidator =
      new ValidatorBuilder()
          .addRule(OCPPSecurityExtDatatypes.string512())
          .setRequired(true)
          .build();

  private String remoteLocation;
  private ZonedDateTime oldestTimestamp;
  private ZonedDateTime latestTimestamp;

  /**
   * The URL of the location at the remote system where the log should be stored.
   *
   * @return String
   */
  public String getRemoteLocation() {
    return remoteLocation;
  }

  /**
   * Required. The URL of the location at the remote system where the log should be stored.
   *
   * @param remoteLocation String
   */
  public void setRemoteLocation(String remoteLocation) {
    remoteLocationValidator.validate(remoteLocation);
    this.remoteLocation = remoteLocation;
  }

  /**
   * This contains the date and time of the oldest logging information to include in the
   * diagnostics.
   *
   * @return dateTime
   */
  public ZonedDateTime getOldestTimestamp() {
    return oldestTimestamp;
  }

  /**
   * Optional. This contains the date and time of the oldest logging information to include in the
   * diagnostics.
   *
   * @param oldestTimestamp dateTime
   */
  public void setOldestTimestamp(ZonedDateTime oldestTimestamp) {
    this.oldestTimestamp = oldestTimestamp;
  }

  /**
   * This contains the date and time of the latest logging information to include in the
   * diagnostics.
   *
   * @return dateTime
   */
  public ZonedDateTime getLatestTimestamp() {
    return latestTimestamp;
  }

  /**
   * Optional. This contains the date and time of the latest logging information to include in the
   * diagnostics.
   *
   * @param latestTimestamp dateTime
   */
  public void setLatestTimestamp(ZonedDateTime latestTimestamp) {
    this.latestTimestamp = latestTimestamp;
  }

  @Override
  public boolean validate() {
    return remoteLocationValidator.safeValidate(remoteLocation);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    LogParametersType that = (LogParametersType) o;
    return Objects.equals(remoteLocation, that.remoteLocation)
        && Objects.equals(oldestTimestamp, that.oldestTimestamp)
        && Objects.equals(latestTimestamp, that.latestTimestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(remoteLocation, oldestTimestamp, latestTimestamp);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("remoteLocation", remoteLocation)
        .add("oldestTimestamp", oldestTimestamp)
        .add("latestTimestamp", latestTimestamp)
        .toString();
  }
}
