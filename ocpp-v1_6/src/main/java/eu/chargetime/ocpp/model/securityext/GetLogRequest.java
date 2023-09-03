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
import eu.chargetime.ocpp.model.securityext.types.LogEnumType;
import eu.chargetime.ocpp.model.securityext.types.LogParametersType;
import eu.chargetime.ocpp.utilities.MoreObjects;
import java.util.Objects;

public class GetLogRequest extends RequestWithId {

  private LogEnumType logType;
  private Integer requestId;
  private Integer retries;
  private Integer retryInterval;
  private LogParametersType log;

  /** Private default constructor for serialization purposes. */
  private GetLogRequest() {}

  /**
   * Handle required fields.
   *
   * @param logType LogEnumType. See {@link #setLogType(LogEnumType)}
   * @param requestId Integer. See {@link #setRequestId(Integer)}
   * @param log LogParametersType. See {@link #setLog(LogParametersType)}
   */
  public GetLogRequest(LogEnumType logType, Integer requestId, LogParametersType log) {
    setLogType(logType);
    setRequestId(requestId);
    setLog(log);
  }

  /**
   * This contains the type of log file that the Charge Point should send.
   *
   * @return {@link LogEnumType}
   */
  public LogEnumType getLogType() {
    return logType;
  }

  /**
   * Required. This contains the type of log file that the Charge Point should send.
   *
   * @param logType {@link LogEnumType}
   */
  public void setLogType(LogEnumType logType) {
    this.logType = logType;
  }

  /**
   * The Id of this request.
   *
   * @return Integer
   */
  public Integer getRequestId() {
    return requestId;
  }

  /**
   * Required. The Id of this request.
   *
   * @param requestId Integer
   */
  public void setRequestId(Integer requestId) {
    this.requestId = requestId;
  }

  /**
   * This specifies how many times the Charge Point must try to upload the log before giving up. If
   * this field is not present, it is left to Charge Point to decide how many times it wants to
   * retry.
   *
   * @return Integer
   */
  public Integer getRetries() {
    return retries;
  }

  /**
   * Optional. This specifies how many times the Charge Point must try to upload the log before
   * giving up. If this field is not present, it is left to Charge Point to decide how many times it
   * wants to retry.
   *
   * @param retries Integer
   */
  public void setRetries(Integer retries) {
    this.retries = retries;
  }

  /**
   * The interval in seconds after which a retry may be attempted. If this field is not present, it
   * is left to Charge Point to decide how long to wait between attempts
   *
   * @return Integer
   */
  public Integer getRetryInterval() {
    return retryInterval;
  }

  /**
   * Optional. The interval in seconds after which a retry may be attempted. If this field is not
   * present, it is left to Charge Point to decide how long to wait between attempts
   *
   * @param retryInterval Integer
   */
  public void setRetryInterval(Integer retryInterval) {
    this.retryInterval = retryInterval;
  }

  /**
   * This field specifies the requested log and the location to which the log should be sent.
   *
   * @return {@link LogParametersType}
   */
  public LogParametersType getLog() {
    return log;
  }

  /**
   * Required. This field specifies the requested log and the location to which the log should be
   * sent.
   *
   * @param log {@link LogParametersType}
   */
  public void setLog(LogParametersType log) {
    this.log = log;
  }

  @Override
  public boolean transactionRelated() {
    return false;
  }

  @Override
  public boolean validate() {
    return logType != null && requestId != null && log != null && log.validate();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GetLogRequest that = (GetLogRequest) o;
    return Objects.equals(logType, that.logType)
        && Objects.equals(requestId, that.requestId)
        && Objects.equals(retries, that.retries)
        && Objects.equals(retryInterval, that.retryInterval)
        && Objects.equals(log, that.log);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logType, requestId, retries, retryInterval, log);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("logType", logType)
        .add("requestId", requestId)
        .add("retries", retries)
        .add("retryInterval", retryInterval)
        .add("log", log)
        .add("isValid", validate())
        .toString();
  }
}
