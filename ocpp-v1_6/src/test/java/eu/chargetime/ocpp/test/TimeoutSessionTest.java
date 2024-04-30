package eu.chargetime.ocpp.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import eu.chargetime.ocpp.ISession;
import eu.chargetime.ocpp.SessionEvents;
import eu.chargetime.ocpp.TimeoutSessionDecorator;
import eu.chargetime.ocpp.utilities.TimeoutTimer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/*
   ChargeTime.eu - Java-OCA-OCPP

   MIT License

   Copyright (C) 2016-2018 Thomas Volden <tv@chargetime.eu>
   Copyright (C) 2022 Emil Melar <emil@iconsultable.no>

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
@RunWith(MockitoJUnitRunner.class)
public class TimeoutSessionTest {

  private TimeoutSessionDecorator session;

  @Mock private SessionEvents sessionEvents;
  @Mock private TimeoutTimer timeoutTimer;
  @Mock private ISession sessionMock;

  @Before
  public void setup() throws Exception {
    session = new TimeoutSessionDecorator(timeoutTimer, sessionMock);
    doAnswer(invocation -> sessionEvents = invocation.getArgument(0, SessionEvents.class))
        .when(sessionMock)
        .accept(any());
    doAnswer(invocation -> sessionEvents = invocation.getArgument(1, SessionEvents.class))
        .when(sessionMock)
        .open(any(), any());
  }

  @Test
  public void handleConnectionOpened_opened_beginTimeout() throws Exception {
    // Given
    session.open(null, sessionEvents);

    // When
    sessionEvents.handleConnectionOpened();

    // Then
    verify(timeoutTimer, times(1)).begin();
  }

  @Test
  public void handleConnectionClosed_opened_endTimeout() throws Exception {
    // Given
    session.open(null, sessionEvents);

    // When
    sessionEvents.handleConnectionClosed(0, null);

    // Then
    verify(timeoutTimer, times(1)).end();
  }

  @Test
  public void handleConnectionOpened_accepted_beginTimeout() throws Exception {
    // Given
    session.accept(sessionEvents);

    // When
    sessionEvents.handleConnectionOpened();

    // Then
    verify(timeoutTimer, times(1)).begin();
  }

  @Test
  public void handleConnectionClosed_accepted_endTimeout() throws Exception {
    // Given
    session.accept(sessionEvents);

    // When
    sessionEvents.handleConnectionClosed(0, null);

    // Then
    verify(timeoutTimer, times(1)).end();
  }

  @Test
  public void handleRequest_any_resetTimeout() throws Exception {
    // Given
    session.open(null, sessionEvents);

    // When
    sessionEvents.handleRequest(null);

    // Then
    verify(timeoutTimer, times(1)).reset();
  }

  @Test
  public void handleConfirmation_any_resetTimeout() throws Exception {
    // Given
    session.open(null, sessionEvents);

    // When
    sessionEvents.handleConfirmation(null, null);

    // Then
    verify(timeoutTimer, times(1)).reset();
  }
}
