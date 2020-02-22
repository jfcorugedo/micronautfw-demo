FROM oracle/graalvm-ce:19.3.1-java8 as graalvm
# For JDK 11
#FROM oracle/graalvm-ce:19.3.1-java11 as graalvm
RUN gu install native-image

COPY . /home/app/micronautfw-demo
WORKDIR /home/app/micronautfw-demo

RUN native-image --no-server -cp target/micronautfw-demo-*.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/micronautfw-demo/micronautfw-demo /app/micronautfw-demo
ENTRYPOINT ["/app/micronautfw-demo"]
