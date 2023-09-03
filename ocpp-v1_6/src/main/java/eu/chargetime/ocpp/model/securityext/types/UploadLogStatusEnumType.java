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
import eu.chargetime.ocpp.model.securityext.LogStatusNotificationRequest;

/** UploadLogStatusEnumType is used by {@link LogStatusNotificationRequest} */
public enum UploadLogStatusEnumType {
  /** A badly formatted packet or other protocol incompatibility was detected. */
  BadMessage,

  /**
   * The Charge Point is not uploading a log file. Idle SHALL only be used when the message was
   * triggered by a {@link ExtendedTriggerMessageRequest}.
   */
  Idle,

  /** The server does not support the operation. */
  NotSupportedOperation,

  /** Insufficient permissions to perform the operation. */
  PermissionDenied,

  /** File has been uploaded successfully. */
  Uploaded,

  /** Failed to upload the requested file. */
  UploadFailure,

  /** File is being uploaded. */
  Uploading
}
