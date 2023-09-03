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

import eu.chargetime.ocpp.PropertyConstraintException;
import eu.chargetime.ocpp.model.securityext.SecurityEventNotificationRequest;
import eu.chargetime.ocpp.utilities.TestUtilities;
import java.time.ZonedDateTime;
import org.junit.Test;

public class SecurityEventNotificationRequestTest {

  @Test
  public void validate_constructor_returnsTrue() {
    // Given
    String type = givenType();
    ZonedDateTime timestamp = ZonedDateTime.now();
    SecurityEventNotificationRequest request =
        new SecurityEventNotificationRequest(type, timestamp);

    // When
    boolean actual = request.validate();

    // Then
    assertTrue(actual);
  }

  @Test(expected = PropertyConstraintException.class)
  public void constructor_nullType_throwsPropertyConstraintException() {
    // Given
    ZonedDateTime timestamp = ZonedDateTime.now();

    // When
    new SecurityEventNotificationRequest(null, timestamp);

    // Then throws
  }

  @Test
  public void validate_returnFalse() {
    // Given
    String type = givenType();
    SecurityEventNotificationRequest request = new SecurityEventNotificationRequest(type, null);

    // When
    boolean actual = request.validate();

    // Then
    assertFalse(actual);
  }

  @Test
  public void setType_maximumLengthString_nothingThrown() {
    // Given
    String type = TestUtilities.aString(50);
    ZonedDateTime timestamp = ZonedDateTime.now();

    // When
    new SecurityEventNotificationRequest(type, timestamp);

    // Then nothing thrown
  }

  @Test(expected = PropertyConstraintException.class)
  public void setType_exceedingLengthString_throwsPropertyConstraintException() {
    // Given
    String type = TestUtilities.aString(51);
    ZonedDateTime timestamp = ZonedDateTime.now();

    // When
    new SecurityEventNotificationRequest(type, timestamp);

    // Then throws
  }

  @Test
  public void setTechInfo_maximumLengthString_nothingThrown() {
    // Given
    String type = givenType();
    ZonedDateTime timestamp = ZonedDateTime.now();
    String techInfo = TestUtilities.aString(255);
    SecurityEventNotificationRequest request =
        new SecurityEventNotificationRequest(type, timestamp);

    // When
    request.setTechInfo(techInfo);

    // Then nothing thrown
  }

  @Test(expected = PropertyConstraintException.class)
  public void setTechInfo_exceedingLengthString_throwsPropertyConstraintException() {
    // Given
    String type = givenType();
    ZonedDateTime timestamp = ZonedDateTime.now();
    String techInfo = TestUtilities.aString(256);
    SecurityEventNotificationRequest request =
        new SecurityEventNotificationRequest(type, timestamp);

    // When
    request.setTechInfo(techInfo);

    // Then throws
  }

  private String givenType() {
    return "A type string";
  }

  private String givenTechInfo() {
    return "A technical info string";
  }
}
