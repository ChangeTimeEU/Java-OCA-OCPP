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
import eu.chargetime.ocpp.model.securityext.SignedUpdateFirmwareRequest;
import eu.chargetime.ocpp.model.validation.OCPPSecurityExtDatatypes;
import eu.chargetime.ocpp.model.validation.StringMaxLengthValidationRule;
import eu.chargetime.ocpp.model.validation.Validator;
import eu.chargetime.ocpp.model.validation.ValidatorBuilder;
import eu.chargetime.ocpp.utilities.MoreObjects;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Represents a copy of the firmware that can be loaded/updated on the Charge Point. FirmwareType is
 * used by {@link SignedUpdateFirmwareRequest}
 */
public class FirmwareType implements Validatable {
  private static final transient Validator locationValidator =
      new ValidatorBuilder()
          .addRule(OCPPSecurityExtDatatypes.string512())
          .setRequired(true)
          .build();

  private static final transient Validator signingCertificateValidator =
      new ValidatorBuilder()
          .addRule(new StringMaxLengthValidationRule(5500))
          .setRequired(true)
          .build();

  private static final transient Validator signatureValidator =
      new ValidatorBuilder()
          .addRule(new StringMaxLengthValidationRule(800))
          .setRequired(true)
          .build();

  private String location;
  private ZonedDateTime retrieveDateTime;
  private ZonedDateTime installDateTime;
  private String signingCertificate;
  private String signature;

  /**
   * URI defining the origin of the firmware.
   *
   * @return string[0..512]
   */
  public String getLocation() {
    return location;
  }

  /**
   * Required. URI defining the origin of the firmware.
   *
   * @param location string[0..512]
   */
  public void setLocation(String location) {
    locationValidator.validate(location);
    this.location = location;
  }

  /**
   * Date and time at which the firmware shall be retrieved.
   *
   * @return dateTime
   */
  public ZonedDateTime getRetrieveDateTime() {
    return retrieveDateTime;
  }

  /**
   * Required. Date and time at which the firmware shall be retrieved.
   *
   * @param retrieveDateTime dateTime
   */
  public void setRetrieveDateTime(ZonedDateTime retrieveDateTime) {
    this.retrieveDateTime = retrieveDateTime;
  }

  /**
   * Date and time at which the firmware shall be installed.
   *
   * @return dateTime
   */
  public ZonedDateTime getInstallDateTime() {
    return installDateTime;
  }

  /**
   * Optional. Date and time at which the firmware shall be installed.
   *
   * @param installDateTime dateTime
   */
  public void setInstallDateTime(ZonedDateTime installDateTime) {
    this.installDateTime = installDateTime;
  }

  /**
   * Certificate with which the firmware was signed. PEM encoded X.509 certificate.
   *
   * @return string[0..5500]
   */
  public String getSigningCertificate() {
    return signingCertificate;
  }

  /**
   * Required. Certificate with which the firmware was signed. PEM encoded X.509 certificate.
   *
   * @param signingCertificate string[0..5500]
   */
  public void setSigningCertificate(String signingCertificate) {
    signingCertificateValidator.validate(signingCertificate);
    this.signingCertificate = signingCertificate;
  }

  /**
   * Base64 encoded firmware signature.
   *
   * @return string[0..800]
   */
  public String getSignature() {
    return signature;
  }

  /**
   * Required. Base64 encoded firmware signature.
   *
   * @param signature string[0..800]
   */
  public void setSignature(String signature) {
    signatureValidator.validate(signature);
    this.signature = signature;
  }

  @Override
  public boolean validate() {
    return locationValidator.safeValidate(location)
        && retrieveDateTime != null
        && signingCertificateValidator.safeValidate(signingCertificate)
        && signatureValidator.safeValidate(signature);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FirmwareType that = (FirmwareType) o;
    return Objects.equals(location, that.location)
        && Objects.equals(retrieveDateTime, that.retrieveDateTime)
        && Objects.equals(installDateTime, that.installDateTime)
        && Objects.equals(signingCertificate, that.signingCertificate)
        && Objects.equals(signature, that.signature);
  }

  @Override
  public int hashCode() {
    return Objects.hash(location, retrieveDateTime, installDateTime, signingCertificate, signature);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("location", location)
        .add("retrieveDateTime", retrieveDateTime)
        .add("installDateTime", installDateTime)
        .add("signingCertificate", signingCertificate)
        .add("signature", signature)
        .toString();
  }
}
