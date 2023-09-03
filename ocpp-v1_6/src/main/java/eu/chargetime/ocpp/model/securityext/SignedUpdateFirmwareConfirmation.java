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
import eu.chargetime.ocpp.model.securityext.types.UpdateFirmwareStatusEnumType;
import eu.chargetime.ocpp.utilities.MoreObjects;
import java.util.Objects;

public class SignedUpdateFirmwareConfirmation extends Confirmation {

  private UpdateFirmwareStatusEnumType status;

  /** Private default constructor for serialization purposes. */
  private SignedUpdateFirmwareConfirmation() {}

  /**
   * Handle required fields.
   *
   * @param status Integer. See {@link #setStatus(UpdateFirmwareStatusEnumType)}
   */
  public SignedUpdateFirmwareConfirmation(UpdateFirmwareStatusEnumType status) {
    setStatus(status);
  }

  /**
   * This field indicates whether the Charge Point was able to accept the request.
   *
   * @return {@link UpdateFirmwareStatusEnumType}
   */
  public UpdateFirmwareStatusEnumType getStatus() {
    return status;
  }

  /**
   * Required. This field indicates whether the Charge Point was able to accept the request.
   *
   * @param status {@link UpdateFirmwareStatusEnumType}
   */
  public void setStatus(UpdateFirmwareStatusEnumType status) {
    this.status = status;
  }

  @Override
  public boolean validate() {
    return status != null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SignedUpdateFirmwareConfirmation that = (SignedUpdateFirmwareConfirmation) o;
    return Objects.equals(status, that.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("retries", status)
        .add("isValid", validate())
        .toString();
  }
}
