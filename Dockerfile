FROM eclipse-temurin:17-jdk-alpine AS jdk
VOLUME ["./src:/src:rshared"]
WORKDIR /
COPY src src
RUN mkdir -p /src/Docs

RUN javadoc -d src/Docs -sourcepath /src/main/java/ ru.geekbrains.hexcore


FROM nginx:alpine
COPY --from=jdk /src/Docs /usr/share/nginx/html
