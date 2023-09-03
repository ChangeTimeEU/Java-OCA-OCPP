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
import eu.chargetime.ocpp.model.securityext.types.TriggerMessageStatusEnumType;
import eu.chargetime.ocpp.utilities.MoreObjects;
import java.util.Objects;

public class ExtendedTriggerMessageConfirmation extends Confirmation {

  private TriggerMessageStatusEnumType status;

  /** Private default constructor for serialization purposes. */
  private ExtendedTriggerMessageConfirmation() {}

  /**
   * Handle required fields.
   *
   * @param status TriggerMessageStatusEnumType. See {@link
   *     #setStatus(TriggerMessageStatusEnumType)}
   */
  public ExtendedTriggerMessageConfirmation(TriggerMessageStatusEnumType status) {
    setStatus(status);
  }

  /**
   * Indicates whether the Charge Point will send the requested notification or not.
   *
   * @return {@link TriggerMessageStatusEnumType}
   */
  public TriggerMessageStatusEnumType getStatus() {
    return status;
  }

  /**
   * Required. Indicates whether the Charge Point will send the requested notification or not.
   *
   * @param status {@link TriggerMessageStatusEnumType}
   */
  public void setStatus(TriggerMessageStatusEnumType status) {
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
    ExtendedTriggerMessageConfirmation that = (ExtendedTriggerMessageConfirmation) o;
    return Objects.equals(status, that.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("status", status)
        .add("isValid", validate())
        .toString();
  }
}
