package eu.chargetime.ocpp.model.securityext;

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

import eu.chargetime.ocpp.model.RequestWithId;
import eu.chargetime.ocpp.model.remotetrigger.TriggerMessageRequest;
import eu.chargetime.ocpp.model.securityext.types.FirmwareStatusEnumType;
import eu.chargetime.ocpp.utilities.MoreObjects;
import java.util.Objects;

public class SignedFirmwareStatusNotificationRequest extends RequestWithId {

  private FirmwareStatusEnumType status;
  private Integer requestId;

  /** Private default constructor for serialization purposes. */
  private SignedFirmwareStatusNotificationRequest() {}

  /**
   * Handle required fields.
   *
   * @param status FirmwareStatusEnumType. See {@link #setStatus(FirmwareStatusEnumType)}
   */
  public SignedFirmwareStatusNotificationRequest(FirmwareStatusEnumType status) {
    setStatus(status);
  }

  /**
   * This contains the progress status of the firmware installation.
   *
   * @return {@link FirmwareStatusEnumType}
   */
  public FirmwareStatusEnumType getStatus() {
    return status;
  }

  /**
   * Required. This contains the progress status of the firmware installation.
   *
   * @param status {@link FirmwareStatusEnumType}
   */
  public void setStatus(FirmwareStatusEnumType status) {
    this.status = status;
  }

  /**
   * The request id that was provided in the {@link SignedUpdateFirmwareRequest} that started this
   * firmware update. This field is mandatory, unless the message was triggered by a {@link
   * TriggerMessageRequest} or the {@link ExtendedTriggerMessageRequest} AND there is no firmware
   * update ongoing.
   *
   * @return Integer
   */
  public Integer getRequestId() {
    return requestId;
  }

  /**
   * Optional. The request id that was provided in the {@link SignedUpdateFirmwareRequest} that
   * started this firmware update. This field is mandatory, unless the message was triggered by a
   * {@link TriggerMessageRequest} or the {@link ExtendedTriggerMessageRequest} AND there is no
   * firmware update ongoing.
   *
   * @param requestId Integer
   */
  public void setRequestId(Integer requestId) {
    this.requestId = requestId;
  }

  @Override
  public boolean transactionRelated() {
    return false;
  }

  @Override
  public boolean validate() {
    return status != null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SignedFirmwareStatusNotificationRequest that = (SignedFirmwareStatusNotificationRequest) o;
    return Objects.equals(status, that.status) && Objects.equals(requestId, that.requestId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, requestId);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("status", status)
        .add("requestId", requestId)
        .add("isValid", validate())
        .toString();
  }
}
