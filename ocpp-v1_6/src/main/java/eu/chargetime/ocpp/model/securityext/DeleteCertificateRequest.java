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
import eu.chargetime.ocpp.model.securityext.types.CertificateHashDataType;
import eu.chargetime.ocpp.utilities.MoreObjects;
import java.util.Objects;

public class DeleteCertificateRequest extends RequestWithId {

  private CertificateHashDataType certificateHashData;

  /** Private default constructor for serialization purposes. */
  private DeleteCertificateRequest() {}

  /**
   * Handle required fields.
   *
   * @param certificateHashData String. See {@link #setCertificateHashData(CertificateHashDataType)}
   */
  public DeleteCertificateRequest(CertificateHashDataType certificateHashData) {
    setCertificateHashData(certificateHashData);
  }

  /**
   * Indicates the certificate of which deletion is requested.
   *
   * @return {@link CertificateHashDataType}
   */
  public CertificateHashDataType getCertificateHashData() {
    return certificateHashData;
  }

  /**
   * Required. Indicates the certificate of which deletion is requested.
   *
   * @param certificateHashData {@link CertificateHashDataType}
   */
  public void setCertificateHashData(CertificateHashDataType certificateHashData) {
    this.certificateHashData = certificateHashData;
  }

  @Override
  public boolean transactionRelated() {
    return false;
  }

  @Override
  public boolean validate() {
    return certificateHashData != null && certificateHashData.validate();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DeleteCertificateRequest that = (DeleteCertificateRequest) o;
    return Objects.equals(certificateHashData, that.certificateHashData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(certificateHashData);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("certificateHashData", certificateHashData)
        .add("isValid", validate())
        .toString();
  }
}
