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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import eu.chargetime.ocpp.model.securityext.GetInstalledCertificateIdsConfirmation;
import eu.chargetime.ocpp.model.securityext.types.CertificateHashDataType;
import eu.chargetime.ocpp.model.securityext.types.GetInstalledCertificateStatusEnumType;
import org.junit.Test;

public class GetInstalledCertificateIdsConfirmationTest {

  @Test
  public void validate_constructor_returnsTrue() {
    // Given
    GetInstalledCertificateStatusEnumType status = GetInstalledCertificateStatusEnumType.Accepted;
    GetInstalledCertificateIdsConfirmation confirmation =
        new GetInstalledCertificateIdsConfirmation(status);

    // When
    boolean actual = confirmation.validate();

    // Then
    assertTrue(actual);
  }

  @Test
  public void validate_setRequired_returnsTrue() {
    // Given
    GetInstalledCertificateStatusEnumType status = GetInstalledCertificateStatusEnumType.Accepted;
    GetInstalledCertificateIdsConfirmation confirmation =
        new GetInstalledCertificateIdsConfirmation(null);
    confirmation.setStatus(status);

    // When
    boolean actual = confirmation.validate();

    // Then
    assertTrue(actual);
  }

  @Test
  public void validate_returnFalse() {
    // Given
    GetInstalledCertificateIdsConfirmation request =
        new GetInstalledCertificateIdsConfirmation(null);

    // When
    boolean actual = request.validate();

    // Then
    assertFalse(actual);
  }

  @Test
  public void validate_setRequiredAndOptional_returnsTrue() {
    // Given
    GetInstalledCertificateStatusEnumType status = GetInstalledCertificateStatusEnumType.Accepted;
    GetInstalledCertificateIdsConfirmation confirmation =
        new GetInstalledCertificateIdsConfirmation(null);
    confirmation.setStatus(status);
    confirmation.setCertificateHashData(givenCertificateHashData());

    // When
    boolean actual = confirmation.validate();

    // Then
    assertTrue(actual);
  }

  private CertificateHashDataType[] givenCertificateHashData() {
    CertificateHashDataType certificateHashDataType = mock(CertificateHashDataType.class);
    when(certificateHashDataType.validate()).thenReturn(true);
    return new CertificateHashDataType[] {certificateHashDataType};
  }
}
