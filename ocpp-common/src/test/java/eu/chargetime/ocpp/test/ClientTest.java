package eu.chargetime.ocpp.test;
/*
ChargeTime.eu - Java-OCA-OCPP
Copyright (C) 2015-2016 Thomas Volden <tv@chargetime.eu>

MIT License

Copyright (C) 2016-2018 Thomas Volden
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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import eu.chargetime.ocpp.*;
import eu.chargetime.ocpp.feature.Feature;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.model.TestConfirmation;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientTest {
  private Client client;
  private SessionEvents eventHandler;

  @Mock private Session session;
  @Mock private Feature feature;
  @Mock private Request request;
  @Mock private CompletableFuture<Confirmation> completableFuture;
  @Mock private ClientEvents events;
  @Mock private IFeatureRepository featureRepository;
  @Mock private IPromiseRepository promiseRepository;

  @Before
  public void setup() {
    when(request.validate()).thenReturn(true);
    doAnswer(invocation -> eventHandler = invocation.getArgument(1, SessionEvents.class))
        .when(session)
        .open(any(), any());

    when(featureRepository.findFeature(any())).thenReturn(Optional.of(feature));
    when(session.getFeatureRepository()).thenReturn(featureRepository);
    client = new Client(session, promiseRepository);
  }

  @Test
  public void connect_connects() {
    // Given
    String someUrl = "localhost";

    // When
    client.connect(someUrl, events);

    // Then
    verify(session, times(1)).open(eq(someUrl), any());
  }

  @Test
  public void connect_connectionOpenedEvent() {
    // Given
    client.connect(null, events);

    // When
    this.eventHandler.handleConnectionOpened();

    // Then
    verify(events, times(1)).connectionOpened();
    verify(events, never()).connectionClosed(0, null);
  }

  @Test
  public void connect_connectionClosedEvent() {
    // Given
    client.connect(null, events);

    // When
    this.eventHandler.handleConnectionClosed(0, null);

    // Then
    verify(events, times(1)).connectionClosed(0, null);
    verify(events, never()).connectionOpened();
  }

  @Test
  public void send_aMessage_isCommunicated() throws Exception {
    // When
    client.send(request);

    // Then
    // TODO action and uuid should not be nullable
    verify(session, times(1)).sendRequest(nullable(String.class), eq(request), nullable(String.class));
  }

  @Test
  public void responseReceived_aMessageWasSend_PromiseIsCompleted() throws Exception {
    // Given
    String someUniqueId = "Some id";
    lenient().when(session.storeRequest(any())).thenReturn(someUniqueId);
    when(promiseRepository.getPromise(any())).thenReturn(Optional.of(completableFuture));

    // When
    client.connect(null, null);
    eventHandler.handleConfirmation(someUniqueId, null);

    // Then
    verify(promiseRepository).getPromise(someUniqueId);
  }

  @Test
  public void handleRequest_returnsConfirmation() throws UnsupportedFeatureException {
    // Given
    client.connect(null, null);
    when(feature.handleRequest(null, request)).thenReturn(new TestConfirmation());

    // When
    Confirmation conf = eventHandler.handleRequest(request);

    // Then
    assertThat(conf, instanceOf(TestConfirmation.class));
  }

  @Test
  public void handleRequest_callsFeatureHandleRequest() throws UnsupportedFeatureException {
    // Given
    client.connect(null, null);

    // When
    eventHandler.handleRequest(request);

    // Then
    verify(feature, times(1)).handleRequest(any(), eq(request));
  }

  @Test
  public void send_aMessage_validatesMessage() throws Exception {
    // When
    client.send(request);

    // Then
    verify(request, times(1)).validate();
  }
}
