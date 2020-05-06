external_url 'https://pds.stardust:8083'
gitlab_rails['gitlab_shell_ssh_port'] = 8082

registry_external_url 'https://pds.stardust:5005/'
gitlab_rails['registry_host'] = "pds.stardust"
gitlab_rails['registry_port'] = "5005"