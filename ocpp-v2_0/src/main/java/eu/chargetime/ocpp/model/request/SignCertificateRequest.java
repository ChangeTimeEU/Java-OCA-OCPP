package eu.chargetime.ocpp.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import eu.chargetime.ocpp.model.RequestWithId;
import eu.chargetime.ocpp.model.dataTypes.CustomDataType;
import eu.chargetime.ocpp.model.dataTypes.enums.CertificateSigningUseEnumType;
import eu.chargetime.ocpp.model.validation.OCPP2PrimDatatypes;
import eu.chargetime.ocpp.model.validation.Validator;
import eu.chargetime.ocpp.model.validation.ValidatorBuilder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "csr",
        "certificateType"
})
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class SignCertificateRequest extends RequestWithId {

    private transient Validator csrValidator =
            new ValidatorBuilder()
                    .setRequired(true)
                    .addRule(OCPP2PrimDatatypes.string5500())
                    .build();

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    public CustomDataType customData;
    /**
     * The Charging Station SHALL send the public key in form of a Certificate Signing Request (CSR) as described in RFC 2986 [22] and then PEM encoded, using the &lt;<signcertificaterequest,SignCertificateRequest>> message.
     *
     * (Required)
     *
     */
    @JsonProperty("csr")
    public String csr;
    /**
     * Indicates the type of certificate that is to be signed. When omitted the certificate is to be used for both the 15118 connection (if implemented) and the Charging Station to CSMS connection.
     *
     *
     *
     */
    @JsonProperty("certificateType")
    public CertificateSigningUseEnumType certificateType;

    public SignCertificateRequest(String csr) {
        csrValidator.validate(csr);
        this.csr = csr;
    }

    public void setCustomData(CustomDataType customData) {
        this.customData = customData;
    }

    public void setCsr(String csr) {
        csrValidator.validate(csr);
        this.csr = csr;
    }

    public void setCertificateType(CertificateSigningUseEnumType certificateType) {
        this.certificateType = certificateType;
    }

    @Override
    public boolean transactionRelated() {
        return false;
    }

    @Override
    public boolean validate() {
        return csrValidator.safeValidate(csr);
    }
}
