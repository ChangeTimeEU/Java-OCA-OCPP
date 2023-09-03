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
import java.util.Objects;

public class CertificateSignedRequest extends RequestWithId {

  private static final transient Validator certificateChainValidator =
      new ValidatorBuilder()
          .addRule(new StringMaxLengthValidationRule(10000))
          .setRequired(true)
          .build();

  private String certificateChain;

  /** Private default constructor for serialization purposes. */
  private CertificateSignedRequest() {}

  /**
   * Handle required fields.
   *
   * @param certificateChain String. See {@link #setCertificateChain(String)}
   */
  public CertificateSignedRequest(String certificateChain) {
    setCertificateChain(certificateChain);
  }

  /**
   * The signed PEM encoded X.509 certificates. This can also contain the necessary sub CA
   * certificates. The maximum size of this field is limited by the configuration key:
   * CertificateSignedMaxSize.
   *
   * @return string[0..10000]
   */
  public String getCertificateChain() {
    return certificateChain;
  }

  /**
   * Required. The signed PEM encoded X.509 certificates. This can also contain the necessary sub CA
   * certificates. The maximum size of this field is limited by the configuration key:
   * CertificateSignedMaxSize.
   *
   * @param certificateChain string[0..10000]
   */
  public void setCertificateChain(String certificateChain) {
    certificateChainValidator.validate(certificateChain);
    this.certificateChain = certificateChain;
  }

  @Override
  public boolean transactionRelated() {
    return false;
  }

  @Override
  public boolean validate() {
    return certificateChainValidator.safeValidate(certificateChain);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CertificateSignedRequest that = (CertificateSignedRequest) o;
    return Objects.equals(certificateChain, that.certificateChain);
  }

  @Override
  public int hashCode() {
    return Objects.hash(certificateChain);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("certificateChain", certificateChain)
        .add("isValid", validate())
        .toString();
  }
}
