package pds.stardust.kms.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pds.stardust.kms.beans.VaultSecret;
import pds.stardust.kms.beans.VaultSecretResponse;
import pds.stardust.kms.config.VaultConfig;
import pds.stardust.kms.constants.SecretsConstants;

import static pds.stardust.kms.utils.HttpUtils.buildHttpHeaders;

/**
 * This class defines the common methods regarding vault secret management.
 */
@Service
public class SecretManagementService extends AbstractRestService implements ISecretManagementService {

    private final ObjectMapper mapper;
    private final VaultConfig vaultConfig;

    @Autowired
    public SecretManagementService(VaultConfig vaultConfig, RestTemplate restTemplate, ObjectMapper mapper) {
        super(restTemplate, mapper);
        this.mapper = mapper;
        this.vaultConfig = vaultConfig;
    }

    /**
     * Retrieve The Data Encryption Key (DEK) from Vault
     *
     * @return The Data Encryption Key encoded in base 64
     */
    public VaultSecretResponse getDataEncryptionKey() throws Exception {
        VaultSecret dekSecret = this.readSecret(SecretsConstants.DATA_ENCRYPTION_KEY_SECRET_PATH);
        return new VaultSecretResponse(dekSecret.getSecret().get(SecretsConstants.DATA_ENCRYPTION_KEY_SECRET_KEY));
    }

    /**
     * Update the Data Encryption Key (DEK) secret. Will be called during the key
     * rotation process.
     *
     * @param vaultSecret  The updated Vault Data Encryption Key secret
     * @throws Exception If the secret cannot be serialized in JSON
     */
    public void updateDataEncryptionKey(VaultSecret vaultSecret) throws Exception {
        this.updateSecret(SecretsConstants.DATA_ENCRYPTION_KEY_SECRET_PATH, vaultSecret);
    }

    /**
     * Read a Vault secret
     *
     * @param secretPath The vault secret path
     * @return The vault secret
     */
    private VaultSecret readSecret(String secretPath) throws Exception {

        HttpEntity<VaultSecret> httpEntity = new HttpEntity<>(
                buildHttpHeaders(vaultConfig.getToken())
        );

        String url = vaultConfig.getUrl() + secretPath;

        ResponseEntity<VaultSecret> response = get(url, httpEntity, VaultSecret.class);

        return response.getBody();

    }

    /**
     * Update a Vault secret
     *
     * @param path The Vault secret path
     * @param vaultSecret The updated Vault secret
     * @throws Exception If the secret cannot be serialized in JSON
     */
    private void updateSecret(String path, VaultSecret vaultSecret) throws Exception {

        String body = mapper.writeValueAsString(vaultSecret.getSecret());

        HttpEntity<String> httpEntity = new HttpEntity<>(
                body,
                buildHttpHeaders(vaultConfig.getToken())
        );

        String url = vaultConfig.getUrl() + path;

        put(url, httpEntity, String.class);

    }

    @Override
    protected String getServiceName() {
        return "Vault";
    }
}
