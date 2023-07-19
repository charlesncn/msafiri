# Msafiri Project

Msafiri is a microservice-based project implemented using Spring Boot. It follows a microservice architecture and
incorporates various technologies for inter-process communication, service discovery, API gateway, secure microservices,
circuit breaker, distributed tracing, event-driven architecture, Dockerization, and monitoring.

## Technologies Used

- **Spring Boot:** Msafiri is built using Spring Boot, a Java-based framework that simplifies the development of
  microservices.
- **Inter Process Communication:** The project utilizes a mechanism for communication between microservices, allowing
  them to exchange data and invoke functionality. This enables seamless integration and collaboration between different
  services.
- **Service Discovery (Netflix Eureka):** Netflix Eureka is employed for service discovery, enabling microservices to
  locate and communicate with each other dynamically. This helps in achieving a flexible and scalable architecture.
- **API Gateway (Spring Cloud Gateway):** Spring Cloud Gateway acts as the entry point for external requests and routes
  them to the appropriate microservice. It provides features like routing, filtering, and load balancing, enhancing the
  security and performance of the system.
- **Secure Microservices (Keycloak):** Keycloak is integrated into the project to provide authentication and
  authorization capabilities for microservices. It ensures secure access control and user management within the system.
- **Circuit Breaker:** A circuit breaker pattern is implemented to prevent cascading failures in the system. It allows
  the system to handle faults and failures gracefully, providing resilience and fault tolerance.
- **Distributed Tracing:** Distributed tracing is used to monitor and analyze the flow of requests across microservices.
  It enables the identification and resolution of performance issues and bottlenecks, ensuring optimal system
  performance.
- **Event Driven Architecture (Kafka):** Kafka is utilized to implement an event-driven architecture, enabling
  communication between microservices through a publish-subscribe messaging system. It facilitates loose coupling and
  asynchronous communication.
- **Dockerization:** The entire application is containerized using Docker, enabling easy deployment and scaling of
  microservices. Docker provides a consistent and isolated environment for running the application.
- **Monitoring Microservices (Prometheus and Grafana):** Prometheus is used for collecting metrics and monitoring the
  health of microservices. Grafana is employed for visualizing and analyzing the collected metrics, providing insights
  into the system's performance and behavior.

## Getting Started

To set up the Msafiri project locally, follow these steps:

1. Clone the repository from GitHub.
2. Install Docker on your machine.
3. Build the Docker images for each microservice.
4. Run the Docker containers for each microservice.
5. Access the API gateway to interact with the microservices.

Detailed instructions for setting up and running the project can be found in the project's documentation.

## Contributing

Contributions to Msafiri are welcome! If you want to contribute, please follow the guidelines outlined in the project's
contribution guide.

## License

The Msafiri project is licensed under the [MIT License](https://opensource.org/licenses/MIT). Feel free to use, modify,
and distribute the project as per the terms of the license.
