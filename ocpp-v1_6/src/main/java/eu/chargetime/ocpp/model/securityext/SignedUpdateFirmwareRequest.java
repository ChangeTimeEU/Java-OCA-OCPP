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
import eu.chargetime.ocpp.model.securityext.types.FirmwareType;
import eu.chargetime.ocpp.utilities.MoreObjects;
import java.util.Objects;

public class SignedUpdateFirmwareRequest extends RequestWithId {

  private Integer retries;
  private Integer retryInterval;
  private Integer requestId;
  private FirmwareType firmware;

  /** Private default constructor for serialization purposes. */
  private SignedUpdateFirmwareRequest() {}

  /**
   * Handle required fields.
   *
   * @param requestId Integer. See {@link #setRequestId(Integer)}
   * @param firmware FirmwareType. See {@link #setFirmware(FirmwareType)}
   */
  public SignedUpdateFirmwareRequest(Integer requestId, FirmwareType firmware) {
    setRequestId(requestId);
    setFirmware(firmware);
  }

  /**
   * This specifies how many times Charge Point must try to download the firmware before giving up.
   * If this field is not present, it is left to Charge Point to decide how many times it wants to
   * retry.
   *
   * @return Integer
   */
  public Integer getRetries() {
    return retries;
  }

  /**
   * Optional. This specifies how many times Charge Point must try to download the firmware before
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
   * is left to Charge Point to decide how long to wait between attempts.
   *
   * @return Integer
   */
  public Integer getRetryInterval() {
    return retryInterval;
  }

  /**
   * Optional. The interval in seconds after which a retry may be attempted. If this field is not
   * present, it is left to Charge Point to decide how long to wait between attempts.
   *
   * @param retryInterval Integer
   */
  public void setRetryInterval(Integer retryInterval) {
    this.retryInterval = retryInterval;
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
   * Specifies the firmware to be updated on the Charge Point.
   *
   * @return {@link FirmwareType}
   */
  public FirmwareType getFirmware() {
    return firmware;
  }

  /**
   * Required. Specifies the firmware to be updated on the Charge Point.
   *
   * @param firmware {@link FirmwareType}
   */
  public void setFirmware(FirmwareType firmware) {
    this.firmware = firmware;
  }

  @Override
  public boolean transactionRelated() {
    return false;
  }

  @Override
  public boolean validate() {
    return requestId != null && firmware != null && firmware.validate();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SignedUpdateFirmwareRequest that = (SignedUpdateFirmwareRequest) o;
    return Objects.equals(retries, that.retries)
        && Objects.equals(retryInterval, that.retryInterval)
        && Objects.equals(requestId, that.requestId)
        && Objects.equals(firmware, that.firmware);
  }

  @Override
  public int hashCode() {
    return Objects.hash(retries, retryInterval, requestId, firmware);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("retries", retries)
        .add("retryInterval", retryInterval)
        .add("requestId", requestId)
        .add("firmware", firmware)
        .add("isValid", validate())
        .toString();
  }
}
