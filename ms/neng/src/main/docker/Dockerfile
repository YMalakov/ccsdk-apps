#============LICENSE_START=======================================================
#  ONAP : CCSDK.apps
#  ================================================================================
#  Copyright (C) 2018 AT&T Intellectual Property. All rights reserved.
#  ================================================================================
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#  
#       http://www.apache.org/licenses/LICENSE-2.0
#  
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
#============LICENSE_END=========================================================

#================================================================================
# Docker setup for the micro-service
#================================================================================
#FROM onap/ccsdk-ubuntu-image:0.3.0-SNAPSHOT
FROM anapsix/alpine-java:8_jdk

RUN apk add --no-cache curl

VOLUME /tmp
ADD NetworkElementNameGen.jar app.jar
VOLUME /opt/etc
#VOLUME /opt/aai
ADD opt/etc/ /opt/etc/
#ADD /opt/aai/ /opt/aai/
ADD startService.sh /startService.sh
RUN chmod 700 /startService.sh
ENTRYPOINT sh /startService.sh 
EXPOSE 8080
