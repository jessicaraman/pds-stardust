listener "tcp" {
    address = "0.0.0.0:8200"
    tls_disable = false
    tls_key_file = "/vault/ssl/vault.key"
    tls_cert_file = "/vault/ssl/vault.crt"
    tls_disable_client_certs = true
    tls_require_and_verify_client_cert = false
}

backend "file" {
    path = "/vault/file"
}

default_lease_ttl = "168h"
max_lease_ttl = "0h"
api_addr = "https://localhost:8200"
ui = true