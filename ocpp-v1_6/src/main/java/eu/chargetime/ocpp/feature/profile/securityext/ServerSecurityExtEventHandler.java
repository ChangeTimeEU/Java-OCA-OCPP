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

import eu.chargetime.ocpp.model.securityext.LogStatusNotificationConfirmation;
import eu.chargetime.ocpp.model.securityext.LogStatusNotificationRequest;
import eu.chargetime.ocpp.model.securityext.SecurityEventNotificationConfirmation;
import eu.chargetime.ocpp.model.securityext.SecurityEventNotificationRequest;
import eu.chargetime.ocpp.model.securityext.SignCertificateConfirmation;
import eu.chargetime.ocpp.model.securityext.SignCertificateRequest;
import eu.chargetime.ocpp.model.securityext.SignedFirmwareStatusNotificationConfirmation;
import eu.chargetime.ocpp.model.securityext.SignedFirmwareStatusNotificationRequest;
import java.util.UUID;

public interface ServerSecurityExtEventHandler {
  /**
   * Handle a {@link LogStatusNotificationRequest} and return a {@link
   * LogStatusNotificationConfirmation}.
   *
   * @param sessionIndex source of the request.
   * @param request incoming {@link LogStatusNotificationRequest} to handle.
   * @return outgoing {@link LogStatusNotificationConfirmation} to reply with.
   */
  LogStatusNotificationConfirmation handleLogStatusNotificationRequest(
      UUID sessionIndex, LogStatusNotificationRequest request);

  /**
   * Handle a {@link SecurityEventNotificationRequest} and return a {@link
   * SecurityEventNotificationConfirmation}.
   *
   * @param sessionIndex source of the request.
   * @param request incoming {@link SecurityEventNotificationRequest} to handle.
   * @return outgoing {@link SecurityEventNotificationConfirmation} to reply with.
   */
  SecurityEventNotificationConfirmation handleSecurityEventNotificationRequest(
      UUID sessionIndex, SecurityEventNotificationRequest request);

  /**
   * Handle a {@link SignCertificateRequest} and return a {@link SignCertificateConfirmation}.
   *
   * @param sessionIndex source of the request.
   * @param request incoming {@link SignCertificateRequest} to handle.
   * @return outgoing {@link SignCertificateConfirmation} to reply with.
   */
  SignCertificateConfirmation handleSignCertificateRequest(
      UUID sessionIndex, SignCertificateRequest request);

  /**
   * Handle a {@link SignedFirmwareStatusNotificationRequest} and return a {@link
   * SignedFirmwareStatusNotificationConfirmation}.
   *
   * @param sessionIndex source of the request.
   * @param request incoming {@link SignedFirmwareStatusNotificationRequest} to handle.
   * @return outgoing {@link SignedFirmwareStatusNotificationConfirmation} to reply with.
   */
  SignedFirmwareStatusNotificationConfirmation handleSignedFirmwareStatusNotificationRequest(
      UUID sessionIndex, SignedFirmwareStatusNotificationRequest request);
}
