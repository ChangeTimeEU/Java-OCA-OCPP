package eu.chargetime.ocpp.model;

public abstract class RequestWithId implements Request {
  @Override
  public String getOcppMessageId() {
    return ocppMessageId;
  }

  @Override
  public void setOcppMessageId(String requestId) {
    this.ocppMessageId = requestId;
  }

  @Exclude private String ocppMessageId;
}
