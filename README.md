# Overview

This repository contains microservices code designed to manage and simulate interactions between banks, customers. The project is structured to run within Docker containers, ensuring a consistent and isolated environment for development, testing, and deployment.

## Getting Started

### Prerequisites
Docker: You must have Docker installed on your machine to build and run the containers. Visit Docker's official website for installation instructions.
Installation and Running the Services
To get the services up and running, follow these steps:

Clone the Repository
Build and Run with Docker
Use the provided script build_and_run.sh to build Docker images and start the containers:

bash
Copy code
./build_and_run.sh
This script performs the following actions:

Builds Docker images for each of the microservices.
Starts the Docker containers.
Configures the necessary network settings to allow communication between the microservices.
Architecture
This project is divided into two main microservices:

Bank Service: Manages banking operations and interactions.
Customer Service: Handles customer information and transactions.
Each service runs in its own Docker container, ensuring they are loosely coupled and can be scaled independently.
