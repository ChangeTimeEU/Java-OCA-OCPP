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
import eu.chargetime.ocpp.model.securityext.types.CertificateHashDataType;
import eu.chargetime.ocpp.model.securityext.types.GetInstalledCertificateStatusEnumType;
import eu.chargetime.ocpp.utilities.MoreObjects;
import java.util.Arrays;
import java.util.Objects;

public class GetInstalledCertificateIdsConfirmation extends Confirmation {

  private GetInstalledCertificateStatusEnumType status;
  private CertificateHashDataType[] certificateHashData;

  /** Private default constructor for serialization purposes. */
  private GetInstalledCertificateIdsConfirmation() {}

  /**
   * Handle required fields.
   *
   * @param status GetInstalledCertificateStatusEnumType. See {@link
   *     #setStatus(GetInstalledCertificateStatusEnumType)}
   */
  public GetInstalledCertificateIdsConfirmation(GetInstalledCertificateStatusEnumType status) {
    setStatus(status);
  }

  /**
   * Charge Point indicates if it can process the request.
   *
   * @return {@link GetInstalledCertificateStatusEnumType}
   */
  public GetInstalledCertificateStatusEnumType getStatus() {
    return status;
  }

  /**
   * Required. Charge Point indicates if it can process the request.
   *
   * @param status {@link GetInstalledCertificateStatusEnumType}
   */
  public void setStatus(GetInstalledCertificateStatusEnumType status) {
    this.status = status;
  }

  /**
   * The Charge Point includes the Certificate information for each available certificate.
   *
   * @return array of {@link CertificateHashDataType}
   */
  public CertificateHashDataType[] getCertificateHashData() {
    return certificateHashData;
  }

  /**
   * Optional. The Charge Point includes the Certificate information for each available certificate.
   *
   * @param certificateHashData array of {@link CertificateHashDataType}
   */
  public void setCertificateHashData(CertificateHashDataType[] certificateHashData) {
    this.certificateHashData = certificateHashData;
  }

  @Override
  public boolean validate() {
    return status != null && validateCertificateHashData();
  }

  private boolean validateCertificateHashData() {
    if (certificateHashData == null) {
      return true;
    }
    for (CertificateHashDataType chd : certificateHashData) {
      if (!chd.validate()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GetInstalledCertificateIdsConfirmation that = (GetInstalledCertificateIdsConfirmation) o;
    return Objects.equals(status, that.status)
        && Arrays.equals(certificateHashData, that.certificateHashData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, Arrays.hashCode(certificateHashData));
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("status", status)
        .add("certificateHashData", certificateHashData)
        .add("isValid", validate())
        .toString();
  }
}
