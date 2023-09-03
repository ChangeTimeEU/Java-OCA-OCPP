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
import eu.chargetime.ocpp.model.validation.StringMaxLengthValidationRule;
import eu.chargetime.ocpp.model.validation.Validator;
import eu.chargetime.ocpp.model.validation.ValidatorBuilder;
import eu.chargetime.ocpp.utilities.MoreObjects;
import java.util.Objects;

public class CertificateHashDataType implements Validatable {
  private static final transient Validator identifierString128Validator =
      new ValidatorBuilder()
          .addRule(OCPPSecurityExtDatatypes.identifierString())
          .addRule(new StringMaxLengthValidationRule(128))
          .setRequired(true)
          .build();

  private static final transient Validator serialNumberValidator =
      new ValidatorBuilder().addRule(OCPPSecurityExtDatatypes.string40()).setRequired(true).build();

  private HashAlgorithmEnumType hashAlgorithm;
  private String issuerNameHash;
  private String issuerKeyHash;
  private String serialNumber;

  /**
   * Used algorithms for the hashes provided.
   *
   * @return {@link HashAlgorithmEnumType}
   */
  public HashAlgorithmEnumType getHashAlgorithm() {
    return hashAlgorithm;
  }

  /**
   * Required. Used algorithms for the hashes provided.
   *
   * @param hashAlgorithm {@link HashAlgorithmEnumType}
   */
  public void setHashAlgorithm(HashAlgorithmEnumType hashAlgorithm) {
    this.hashAlgorithm = hashAlgorithm;
  }

  /**
   * Hashed value of the IssuerName.
   *
   * @return identifierString[0..128]
   */
  public String getIssuerNameHash() {
    return issuerNameHash;
  }

  /**
   * Required. Hashed value of the IssuerName.
   *
   * @param issuerNameHash identifierString[0..128]
   */
  public void setIssuerNameHash(String issuerNameHash) {
    identifierString128Validator.validate(issuerNameHash);
    this.issuerNameHash = issuerNameHash;
  }

  /**
   * Hashed value of the issuers public key.
   *
   * @return identifierString[0..128]
   */
  public String getIssuerKeyHash() {
    return issuerKeyHash;
  }

  /**
   * Required. Hashed value of the issuers public key.
   *
   * @param issuerKeyHash String
   */
  public void setIssuerKeyHash(String issuerKeyHash) {
    identifierString128Validator.validate(issuerKeyHash);
    this.issuerKeyHash = issuerKeyHash;
  }

  /**
   * The serial number of the certificate.
   *
   * @return string[0..40]
   */
  public String getSerialNumber() {
    return serialNumber;
  }

  /**
   * Required. The serial number of the certificate.
   *
   * @param serialNumber string[0..40]
   */
  public void setSerialNumber(String serialNumber) {
    serialNumberValidator.validate(serialNumber);
    this.serialNumber = serialNumber;
  }

  @Override
  public boolean validate() {
    return hashAlgorithm != null
        && identifierString128Validator.safeValidate(issuerNameHash)
        && identifierString128Validator.safeValidate(issuerKeyHash)
        && serialNumberValidator.safeValidate(serialNumber);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CertificateHashDataType that = (CertificateHashDataType) o;
    return Objects.equals(hashAlgorithm, that.hashAlgorithm)
        && Objects.equals(issuerNameHash, that.issuerNameHash)
        && Objects.equals(issuerKeyHash, that.issuerKeyHash)
        && Objects.equals(serialNumber, that.serialNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hashAlgorithm, issuerNameHash, issuerKeyHash, serialNumber);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("hashAlgorithm", hashAlgorithm)
        .add("issuerNameHash", issuerNameHash)
        .add("issuerKeyHash", issuerKeyHash)
        .add("serialNumber", serialNumber)
        .toString();
  }
}
