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
import eu.chargetime.ocpp.model.securityext.types.UploadLogStatusEnumType;
import eu.chargetime.ocpp.utilities.MoreObjects;
import java.util.Objects;

public class LogStatusNotificationRequest extends RequestWithId {

  private UploadLogStatusEnumType status;
  private Integer requestId;

  /** Private default constructor for serialization purposes. */
  private LogStatusNotificationRequest() {}

  /**
   * Handle required fields.
   *
   * @param status UploadLogStatusEnumType. See {@link #setStatus(UploadLogStatusEnumType)}
   */
  public LogStatusNotificationRequest(UploadLogStatusEnumType status) {
    setStatus(status);
  }

  /**
   * This contains the status of the log upload.
   *
   * @return {@link UploadLogStatusEnumType}
   */
  public UploadLogStatusEnumType getStatus() {
    return status;
  }

  /**
   * Required. This contains the status of the log upload.
   *
   * @param status {@link UploadLogStatusEnumType}
   */
  public void setStatus(UploadLogStatusEnumType status) {
    this.status = status;
  }

  /**
   * The request id that was provided in the {@link GetLogRequest} that started this log upload.
   *
   * @return Integer
   */
  public Integer getRequestId() {
    return requestId;
  }

  /**
   * Optional. The request id that was provided in the {@link GetLogRequest} that started this log
   * upload.
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
    LogStatusNotificationRequest that = (LogStatusNotificationRequest) o;
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
