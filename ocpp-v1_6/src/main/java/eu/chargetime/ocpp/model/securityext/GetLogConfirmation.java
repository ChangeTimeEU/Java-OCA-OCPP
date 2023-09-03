package eu.chargetime.ocpp.model.securityext;

/*
   ChargeTime.eu - Java-OCA-OCPP

   MIT License

   Copyright (C) 2022 Mathias Oben <mathias.oben@enervalis.com>
   Copyright (C) 2022 Emil Melar <emil@iconsultable.no>

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

import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.securityext.types.LogStatusEnumType;
import eu.chargetime.ocpp.model.validation.StringMaxLengthValidationRule;
import eu.chargetime.ocpp.model.validation.Validator;
import eu.chargetime.ocpp.model.validation.ValidatorBuilder;
import eu.chargetime.ocpp.utilities.MoreObjects;
import java.util.Objects;

public class GetLogConfirmation extends Confirmation {

  private static final transient Validator filenameValidator =
      new ValidatorBuilder().addRule(new StringMaxLengthValidationRule(255)).build();

  private LogStatusEnumType status;
  private String filename;

  /** Private default constructor for serialization purposes. */
  private GetLogConfirmation() {}

  /**
   * Handle required fields.
   *
   * @param status LogStatusEnumType. See {@link #setStatus(LogStatusEnumType)}
   */
  public GetLogConfirmation(LogStatusEnumType status) {
    setStatus(status);
  }

  /**
   * This field indicates whether the Charge Point was able to accept the request.
   *
   * @return {@link LogStatusEnumType}
   */
  public LogStatusEnumType getStatus() {
    return status;
  }

  /**
   * Required. This field indicates whether the Charge Point was able to accept the request.
   *
   * @param status {@link LogStatusEnumType}
   */
  public void setStatus(LogStatusEnumType status) {
    this.status = status;
  }

  /**
   * This contains the name of the log file that will be uploaded. This field is not present when no
   * logging information is available.
   *
   * @return string[0..255]
   */
  public String getFilename() {
    return filename;
  }

  /**
   * Optional. This contains the name of the log file that will be uploaded. This field is not
   * present when no logging information is available.
   *
   * @param filename string[0..255]
   */
  public void setFilename(String filename) {
    filenameValidator.validate(filename);
    this.filename = filename;
  }

  @Override
  public boolean validate() {
    return status != null && filenameValidator.safeValidate(filename);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GetLogConfirmation that = (GetLogConfirmation) o;
    return Objects.equals(status, that.status) && Objects.equals(filename, that.filename);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, filename);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("status", status)
        .add("filename", filename)
        .add("isValid", validate())
        .toString();
  }
}
