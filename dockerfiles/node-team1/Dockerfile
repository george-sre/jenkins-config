FROM jenkins/jnlp-slave:3.27-1-alpine

USER root
RUN echo http://mirrors.aliyun.com/alpine/edge/community/ >> /etc/apk/repositories

# 使用阿里镜像 https://mirrors.aliyun.com
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories

RUN apk update && \
  apk add --upgrade docker sudo shadow git python py-pip curl libcurl nss && rm -rf /var/cache/apk/*
RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers
RUN usermod -aG docker jenkins
RUN usermod -aG root jenkins
