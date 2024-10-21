# Category Manager Telegram Bot

Category Manager Telegram Bot is a Spring Boot-based application that allows users to manage a hierarchical tree of categories directly through Telegram. This bot enables users to add, view, and remove categories with simple command-based interaction.

## Features

- **/start**: Start interacting with the bot.
- **/help**: Display a list of available commands.
- **/viewTree**: View the current tree structure of categories.
- **/addElement**: Add a new element to the tree (as a root or child element).
- **/removeElement**: Remove an existing element from the tree.
- **/clear**: Clear all categories from the tree.

## Table of Contents
- [Installation](#installation)
- [Commands](#commands)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Docker](#docker)

## Installation

### Prerequisites
1. Java 21+
2. Maven
3. PostgreSQL (or another relational database of your choice)

### Steps
1. Clone the repository:
    ```bash
    git clone https://github.com/RamazanMamyrbek/category-manager-tgbot.git
    cd category-manager-tgbot
    ```

2. Configure the application:
    - Set up your `application.yml` file with your PostgreSQL or preferred database credentials.
    - Add your Telegram Bot's name and token in `application.yml`:
      ```yml
      bot:
        name:YourBotName
        token:YourBotToken
      ```

3. Build the project:
    ```bash
    mvn clean install
    ```

4. Run the application:
    ```bash
    mvn spring-boot:run
    ```

## Commands

| Command            | Description                                                 |
|--------------------|-------------------------------------------------------------|
| `/start`           | Starts interaction with the bot.                            |
| `/help`            | Shows a list of available commands.                         |
| `/viewTree`        | Displays the current tree structure.                        |
| `/addElement`      | Adds a category (either as a root or as a child of a parent).|
| `/removeElement`   | Removes a category from the tree.                           |
| `/clear`           | Clears all the categories from the tree.                    |
| `/exit`            | Exit the current command process (e.g., add/remove element).|

## Project Structure

- **TelegramBot**: The main bot class handling updates and interacting with the Telegram API.
- **CategoryService**: Manages category logic, including adding, removing, and displaying categories.
- **Command Classes**: Each command (e.g., `/start`, `/help`, `/viewTree`) has its own class implementing the `Command` interface.
- **MessageSender**: A utility class responsible for sending messages to the user via Telegram.

## Technologies Used

- **Spring Boot**: Backend framework for rapid application development.
- **Telegram Bots API**: To interact with the Telegram platform.
- **Hibernate**: ORM framework used for managing categories in the database.
- **PostgreSQL**: Database for storing categories (other relational databases can be used).
- **Java 21**: Latest version of Java used in this project.

## Docker

To run the application using Docker, ensure you have Docker installed on your machine, then follow these steps:

1. Build the Docker image:
    ```bash
    docker build -t category-manager-tgbot .
    ```

2. Run the Docker container:
    ```bash
    docker run -d -p 8080:8080 --name category-manager-tgbot category-manager-tgbot
    ```

3. The bot will be available and ready to process Telegram commands.
