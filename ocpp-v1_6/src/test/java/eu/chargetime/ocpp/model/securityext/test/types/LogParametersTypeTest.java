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
import eu.chargetime.ocpp.model.securityext.types.LogParametersType;
import eu.chargetime.ocpp.utilities.TestUtilities;
import java.time.ZonedDateTime;
import org.junit.Test;

public class LogParametersTypeTest {

  @Test
  public void validate_constructor_returnsFalse() {
    // Given
    LogParametersType type = new LogParametersType();

    // When
    boolean actual = type.validate();

    // Then
    assertFalse(actual);
  }

  @Test
  public void validate_setRequired_returnsTrue() {
    // Given
    String remoteLocation = givenLocation();
    LogParametersType type = new LogParametersType();

    type.setRemoteLocation(remoteLocation);

    // When
    boolean actual = type.validate();

    // Then
    assertTrue(actual);
  }

  @Test
  public void validate_setRequiredAndOptional_returnsTrue() {
    // Given
    String remoteLocation = givenLocation();
    ZonedDateTime oldestTimestamp = ZonedDateTime.now();
    ZonedDateTime latestTimestamp = ZonedDateTime.now();
    LogParametersType type = new LogParametersType();

    type.setRemoteLocation(remoteLocation);
    type.setOldestTimestamp(oldestTimestamp);
    type.setLatestTimestamp(latestTimestamp);

    // When
    boolean actual = type.validate();

    // Then
    assertTrue(actual);
  }

  @Test
  public void setLocation_maximumLengthString_nothingThrown() {
    // Given
    String location = TestUtilities.aString(512);
    LogParametersType type = new LogParametersType();

    // When
    type.setRemoteLocation(location);

    // Then nothing thrown
  }

  @Test(expected = PropertyConstraintException.class)
  public void setLocation_exceedingLengthString_throwsPropertyConstraintException() {
    // Given
    String location = TestUtilities.aString(513);
    LogParametersType type = new LogParametersType();

    // When
    type.setRemoteLocation(location);

    // Then throws
  }

  private String givenLocation() {
    return "Location";
  }
}
