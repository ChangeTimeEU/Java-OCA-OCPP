package eu.chargetime.ocpp2.test.profiles.core.soap

import eu.chargetime.ocpp2.test.base.soap.SOAPBaseSpec
import spock.util.concurrent.PollingConditions

class SOAPStartTransactionSpec extends SOAPBaseSpec {

    def "Charge point sends StartTransaction request and receives a response"() {
        def conditions = new PollingConditions(timeout: 10)
        when:
        chargePoint.sendStartTransactionRequest()

        then:
        conditions.eventually {
            assert centralSystem.hasHandledStartTransactionRequest()
        }

        then:
        conditions.eventually {
            assert chargePoint.hasReceivedStartTransactionConfirmation()
        }

    }

}
