# Java Model Project

A multi-module project template based on Spring Boot and Spring Cloud.

## Project Structure

```
java_model/
├── common/                     # Common Modules
│   ├── common-core/           # Core Utils
│   ├── common-redis/          # Redis Utils
│   └── common-security/       # Security Utils
│
├── framework/                  # Framework Modules
│   ├── spring-boot-starter/   # Spring Boot Starter
│   └── spring-cloud-starter/  # Spring Cloud Starter
│
├── modules/                    # Business Modules
│   ├── system/                # System Management
│   └── auth/                  # Authentication
│
└── example/                    # Example Projects
    ├── boot-example/          # Spring Boot Example
    └── cloud-example/         # Spring Cloud Example
```

## Technology Stack

- Java 17
- Spring Boot 3.2.3
- Spring Cloud 2023.0.0
- MyBatis Plus 3.5.5
- Redis & Redisson
- JWT & Spring Security
- Knife4j (API Documentation)

## Quick Start

1. Clone the repository
```bash
git clone https://github.com/noobnoodles/java_model.git
```

2. Build the project
```bash
mvn clean install
```
3. Run example
```bash
cd example/boot-example
mvn spring-boot:run
```

## Features

- **Modular Design**: Clear module separation for better maintainability
- **Security**: Built-in security configuration with JWT
- **API Documentation**: Integrated Knife4j for API documentation
- **Database**: MyBatis Plus with code generation
- **Cache**: Redis support with Redisson client
- **Microservices**: Ready for cloud deployment

## Development Guide

### Prerequisites
- JDK 17+
- Maven 3.8+
- Redis 6+
- MySQL 8+

### IDE Setup
- IntelliJ IDEA (Recommended)
- Enable Annotation Processing
- Install Lombok Plugin

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details

