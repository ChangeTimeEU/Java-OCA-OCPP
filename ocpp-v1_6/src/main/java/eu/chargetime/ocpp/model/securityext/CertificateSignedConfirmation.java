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
import eu.chargetime.ocpp.model.securityext.types.CertificateSignedStatusEnumType;
import eu.chargetime.ocpp.utilities.MoreObjects;
import java.util.Objects;

public class CertificateSignedConfirmation extends Confirmation {

  private CertificateSignedStatusEnumType status;

  /** Private default constructor for serialization purposes. */
  private CertificateSignedConfirmation() {}

  /**
   * Handle required fields.
   *
   * @param status CertificateSignedStatusEnumType. See {@link
   *     #setStatus(CertificateSignedStatusEnumType)}
   */
  public CertificateSignedConfirmation(CertificateSignedStatusEnumType status) {
    setStatus(status);
  }

  /**
   * Returns whether certificate signing has been accepted, otherwise rejected.
   *
   * @return {@link CertificateSignedStatusEnumType}
   */
  public CertificateSignedStatusEnumType getStatus() {
    return status;
  }

  /**
   * Required. Returns whether certificate signing has been accepted, otherwise rejected.
   *
   * @param status {@link CertificateSignedStatusEnumType}
   */
  public void setStatus(CertificateSignedStatusEnumType status) {
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
    CertificateSignedConfirmation that = (CertificateSignedConfirmation) o;
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
