package eu.chargetime.ocpp.feature.profile.securityext;

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

import eu.chargetime.ocpp.feature.Feature;
import eu.chargetime.ocpp.feature.ProfileFeature;
import eu.chargetime.ocpp.feature.profile.Profile;
import eu.chargetime.ocpp.feature.securityext.CertificateSignedFeature;
import eu.chargetime.ocpp.feature.securityext.DeleteCertificateFeature;
import eu.chargetime.ocpp.feature.securityext.ExtendedTriggerMessageFeature;
import eu.chargetime.ocpp.feature.securityext.GetInstalledCertificateIdsFeature;
import eu.chargetime.ocpp.feature.securityext.GetLogFeature;
import eu.chargetime.ocpp.feature.securityext.InstallCertificateFeature;
import eu.chargetime.ocpp.feature.securityext.LogStatusNotificationFeature;
import eu.chargetime.ocpp.feature.securityext.SecurityEventNotificationFeature;
import eu.chargetime.ocpp.feature.securityext.SignCertificateFeature;
import eu.chargetime.ocpp.feature.securityext.SignedFirmwareStatusNotificationFeature;
import eu.chargetime.ocpp.feature.securityext.SignedUpdateFirmwareFeature;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.model.securityext.CertificateSignedRequest;
import eu.chargetime.ocpp.model.securityext.DeleteCertificateRequest;
import eu.chargetime.ocpp.model.securityext.ExtendedTriggerMessageRequest;
import eu.chargetime.ocpp.model.securityext.GetInstalledCertificateIdsRequest;
import eu.chargetime.ocpp.model.securityext.GetLogRequest;
import eu.chargetime.ocpp.model.securityext.InstallCertificateRequest;
import eu.chargetime.ocpp.model.securityext.LogStatusNotificationRequest;
import eu.chargetime.ocpp.model.securityext.SecurityEventNotificationRequest;
import eu.chargetime.ocpp.model.securityext.SignCertificateRequest;
import eu.chargetime.ocpp.model.securityext.SignedFirmwareStatusNotificationRequest;
import eu.chargetime.ocpp.model.securityext.SignedUpdateFirmwareRequest;
import eu.chargetime.ocpp.model.securityext.types.CertificateHashDataType;
import eu.chargetime.ocpp.model.securityext.types.CertificateUseEnumType;
import eu.chargetime.ocpp.model.securityext.types.FirmwareType;
import eu.chargetime.ocpp.model.securityext.types.LogEnumType;
import eu.chargetime.ocpp.model.securityext.types.LogParametersType;
import eu.chargetime.ocpp.model.securityext.types.MessageTriggerEnumType;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ServerSecurityExtProfile implements Profile {

  private final Set<Feature> features;
  private final ServerSecurityExtEventHandler handler;

  public ServerSecurityExtProfile(ServerSecurityExtEventHandler handler) {
    this.features = new HashSet<>();
    this.handler = handler;

    features.add(new CertificateSignedFeature(null));
    features.add(new DeleteCertificateFeature(null));
    features.add(new ExtendedTriggerMessageFeature(null));
    features.add(new GetInstalledCertificateIdsFeature(null));
    features.add(new GetLogFeature(null));
    features.add(new InstallCertificateFeature(null));
    features.add(new LogStatusNotificationFeature(this));
    features.add(new SecurityEventNotificationFeature(this));
    features.add(new SignCertificateFeature(this));
    features.add(new SignedFirmwareStatusNotificationFeature(this));
    features.add(new SignedUpdateFirmwareFeature(null));
  }

  @Override
  public ProfileFeature[] getFeatureList() {
    return features.toArray(new ProfileFeature[0]);
  }

  @Override
  public Confirmation handleRequest(UUID sessionIndex, Request request) {
    Confirmation result = null;

    if (request instanceof LogStatusNotificationRequest) {
      result =
          handler.handleLogStatusNotificationRequest(
              sessionIndex, (LogStatusNotificationRequest) request);
    } else if (request instanceof SecurityEventNotificationRequest) {
      result =
          handler.handleSecurityEventNotificationRequest(
              sessionIndex, (SecurityEventNotificationRequest) request);
    } else if (request instanceof SignCertificateRequest) {
      result = handler.handleSignCertificateRequest(sessionIndex, (SignCertificateRequest) request);
    } else if (request instanceof SignedFirmwareStatusNotificationRequest) {
      result =
          handler.handleSignedFirmwareStatusNotificationRequest(
              sessionIndex, (SignedFirmwareStatusNotificationRequest) request);
    }

    return result;
  }

  /**
   * Create a client {@link CertificateSignedRequest} with required values.
   *
   * @return an instance of {@link CertificateSignedRequest}
   * @see CertificateSignedRequest
   * @see CertificateSignedFeature
   */
  public CertificateSignedRequest createCertificateSignedRequest(String certificateChain) {
    return new CertificateSignedRequest(certificateChain);
  }

  /**
   * Create a client {@link DeleteCertificateRequest} with required values.
   *
   * @return an instance of {@link DeleteCertificateRequest}
   * @see DeleteCertificateRequest
   * @see DeleteCertificateFeature
   */
  public DeleteCertificateRequest createDeleteCertificateRequest(
      CertificateHashDataType certificateHashData) {
    return new DeleteCertificateRequest(certificateHashData);
  }

  /**
   * Create a client {@link ExtendedTriggerMessageFeature}.
   *
   * @return an instance of {@link ExtendedTriggerMessageRequest}
   * @see ExtendedTriggerMessageRequest
   * @see ExtendedTriggerMessageFeature
   */
  public ExtendedTriggerMessageRequest createExtendedTriggerMessageRequest(
      MessageTriggerEnumType requestedMessage) {
    return new ExtendedTriggerMessageRequest(requestedMessage);
  }

  /**
   * Create a client {@link GetInstalledCertificateIdsRequest} required values.
   *
   * @return an instance of {@link GetInstalledCertificateIdsRequest}
   * @see GetInstalledCertificateIdsRequest
   * @see GetInstalledCertificateIdsFeature
   */
  public GetInstalledCertificateIdsRequest createGetInstalledCertificateIdsRequest(
      CertificateUseEnumType certificateType) {
    return new GetInstalledCertificateIdsRequest(certificateType);
  }

  /**
   * Create a client {@link GetLogRequest}.
   *
   * @return an instance of {@link GetLogRequest}
   * @see GetLogRequest
   * @see GetLogFeature
   */
  public GetLogRequest createGetLogRequest(
      LogEnumType logType, Integer requestId, LogParametersType log) {
    return new GetLogRequest(logType, requestId, log);
  }

  /**
   * Create a client {@link InstallCertificateRequest} with required values.
   *
   * @return an instance of {@link InstallCertificateRequest}
   * @see InstallCertificateRequest
   * @see InstallCertificateFeature
   */
  public InstallCertificateRequest createInstallCertificateRequest(
      CertificateUseEnumType certificateType, String certificate) {
    return new InstallCertificateRequest(certificateType, certificate);
  }

  /**
   * Create a client {@link SignedUpdateFirmwareRequest} with required values.
   *
   * @return an instance of {@link SignedUpdateFirmwareRequest}
   * @see SignedUpdateFirmwareRequest
   * @see SignedUpdateFirmwareFeature
   */
  public SignedUpdateFirmwareRequest createSignedUpdateFirmwareRequest(
      Integer requestId, FirmwareType firmware) {
    return new SignedUpdateFirmwareRequest(requestId, firmware);
  }
}
