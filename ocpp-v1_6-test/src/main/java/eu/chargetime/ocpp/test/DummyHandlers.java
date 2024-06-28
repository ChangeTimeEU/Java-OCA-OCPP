package eu.chargetime.ocpp.test;
/*
   ChargeTime.eu - Java-OCA-OCPP

   MIT License

   Copyright (C) 2016-2018 Thomas Volden <tv@chargetime.eu>
   Copyright (C) 2019 Kevin Raddatz <kevin.raddatz@valtech-mobility.com>
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

import eu.chargetime.ocpp.AuthenticationException;
import eu.chargetime.ocpp.ServerEvents;
import eu.chargetime.ocpp.feature.profile.ServerCoreEventHandler;
import eu.chargetime.ocpp.feature.profile.ServerFirmwareManagementEventHandler;
import eu.chargetime.ocpp.feature.profile.securityext.ServerSecurityExtEventHandler;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.model.SessionInformation;
import eu.chargetime.ocpp.model.core.*;
import eu.chargetime.ocpp.model.firmware.DiagnosticsStatusNotificationConfirmation;
import eu.chargetime.ocpp.model.firmware.DiagnosticsStatusNotificationRequest;
import eu.chargetime.ocpp.model.firmware.FirmwareStatusNotificationConfirmation;
import eu.chargetime.ocpp.model.firmware.FirmwareStatusNotificationRequest;
import eu.chargetime.ocpp.model.securityext.*;
import eu.chargetime.ocpp.model.securityext.types.GenericStatusEnumType;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.function.BiConsumer;

public class DummyHandlers {

  private boolean riggedToFail;

  private Request receivedRequest;
  private Confirmation receivedConfirmation;

  private String currentIdentifier;
  private UUID currentSessionIndex;

  public ServerCoreEventHandler createServerCoreEventHandler() {
    return new ServerCoreEventHandler() {
      @Override
      public AuthorizeConfirmation handleAuthorizeRequest(
          UUID sessionIndex, AuthorizeRequest request) {
        receivedRequest = request;
        AuthorizeConfirmation confirmation = new AuthorizeConfirmation();
        IdTagInfo tagInfo = new IdTagInfo();
        tagInfo.setStatus(AuthorizationStatus.Accepted);
        ZonedDateTime calendar = ZonedDateTime.parse("2018-01-01T01:01:01.988Z");
        tagInfo.setExpiryDate(calendar);
        confirmation.setIdTagInfo(tagInfo);
        return failurePoint(confirmation);
      }

      @Override
      public BootNotificationConfirmation handleBootNotificationRequest(
          UUID sessionIndex, BootNotificationRequest request) {
        receivedRequest = request;
        BootNotificationConfirmation confirmation = new BootNotificationConfirmation();
        try {
          confirmation.setInterval(1);
        } catch (Exception e) {
          e.printStackTrace();
        }
        confirmation.setCurrentTime(ZonedDateTime.now());
        confirmation.setStatus(RegistrationStatus.Accepted);
        return failurePoint(confirmation);
      }

      @Override
      public DataTransferConfirmation handleDataTransferRequest(
          UUID sessionIndex, DataTransferRequest request) {
        receivedRequest = request;
        DataTransferConfirmation confirmation = new DataTransferConfirmation();
        confirmation.setStatus(DataTransferStatus.Accepted);
        return failurePoint(confirmation);
      }

      @Override
      public HeartbeatConfirmation handleHeartbeatRequest(
          UUID sessionIndex, HeartbeatRequest request) {
        receivedRequest = request;
        HeartbeatConfirmation confirmation = new HeartbeatConfirmation();
        confirmation.setCurrentTime(ZonedDateTime.now());
        return failurePoint(confirmation);
      }

      @Override
      public MeterValuesConfirmation handleMeterValuesRequest(
          UUID sessionIndex, MeterValuesRequest request) {
        receivedRequest = request;
        return failurePoint(new MeterValuesConfirmation());
      }

      @Override
      public StartTransactionConfirmation handleStartTransactionRequest(
          UUID sessionIndex, StartTransactionRequest request) {
        receivedRequest = request;
        IdTagInfo tagInfo = new IdTagInfo();
        tagInfo.setStatus(AuthorizationStatus.Accepted);

        StartTransactionConfirmation confirmation = new StartTransactionConfirmation();
        confirmation.setIdTagInfo(tagInfo);
        confirmation.setTransactionId(42);
        return failurePoint(confirmation);
      }

      @Override
      public StatusNotificationConfirmation handleStatusNotificationRequest(
          UUID sessionIndex, StatusNotificationRequest request) {
        receivedRequest = request;
        StatusNotificationConfirmation confirmation = new StatusNotificationConfirmation();
        return failurePoint(confirmation);
      }

      @Override
      public StopTransactionConfirmation handleStopTransactionRequest(
          UUID sessionIndex, StopTransactionRequest request) {
        receivedRequest = request;
        StopTransactionConfirmation confirmation = new StopTransactionConfirmation();
        return failurePoint(confirmation);
      }
    };
  }

  public ServerFirmwareManagementEventHandler createServerFirmwareManagementEventHandler() {
    return new ServerFirmwareManagementEventHandler() {
      @Override
      public DiagnosticsStatusNotificationConfirmation handleDiagnosticsStatusNotificationRequest(
          UUID sessionId, DiagnosticsStatusNotificationRequest request) {
        receivedRequest = request;
        DiagnosticsStatusNotificationConfirmation confirmation =
            new DiagnosticsStatusNotificationConfirmation();
        return failurePoint(confirmation);
      }

      @Override
      public FirmwareStatusNotificationConfirmation handleFirmwareStatusNotificationRequest(
          UUID sessionId, FirmwareStatusNotificationRequest request) {
        receivedRequest = request;
        FirmwareStatusNotificationConfirmation confirmation =
            new FirmwareStatusNotificationConfirmation();
        return failurePoint(confirmation);
      }
    };
  }

  public ServerSecurityExtEventHandler createServerSecurityExtEventHandler() {
    return new ServerSecurityExtEventHandler() {

      @Override
      public LogStatusNotificationConfirmation handleLogStatusNotificationRequest(UUID sessionIndex, LogStatusNotificationRequest request) {
        receivedRequest = request;
        LogStatusNotificationConfirmation confirmation = new LogStatusNotificationConfirmation();
        return failurePoint(confirmation);
      }

      @Override
      public SecurityEventNotificationConfirmation handleSecurityEventNotificationRequest(UUID sessionIndex, SecurityEventNotificationRequest request) {
        receivedRequest = request;
        SecurityEventNotificationConfirmation confirmation = new SecurityEventNotificationConfirmation();
        return failurePoint(confirmation);
      }

      @Override
      public SignCertificateConfirmation handleSignCertificateRequest(UUID sessionIndex, SignCertificateRequest request) {
        receivedRequest = request;
        SignCertificateConfirmation confirmation = new SignCertificateConfirmation(GenericStatusEnumType.Accepted);
        return failurePoint(confirmation);
      }

      @Override
      public SignedFirmwareStatusNotificationConfirmation handleSignedFirmwareStatusNotificationRequest(UUID sessionIndex, SignedFirmwareStatusNotificationRequest request) {
        receivedRequest = request;
        SignedFirmwareStatusNotificationConfirmation confirmation = new SignedFirmwareStatusNotificationConfirmation();
        return failurePoint(confirmation);
      }
    };
  }

  public ServerEvents generateServerEventsHandler() {
    return new ServerEvents() {
      @Override
      public void authenticateSession(
          SessionInformation information, String username, byte[] password) throws AuthenticationException {}

      @Override
      public void newSession(UUID sessionIndex, SessionInformation information) {
        currentSessionIndex = sessionIndex;
        currentIdentifier = information.getIdentifier();
      }

      @Override
      public void lostSession(UUID identity) {
        currentSessionIndex = null;
        currentIdentifier = null;
        // clear
        receivedConfirmation = null;
        receivedRequest = null;
      }
    };
  }

  public BiConsumer<Confirmation, Throwable> generateWhenCompleteHandler() {
    return (confirmation, throwable) -> receivedConfirmation = confirmation;
  }

  <T extends Confirmation> T failurePoint(T confirmation) {
    if (riggedToFail) {
      riggedToFail = false;
      throw new RuntimeException();
    }
    return confirmation;
  }

  public boolean wasLatestRequest(Type requestType) {
    return requestType != null
        && receivedRequest != null
        && requestType.equals(receivedRequest.getClass());
  }

  public <T extends Request> T getReceivedRequest(Class<T> clazz) {
    return wasLatestRequest(clazz) ? (T) receivedRequest : null;
  }

  public boolean wasLatestConfirmation(Type confirmationType) {
    return confirmationType != null
        && receivedConfirmation != null
        && confirmationType.equals(receivedConfirmation.getClass());
  }

  public <T extends Confirmation> T getReceivedConfirmation(Class<T> clazz) {
    return wasLatestConfirmation(clazz) ? (T) receivedConfirmation : null;
  }

  public void setRiggedToFail(boolean riggedToFail) {
    this.riggedToFail = riggedToFail;
  }

  public boolean isRiggedToFail() {
    return riggedToFail;
  }

  public String getCurrentIdentifier() {
    return currentIdentifier;
  }

  public UUID getCurrentSessionIndex() {
    return currentSessionIndex;
  }
}
