package eu.chargetime.ocpp.test;
/*
   ChargeTime.eu - Java-OCA-OCPP

   MIT License

   Copyright (C) 2018 Thomas Volden <tv@chargetime.eu>

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

import eu.chargetime.ocpp.AuthenticationException;
import eu.chargetime.ocpp.IServerAPI;
import eu.chargetime.ocpp.JSONConfiguration;
import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.NotConnectedException;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.ServerEvents;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.feature.Feature;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.model.SessionInformation;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FakeCentralSystem {
  private static final Logger logger = LoggerFactory.getLogger(FakeCentralSystem.class);
  private final int port = 8887;
  private final String host = "127.0.0.1";

  private IServerAPI server;
  private boolean isStarted;
  private UUID currentSession;
  private Request handlerRequest = null;
  private Confirmation receivedConfirmation;

  public FakeCentralSystem() {
    JSONConfiguration configuration =
        JSONConfiguration.get().setParameter(JSONConfiguration.REUSE_ADDR_PARAMETER, true);
    server = new JSONServer(configuration);
    isStarted = false;
  }

  public void addFeature(Feature feature) {
    FeatureTestDecorator monitoredFeature =
        new FeatureTestDecorator(feature, request -> handlerRequest = request);
    server.addFeature(monitoredFeature);
  }

  public void started() throws Exception {
    if (!isStarted) {
      server.open(
          host,
          port,
          new ServerEvents() {
            @Override
            public void authenticateSession(
                SessionInformation information, String username, byte[] password) throws AuthenticationException {}

            @Override
            public void newSession(UUID sessionIndex, SessionInformation information) {
              currentSession = sessionIndex;
            }

            @Override
            public void lostSession(UUID sessionIndex) {
              currentSession = null;
            }
          });
      logger.info("Server started on host: {}, port: {}", host, port);
      isStarted = true;
    }
  }

  public void stopped() {
    server.close();
    isStarted = false;
  }

  public boolean hasHandled(Request request) {
    if (handlerRequest != null && request != null) return handlerRequest.equals(request);
    return false;
  }

  public void send(Request request)
      throws NotConnectedException, OccurenceConstraintException, UnsupportedFeatureException {
    CompletionStage<Confirmation> send = server.send(currentSession, request);
    send.whenComplete((confirmation, throwable) -> receivedConfirmation = confirmation);
  }

  public boolean received(Confirmation confirmation) {
    if (receivedConfirmation != null && confirmation != null)
      return receivedConfirmation.equals(confirmation);
    return false;
  }
}
