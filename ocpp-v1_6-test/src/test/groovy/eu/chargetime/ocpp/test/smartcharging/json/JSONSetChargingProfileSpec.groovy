package eu.chargetime.ocpp.test.smartcharging.json

import eu.chargetime.ocpp.model.core.ChargingProfile
import eu.chargetime.ocpp.model.core.ChargingProfileKindType
import eu.chargetime.ocpp.model.core.ChargingProfilePurposeType
import eu.chargetime.ocpp.model.core.ChargingRateUnitType
import eu.chargetime.ocpp.model.core.ChargingSchedule
import eu.chargetime.ocpp.model.core.ChargingSchedulePeriod
import eu.chargetime.ocpp.test.FakeCentral
import eu.chargetime.ocpp.test.FakeCentralSystem
import eu.chargetime.ocpp.test.FakeChargePoint
import spock.lang.Shared
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

/*
    ChargeTime.eu - Java-OCA-OCPP

    MIT License

    Copyright (C) 2016-2018 Thomas Volden <tv@chargetime.eu>
    Copyright (C) 2018 Mikhail Kladkevich <kladmv@ecp-share.com>

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

class JSONSetChargingProfileSpec extends Specification {
    @Shared
    FakeCentralSystem centralSystem = FakeCentral.getSystem(FakeCentral.serverType.JSON)
    @Shared
    FakeChargePoint chargePoint = new FakeChargePoint()

    def setupSpec() {
        // When a Central System is running
        centralSystem.started()
    }

    def setup() {
        chargePoint.connect()
    }

    def cleanup() {
        chargePoint.disconnect()
    }

    def "Central System sends a SetChargingProfile request and receives a response"() {
        def conditions = new PollingConditions(timeout: 1)
        given:
        conditions.eventually {
            assert centralSystem.connected()
        }

        when:
        ChargingSchedulePeriod[] chargingSchedulePeriod = new ChargingSchedulePeriod[1]
        chargingSchedulePeriod[0] = new ChargingSchedulePeriod(1, 2D)
        centralSystem.sendSetChargingProfileRequest(1, new ChargingProfile(1, 2, ChargingProfilePurposeType.ChargePointMaxProfile, ChargingProfileKindType.Recurring,
                new ChargingSchedule(ChargingRateUnitType.A, chargingSchedulePeriod)))

        then:
        conditions.eventually {
            assert chargePoint.hasHandledSetChargingProfileRequest()
            assert centralSystem.hasReceivedSetChargingProfileConfirmation()
        }
    }
}
