/*
 * ChargeTime.eu - Java-OCA-OCPP
 *
 * MIT License
 *
 * Copyright (C) 2016-2018 Thomas Volden <tv@chargetime.eu>
 * Copyright (C) 2023 Robert Schlabbach <robert.schlabbach@ubitricity.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package eu.chargetime.ocpp;

import eu.chargetime.ocpp.feature.function.Function;
import eu.chargetime.ocpp.feature.profile.Profile;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.wss.BaseWssSocketBuilder;
import eu.chargetime.ocpp.wss.WssSocketBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import javax.annotation.Nullable;
import javax.net.ssl.SSLContext;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.protocols.IProtocol;
import org.java_websocket.protocols.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** multiple protocol versions capable JSON Web Socket implementation of the client. */
public class MultiProtocolJSONClient implements IMultiProtocolClientAPI {

  private static final Logger logger = LoggerFactory.getLogger(MultiProtocolJSONClient.class);

  private final String identity;
  private final MultiProtocolFeatureRepository featureRepository;
  private final MultiProtocolWebSocketTransmitter transmitter;
  private final Client client;

  /**
   * Application composite root for a json client.
   *
   * @param protocolVersions list of protocol versions to offer.
   */
  public MultiProtocolJSONClient(List<ProtocolVersion> protocolVersions) {
    this(protocolVersions, null);
  }

  /**
   * Application composite root for a json client.
   *
   * @param protocolVersions list of protocol versions to offer.
   * @param identity identity if set, will append identity to url.
   */
  public MultiProtocolJSONClient(List<ProtocolVersion> protocolVersions, String identity) {
    this(protocolVersions, identity, JSONConfiguration.get());
  }

  /**
   * Application composite root for a json client.
   *
   * @param protocolVersions list of protocol versions to offer.
   * @param identity if set, will append identity to url.
   * @param configuration network configuration for a json client.
   */
  public MultiProtocolJSONClient(
      List<ProtocolVersion> protocolVersions, String identity, JSONConfiguration configuration) {
    this.identity = identity;
    featureRepository = new MultiProtocolFeatureRepository(protocolVersions);
    List<IProtocol> inputProtocols = new ArrayList<>(protocolVersions.size());
    for (ProtocolVersion protocolVersion : protocolVersions) {
      inputProtocols.add(new Protocol(protocolVersion.getSubProtocolName()));
    }
    Draft draft = new Draft_6455(Collections.emptyList(), inputProtocols);
    transmitter = new MultiProtocolWebSocketTransmitter(featureRepository, configuration, draft);
    JSONCommunicator communicator = new JSONCommunicator(transmitter, false);
    ISessionFactory sessionFactory = new MultiProtocolSessionFactory(featureRepository);
    ISession session = sessionFactory.createSession(communicator);
    client = new Client(session, new PromiseRepository());
  }

  /**
   * Application composite root for a json client.
   *
   * @param protocolVersions list of protocol versions to offer.
   * @param identity if set, will append identity to url.
   * @param wssSocketBuilder to build {@link java.net.Socket} to support wss://.
   */
  public MultiProtocolJSONClient(
      List<ProtocolVersion> protocolVersions, String identity, WssSocketBuilder wssSocketBuilder) {
    this(protocolVersions, identity, wssSocketBuilder, JSONConfiguration.get());
  }

  /**
   * Application composite root for a json client.
   *
   * @param protocolVersions list of protocol versions to offer.
   * @param identity if set, will append identity to url.
   * @param wssSocketBuilder to build {@link java.net.Socket} to support wss://.
   * @param configuration network configuration for a json client.
   */
  public MultiProtocolJSONClient(
      List<ProtocolVersion> protocolVersions,
      String identity,
      WssSocketBuilder wssSocketBuilder,
      JSONConfiguration configuration) {
    this(protocolVersions, identity, configuration);
    enableWSS(wssSocketBuilder);
  }

  // To ensure the exposed API is backward compatible
  public void enableWSS(SSLContext sslContext) throws IOException {
    WssSocketBuilder wssSocketBuilder =
        BaseWssSocketBuilder.builder().sslSocketFactory(sslContext.getSocketFactory());
    enableWSS(wssSocketBuilder);
  }

  /**
   * Enables WSS connection to the endpoint. The {@code wssSocketBuilder} must be initialized at
   * that step (as required parameters set might vary depending on implementation the {@link
   * WssSocketBuilder#verify()} is used to ensure initialization).
   *
   * @param wssSocketBuilder builder to provide SSL socket
   * @return instance of {@link MultiProtocolJSONClient}
   * @throws IllegalStateException in case if the client is already connected
   * @throws IllegalStateException in case {@code wssSocketBuilder} not initialized properly
   */
  public MultiProtocolJSONClient enableWSS(WssSocketBuilder wssSocketBuilder) {
    wssSocketBuilder.verify();
    transmitter.enableWSS(wssSocketBuilder);
    return this;
  }

  /**
   * Applies JSONConfiguration changes when already connected. Specifically, the WebSocket ping
   * interval can be changed without reconnecting by calling this method.
   */
  public void reconfigure() {
    transmitter.configure();
  }

  @Override
  public void addFeatureProfile(Profile profile) {
    addFeatureProfile(ProtocolVersion.OCPP1_6, profile);
  }

  @Override
  public void addFeatureProfile(ProtocolVersion protocolVersion, Profile profile) {
    featureRepository.addFeatureProfile(protocolVersion, profile);
  }

  @Override
  public void addFunction(ProtocolVersion protocolVersion, Function function) {
    featureRepository.addFeatureFunction(protocolVersion, function);
  }

  @Override
  public void connect(String url, ClientEvents clientEvents) {
    logger.debug("Feature repository: {}", featureRepository);

    String identityUrl = (identity != null) ? String.format("%s/%s", url, identity) : url;
    client.connect(identityUrl, clientEvents);
  }

  @Override
  @Nullable
  public ProtocolVersion getProtocolVersion() {
    return featureRepository.getProtocolVersion();
  }

  @Override
  public CompletionStage<Confirmation> send(Request request)
      throws OccurenceConstraintException, UnsupportedFeatureException {
    return client.send(request);
  }

  @Override
  public boolean asyncCompleteRequest(String uniqueId, Confirmation confirmation)
      throws UnsupportedFeatureException, OccurenceConstraintException {
    return client.asyncCompleteRequest(uniqueId, confirmation);
  }

  @Override
  public void disconnect() {
    client.disconnect();
  }

  public Exception getLastError() {
    return transmitter.getLastError();
  }

  @Override
  public boolean isClosed() {
    return transmitter.isClosed();
  }

  @Override
  public UUID getSessionId() {
    return client.getSessionId();
  }
}
