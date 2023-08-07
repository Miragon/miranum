FROM postgres:13.2

RUN echo "Building the image..."

# Copy the DB init script into the container
COPY ./init.sh /docker-entrypoint-initdb.d/init.sh
RUN chmod +x /docker-entrypoint-initdb.d/init.sh

ENTRYPOINT ["docker-entrypoint.sh"]

CMD ["postgres"]