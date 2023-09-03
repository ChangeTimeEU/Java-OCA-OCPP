package eu.chargetime.ocpp.model.securityext.types;

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

import eu.chargetime.ocpp.model.securityext.ExtendedTriggerMessageRequest;
import eu.chargetime.ocpp.model.securityext.SignedFirmwareStatusNotificationRequest;
import eu.chargetime.ocpp.model.securityext.SignedUpdateFirmwareRequest;

/**
 * Status of a firmware download. A value with "Intermediate state" in the description, is an
 * intermediate state, update process is not finished. A value with "Failure end state" in the
 * description, is an end state, update process has stopped, update failed. A value with "Successful
 * end state" in the description, is an end state, update process has stopped, update successful.
 * FirmwareStatusEnumType is used by {@link SignedFirmwareStatusNotificationRequest}
 */
public enum FirmwareStatusEnumType {
  /** Intermediate state. New firmware has been downloaded by Charge Point. */
  Downloaded,

  /** Failure end state. Charge Point failed to download firmware. */
  DownloadFailed,

  /** Intermediate state. Firmware is being downloaded. */
  Downloading,

  /** Intermediate state. Downloading of new firmware has been scheduled. */
  DownloadScheduled,

  /** Intermediate state. Downloading has been paused. */
  DownloadPaused,

  /**
   * Charge Point is not performing firmware update related tasks. Status Idle SHALL only be used as
   * in a {@link SignedFirmwareStatusNotificationRequest} that was triggered by {@link
   * ExtendedTriggerMessageRequest}.
   */
  Idle,

  /** Failure end state. Installation of new firmware has failed. */
  InstallationFailed,

  /** Intermediate state. Firmware is being installed. */
  Installing,

  /** Successful end state. New firmware has successfully been installed in Charge Point. */
  Installed,

  /**
   * Intermediate state. Charge Point is about to reboot to activate new firmware. This status MAY
   * be omitted if a reboot is an integral part of the installation and cannot be reported
   * separately
   */
  InstallRebooting,

  /**
   * Intermediate state. Installation of the downloaded firmware is scheduled to take place on
   * installDateTime given in {@link SignedUpdateFirmwareRequest}.
   */
  InstallScheduled,

  /**
   * Failure end state. Verification of the new firmware (e.g. using a checksum or some other means)
   * has failed and installation will not proceed. (Final failure state)
   */
  InstallVerificationFailed,

  /** Failure end state. The firmware signature is not valid. */
  InvalidSignature,

  /** Intermediate state. Provide signature successfully verified. */
  SignatureVerified
}
