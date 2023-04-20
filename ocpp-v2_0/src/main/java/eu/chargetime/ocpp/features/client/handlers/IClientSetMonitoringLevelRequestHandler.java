package eu.chargetime.ocpp.features.client.handlers;

import eu.chargetime.ocpp.model.request.SetMonitoringLevelRequest;
import eu.chargetime.ocpp.model.response.SetMonitoringLevelResponse;

/** Charging Station handler of {@link SetMonitoringLevelRequest} */
public interface IClientSetMonitoringLevelRequestHandler {
    SetMonitoringLevelResponse handleSetMonitoringLevelRequest(SetMonitoringLevelRequest request);
}