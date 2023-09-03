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

public class SignCertificateRequest extends RequestWithId {

  private static final transient Validator csrValidator =
      new ValidatorBuilder()
          .addRule(new StringMaxLengthValidationRule(5500))
          .setRequired(true)
          .build();

  private String csr;

  /** Private default constructor for serialization purposes. */
  private SignCertificateRequest() {}

  /**
   * Handle required fields.
   *
   * @param csr String. See {@link #setCsr(String)}
   */
  public SignCertificateRequest(String csr) {
    setCsr(csr);
  }

  /**
   * The Charge Point SHALL send the public key in form of a Certificate Signing Request (CSR) as
   * described in RFC 2986 and then PEM encoded, using the {@link SignCertificateRequest} message.
   *
   * @return string[0..5500]
   */
  public String getCsr() {
    return csr;
  }

  /**
   * Required. The Charge Point SHALL send the public key in form of a Certificate Signing Request
   * (CSR) as described in RFC 2986 and then PEM encoded, using the {@link SignCertificateRequest}
   * message.
   *
   * @param csr string[0..5500]
   */
  public void setCsr(String csr) {
    csrValidator.validate(csr);
    this.csr = csr;
  }

  @Override
  public boolean transactionRelated() {
    return false;
  }

  @Override
  public boolean validate() {
    return csrValidator.safeValidate(csr);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SignCertificateRequest that = (SignCertificateRequest) o;
    return Objects.equals(csr, that.csr);
  }

  @Override
  public int hashCode() {
    return Objects.hash(csr);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("csr", csr).add("isValid", validate()).toString();
  }
}
