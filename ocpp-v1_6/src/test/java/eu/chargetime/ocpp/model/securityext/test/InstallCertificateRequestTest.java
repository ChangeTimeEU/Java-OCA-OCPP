package eu.chargetime.ocpp.model.securityext.test;

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

import static org.junit.Assert.assertTrue;

import eu.chargetime.ocpp.PropertyConstraintException;
import eu.chargetime.ocpp.model.securityext.InstallCertificateRequest;
import eu.chargetime.ocpp.model.securityext.types.CertificateUseEnumType;
import eu.chargetime.ocpp.utilities.TestUtilities;
import org.junit.Test;

public class InstallCertificateRequestTest {

  @Test
  public void validate_constructor_returnsTrue() {
    // Given
    CertificateUseEnumType certificateType = CertificateUseEnumType.CentralSystemRootCertificate;
    String certificate = givenCertificate();
    InstallCertificateRequest request = new InstallCertificateRequest(certificateType, certificate);

    // When
    boolean actual = request.validate();

    // Then
    assertTrue(actual);
  }

  @Test(expected = PropertyConstraintException.class)
  public void constructor_nullCertificate_throwsPropertyConstraintException() {
    // Given
    CertificateUseEnumType certificateType = CertificateUseEnumType.CentralSystemRootCertificate;

    // When
    new InstallCertificateRequest(certificateType, null);

    // Then throws
  }

  @Test
  public void setCertificate_maximumLengthString_nothingThrown() {
    // Given
    String certificate = TestUtilities.aString(5500);

    // When
    new InstallCertificateRequest(null, certificate);

    // Then nothing thrown
  }

  @Test(expected = PropertyConstraintException.class)
  public void setCertificate_exceedingLengthString_throwsPropertyConstraintException() {
    // Given
    String certificate = TestUtilities.aString(5501);

    // When
    new InstallCertificateRequest(null, certificate);

    // Then throws
  }

  private String givenCertificate() {
    return "A PEM encoded X.509 certificate";
  }
}
