FROM jenkins/jenkins:lts

USER root

RUN apt-get update && apt-get -y install apt-transport-https ca-certificates curl gnupg2 software-properties-common
RUN curl -fsSL https://download.docker.com/linux/$(. /etc/os-release; echo "$ID")/gpg > /tmp/dkey; apt-key add /tmp/dkey && \
    add-apt-repository \
    "deb [arch=amd64] https://download.docker.com/linux/$(. /etc/os-release; echo "$ID") \
    $(lsb_release -cs) \
    stable"

RUN apt-get update && apt-get -y install docker-ce vim

RUN curl -sL https://deb.nodesource.com/setup_10.x | sudo -E bash -

RUN apt-get install -y python3 python3-pip npm

RUN pip3 install ansible

RUN usermod -aG docker jenkins