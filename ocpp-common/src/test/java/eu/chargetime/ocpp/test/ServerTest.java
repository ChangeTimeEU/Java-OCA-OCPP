package eu.chargetime.ocpp.test;

import eu.chargetime.ocpp.*;
import eu.chargetime.ocpp.feature.Feature;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.model.SessionInformation;
import eu.chargetime.ocpp.model.TestConfirmation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

/*
   ChargeTime.eu - Java-OCA-OCPP

   MIT License

   Copyright (C) 2016-2018 Thomas Volden <tv@chargetime.eu>

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
public class ServerTest {

  private static final String LOCALHOST = "localhost";
  private static final int PORT = 42;

  private Server server;
  private SessionEvents sessionEvents;
  private ListenerEvents listenerEvents;
  private UUID sessionIndex;

  @Mock private Session session;
  @Mock private Feature feature;
  @Mock private Listener listener;
  @Mock private ServerEvents serverEvents;
  @Mock private Request request;
  @Mock private SessionInformation information;
  @Mock private IFeatureRepository featureRepository;
  @Mock IPromiseRepository promiseRepository;

  @Before
  public void setup() {
    UUID sessionId = UUID.randomUUID();
    when(request.validate()).thenReturn(true);
    when(session.getSessionId()).thenReturn(sessionId);
    doAnswer(invocation -> listenerEvents = invocation.getArgument(2, ListenerEvents.class))
        .when(listener)
        .open(anyString(), anyInt(), any());
    doAnswer(invocation -> sessionEvents = invocation.getArgument(0, SessionEvents.class))
        .when(session)
        .accept(any());
    doAnswer(invocation -> sessionIndex = invocation.getArgument(0, UUID.class))
        .when(serverEvents)
        .newSession(any(), any());

    when(promiseRepository.createPromise(any())).then(invocation -> new CompletableFuture<Confirmation>());
    when(featureRepository.findFeature(any())).thenReturn(Optional.of(feature));
    when(session.getFeatureRepository()).thenReturn(featureRepository);
    when(session.storeRequest(any())).thenAnswer(invocation -> UUID.randomUUID().toString());
    server = new Server(listener, promiseRepository);
  }

  @Test
  public void newSession_serverIsListening_sessionIsAccepted() {
    // Given
    server.open(LOCALHOST, PORT, serverEvents);

    // When
    listenerEvents.newSession(session, information);

    // Then
    verify(session, times(1)).accept(any());
  }

  @Test
  public void newSession_serverIsListening_callbackWithIndex0() {
    // Given
    server.open(LOCALHOST, PORT, serverEvents);

    // When
    listenerEvents.newSession(session, information);

    // Then
    verify(serverEvents, times(1)).newSession(any(UUID.class), eq(information));
  }

  @Test
  public void send_aMessage_isCommunicated() throws Exception {
    // Given
    server.open(LOCALHOST, PORT, serverEvents);
    listenerEvents.newSession(session, information);

    // When
    server.send(sessionIndex, request);

    // Then
    // TODO action and uuid should not be nullable
    verify(session, times(1)).sendRequest(nullable(String.class), eq(request), nullable(String.class));
  }

  @Test
  public void handleRequest_callsFeatureHandleRequest() throws UnsupportedFeatureException {
    // Given
    server.open(LOCALHOST, PORT, serverEvents);
    listenerEvents.newSession(session, information);

    // When
    sessionEvents.handleRequest(request);

    // Then
    verify(feature, times(1)).handleRequest(any(UUID.class), eq(request));
  }

  @Test
  public void send_aMessage_validatesMessage() throws Exception {
    // Given
    server.open(LOCALHOST, PORT, serverEvents);
    listenerEvents.newSession(session, information);

    // When
    server.send(sessionIndex, request);

    // Then
    verify(request, times(1)).validate();
  }

  @Test
  public void send_aMessage_promiseCompletes() throws Exception {
    // Given
    server.open(LOCALHOST, PORT, serverEvents);
    listenerEvents.newSession(session, information);
    CompletableFuture<Confirmation> internalFuture = new CompletableFuture<>();
    when(promiseRepository.createPromise(any())).thenReturn(internalFuture);

    // When
    CompletableFuture<Confirmation> returnedFuture = server.send(sessionIndex, request);
    TestConfirmation confirmation = new TestConfirmation();
    internalFuture.complete(confirmation);

    // Then
    assertThat(returnedFuture, is(internalFuture));
    assertThat(returnedFuture.isDone(), is(true));
    assertThat(returnedFuture.get(), is(confirmation));
    verify(session, times(1)).removeRequest(any());
    verify(promiseRepository, times(1)).removePromise(any());
  }

  @Test
  public void send_aMessage_promiseCompletesExceptionally() throws Exception {
    // Given
    server.open(LOCALHOST, PORT, serverEvents);
    listenerEvents.newSession(session, information);
    CompletableFuture<Confirmation> internalFuture = new CompletableFuture<>();
    when(promiseRepository.createPromise(any())).thenReturn(internalFuture);

    // When
    CompletableFuture<Confirmation> returnedFuture = server.send(sessionIndex, request);
    internalFuture.completeExceptionally(new IllegalStateException());

    // Then
    assertThat(returnedFuture, is(internalFuture));
    assertThat(returnedFuture.isDone(), is(true));
    assertThat(returnedFuture.isCompletedExceptionally(), is(true));
    ExecutionException executionException = assertThrows(ExecutionException.class, returnedFuture::get);
    assertThat(executionException.getCause().getClass(), is(equalTo(IllegalStateException.class)));
    verify(session, times(1)).removeRequest(any());
    verify(promiseRepository, times(1)).removePromise(any());
  }

  @Test
  public void send_aMessage_promiseCompletesWithTimeout() throws Exception {
    // Given
    server.open(LOCALHOST, PORT, serverEvents);
    listenerEvents.newSession(session, information);
    CompletableFuture<Confirmation> internalFuture = new CompletableFuture<>();
    when(promiseRepository.createPromise(any())).thenReturn(internalFuture);

    // When
    CompletableFuture<Confirmation> returnedFuture = server.send(sessionIndex, request);
    // If the client uses at least Java 9, it could use CompletableFuture::orTimeout(..).
//    returnedFuture.orTimeout(50, TimeUnit.MILLISECONDS);
    assertThat(returnedFuture.isDone(), is(false));
    Thread.sleep(100);
    // .. alternatively, it can be implemented manually
    returnedFuture.completeExceptionally(new TimeoutException());

    // Then
    assertThat(returnedFuture, is(internalFuture));
    assertThat(returnedFuture.isDone(), is(true));
    assertThat(returnedFuture.isCompletedExceptionally(), is(true));
    ExecutionException executionException = assertThrows(ExecutionException.class, returnedFuture::get);
    assertThat(executionException.getCause().getClass(), is(equalTo(TimeoutException.class)));
    verify(session, times(1)).removeRequest(any());
    verify(promiseRepository, times(1)).removePromise(any());
  }
}
