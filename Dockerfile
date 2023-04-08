FROM rabbitmq:3.11.0-management

# Define environment variables
ENV RABBITMQ_DEFAULT_USER=delsix \
    RABBITMQ_DEFAULT_PASS=password \
    RABBITMQ_DEFAULT_VHOST=your-virtual-host

# Expose ports
EXPOSE 5672 15672

# Copy configuration file
COPY rabbitmq.conf /etc/rabbitmq/

# Enable rabbitmq_management plugin
RUN rabbitmq-plugins enable --offline rabbitmq_management

# Set permissions for the configuration file
RUN chown rabbitmq:rabbitmq /etc/rabbitmq/rabbitmq.conf && chmod 777 /etc/rabbitmq/rabbitmq.conf

# Start RabbitMQ server
CMD ["rabbitmq-server"]