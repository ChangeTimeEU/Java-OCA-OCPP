package eu.chargetime.ocpp.model.securityext.test.types;

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

import eu.chargetime.ocpp.PropertyConstraintException;
import eu.chargetime.ocpp.model.securityext.types.CertificateHashDataType;
import eu.chargetime.ocpp.model.securityext.types.HashAlgorithmEnumType;
import eu.chargetime.ocpp.utilities.TestUtilities;
import org.junit.Test;

public class CertificateHashDataTypeTest {

  @Test
  public void validate_constructor_returnsFalse() {
    // Given
    CertificateHashDataType dataType = new CertificateHashDataType();

    // When
    boolean actual = dataType.validate();

    // Then
    assertFalse(actual);
  }

  @Test
  public void validate_setRequired_returnsTrue() {
    // Given
    HashAlgorithmEnumType hashAlgorithm = HashAlgorithmEnumType.SHA256;
    String issuerKeyHash = givenIdentifierString();
    String issuerNameHash = givenIdentifierString();
    String serialNumber = givenSerialNumber();
    CertificateHashDataType dataType = new CertificateHashDataType();

    dataType.setHashAlgorithm(hashAlgorithm);
    dataType.setIssuerNameHash(issuerNameHash);
    dataType.setIssuerKeyHash(issuerKeyHash);
    dataType.setSerialNumber(serialNumber);

    // When
    boolean actual = dataType.validate();

    // Then
    assertTrue(actual);
  }

  @Test(expected = PropertyConstraintException.class)
  public void setIssuerNameHash_nonIdentifierString_throwsPropertyConstraintException() {
    // Given
    String issuerNameHash = TestUtilities.aString(50);
    CertificateHashDataType dataType = new CertificateHashDataType();

    // When
    dataType.setIssuerNameHash(issuerNameHash);

    // Then throws
  }

  @Test(expected = PropertyConstraintException.class)
  public void setIssuerKeyHash_nonIdentifierString_throwsPropertyConstraintException() {
    // Given
    String issuerKeyHash = TestUtilities.aString(50);
    CertificateHashDataType dataType = new CertificateHashDataType();

    // When
    dataType.setIssuerKeyHash(issuerKeyHash);

    // Then throws
  }

  @Test
  public void setSerialNumber_maximumLengthString_nothingThrown() {
    // Given
    String serialNumber = TestUtilities.aString(40);
    CertificateHashDataType dataType = new CertificateHashDataType();

    // When
    dataType.setSerialNumber(serialNumber);

    // Then nothing thrown
  }

  @Test(expected = PropertyConstraintException.class)
  public void setSerialNumber_exceedingLengthString_throwsPropertyConstraintException() {
    // Given
    String serialNumber = TestUtilities.aString(41);
    CertificateHashDataType dataType = new CertificateHashDataType();

    // When
    dataType.setSerialNumber(serialNumber);

    // Then throws
  }

  private String givenIdentifierString() {
    return "an-identifier-string";
  }

  private String givenSerialNumber() {
    return "123";
  }
}
