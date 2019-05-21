# FROM golang:1.10-alpine3.8 as build
# ENV OSSUTIL_VERSION 1.4.2
# WORKDIR /go/src/aliyun
# RUN apk add --no-cache curl git && \
#   curl -Lo /go/src/aliyun/ossutil.zip https://github.com/aliyun/ossutil/archive/${OSSUTIL_VERSION}.zip && \
#   unzip /go/src/aliyun/ossutil.zip && \
#   cd ossutil-${OSSUTIL_VERSION} && \
#   go get && \
#   go build && \
#   cp ossutil-${OSSUTIL_VERSION} /xxxx

FROM jenkins/jnlp-slave:3.27-1-alpine

USER root
RUN echo http://mirrors.aliyun.com/alpine/edge/community/ >> /etc/apk/repositories

# 使用阿里镜像 https://mirrors.aliyun.com
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories

RUN apk update && \
  apk add --upgrade docker sudo shadow git python py-pip curl libcurl nss maven && \
  rm -rf /var/cache/apk/*

RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers
RUN usermod -aG docker jenkins
RUN usermod -aG root jenkins
