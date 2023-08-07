FROM postgres:13.2

RUN echo "Building the image..."

# install dos2unix to be sure that the init script is correct formated
RUN apt-get update && apt-get install -y dos2unix

# Copy the DB init script into the container
COPY ./init.sh /docker-entrypoint-initdb.d/init.sh
RUN dos2unix /docker-entrypoint-initdb.d/init.sh && chmod +x /docker-entrypoint-initdb.d/init.sh

ENTRYPOINT ["docker-entrypoint.sh"]

CMD ["postgres"]