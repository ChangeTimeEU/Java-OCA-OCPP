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
import eu.chargetime.ocpp.model.securityext.types.CertificateUseEnumType;
import eu.chargetime.ocpp.model.validation.StringMaxLengthValidationRule;
import eu.chargetime.ocpp.model.validation.Validator;
import eu.chargetime.ocpp.model.validation.ValidatorBuilder;
import eu.chargetime.ocpp.utilities.MoreObjects;
import java.util.Objects;

public class InstallCertificateRequest extends RequestWithId {

  private static final transient Validator certificateValidator =
      new ValidatorBuilder()
          .addRule(new StringMaxLengthValidationRule(5500))
          .setRequired(true)
          .build();

  private CertificateUseEnumType certificateType;
  private String certificate;

  /** Private default constructor for serialization purposes. */
  private InstallCertificateRequest() {}

  /**
   * Handle required fields.
   *
   * @param certificateType CertificateUseEnumType. See {@link
   *     #setCertificateType(CertificateUseEnumType)}
   * @param certificate String. See {@link #setCertificate(String)}
   */
  public InstallCertificateRequest(CertificateUseEnumType certificateType, String certificate) {
    setCertificateType(certificateType);
    setCertificate(certificate);
  }

  /**
   * Indicates the certificate type that is sent.
   *
   * @return {@link CertificateUseEnumType}
   */
  public CertificateUseEnumType getCertificateType() {
    return certificateType;
  }

  /**
   * Required. Indicates the certificate type that is sent.
   *
   * @param certificateType {@link CertificateUseEnumType}
   */
  public void setCertificateType(CertificateUseEnumType certificateType) {
    this.certificateType = certificateType;
  }

  /**
   * An PEM encoded X.509 certificate.
   *
   * @return string[0..5500]
   */
  public String getCertificate() {
    return certificate;
  }

  /**
   * Required. An PEM encoded X.509 certificate.
   *
   * @param certificate string[0..5500]
   */
  public void setCertificate(String certificate) {
    certificateValidator.validate(certificate);
    this.certificate = certificate;
  }

  @Override
  public boolean transactionRelated() {
    return false;
  }

  @Override
  public boolean validate() {
    return certificateType != null && certificateValidator.safeValidate(certificate);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    InstallCertificateRequest that = (InstallCertificateRequest) o;
    return Objects.equals(certificateType, that.certificateType)
        && Objects.equals(certificate, that.certificate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(certificateType, certificate);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("certificateType", certificateType)
        .add("certificate", certificate)
        .add("isValid", validate())
        .toString();
  }
}
