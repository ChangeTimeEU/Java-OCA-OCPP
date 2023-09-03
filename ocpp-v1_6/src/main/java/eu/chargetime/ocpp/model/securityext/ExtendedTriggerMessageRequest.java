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

import eu.chargetime.ocpp.PropertyConstraintException;
import eu.chargetime.ocpp.model.RequestWithId;
import eu.chargetime.ocpp.model.securityext.types.MessageTriggerEnumType;
import eu.chargetime.ocpp.utilities.MoreObjects;
import java.util.Objects;

public class ExtendedTriggerMessageRequest extends RequestWithId {

  private MessageTriggerEnumType requestedMessage;
  private Integer connectorId;

  /** Private default constructor for serialization purposes. */
  private ExtendedTriggerMessageRequest() {}

  /**
   * Handle required fields.
   *
   * @param requestedMessage MessageTriggerEnumType. See {@link
   *     #setRequestedMessage(MessageTriggerEnumType)}
   */
  public ExtendedTriggerMessageRequest(MessageTriggerEnumType requestedMessage) {
    setRequestedMessage(requestedMessage);
  }

  /**
   * Type of the message to be triggered.
   *
   * @return {@link MessageTriggerEnumType}
   */
  public MessageTriggerEnumType getRequestedMessage() {
    return requestedMessage;
  }

  /**
   * Required. Type of the message to be triggered.
   *
   * @param requestedMessage {@link MessageTriggerEnumType}
   */
  public void setRequestedMessage(MessageTriggerEnumType requestedMessage) {
    this.requestedMessage = requestedMessage;
  }

  /**
   * Only filled in when request applies to a specific connector.
   *
   * @return Integer connectorId &gt; 0
   */
  public Integer getConnectorId() {
    return connectorId;
  }

  /**
   * Optional. Only filled in when request applies to a specific connector.
   *
   * @param connectorId Integer connectorId &gt; 0
   */
  public void setConnectorId(Integer connectorId) {
    if (connectorId != null && connectorId <= 0) {
      throw new PropertyConstraintException(connectorId, "connectorId must be > 0");
    }
    this.connectorId = connectorId;
  }

  @Override
  public boolean transactionRelated() {
    return false;
  }

  @Override
  public boolean validate() {
    return requestedMessage != null && (connectorId == null || connectorId > 0);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ExtendedTriggerMessageRequest that = (ExtendedTriggerMessageRequest) o;
    return Objects.equals(requestedMessage, that.requestedMessage)
        && Objects.equals(connectorId, that.connectorId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestedMessage, connectorId);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("requestedMessage", requestedMessage)
        .add("connectorId", connectorId)
        .add("isValid", validate())
        .toString();
  }
}
