QA Demo Automation Framework

![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Java](https://img.shields.io/badge/Java-19-orange)
![Selenium](https://img.shields.io/badge/Selenium-4.15.0-green)
![Cucumber](https://img.shields.io/badge/Cucumber-7.15.0-brightgreen)


🚀 Overview

A comprehensive **Selenium WebDriver** automation framework built with **Cucumber BDD**, **Java 19**, and **Maven**. This framework provides a robust foundation for automated testing with clean architecture, detailed reporting, and CI/CD integration.


🛠️ Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 19 | Programming Language |
| **Selenium WebDriver** | 4.31.0 | Browser Automation |
| **Cucumber** | 7.22.0 | BDD Framework |
| **JUnit 5** | 5.12.2 | Test Framework |
| **Maven** | 3.9+ | Build Tool |
| **TestLogger** | Custom | Enhanced Logging |


📋 Prerequisites

Before running this automation framework, ensure you have:

- ☑️ **Java 19** or higher installed
- ☑️ **Maven 3.6+** installed  
- ☑️ **Chrome/Firefox** browser installed
- ☑️ **Git** installed
- ☑️ **IDE** (IntelliJ IDEA/Eclipse recommended)


🏗️ Framework Architecture
src/
├── main/java/
│   ├── constants/          # Test constants and enums
│   │   ├── GlobalConstants.java
│   │   ├── TestDataConstants.java
│   │   ├── TimeConstants.java
│   │   └── URLConstants.java
│   ├── context/           # Test context management
│   │   ├── TestContext.java
│   │   └── ScenarioContext.java
│   ├── pages/             # Page Object Model classes
│   │   └── LoginPage.java
│   └── utils/             # Utility classes
│       ├── ConfigReader.java
│       ├── DriverFactory.java
│       ├── ElementUtils.java
│       ├── WaitUtils.java
│       ├── TestLogger.java
│       ├── CSVUtils.java
│       └── ExcelUtils.java
└── test/
    ├── java/
    │   ├── runners/       # Cucumber test runners
    │   │   └── TestRunner.java
    │   ├── stepDefinitions/ # Cucumber step definitions
    │   │   └── LoginSteps.java
    │   └── hooks/         # Test lifecycle hooks
    │       └── Hooks.java
    └── resources/
        ├── features/      # Cucumber feature files
        │   └── login.feature
        └── config/        # Configuration files
            └── config.properties


🔧 Key Features

✅ Page Object Model - Clean, maintainable page classes
✅ BDD with Cucumber - Business-readable test scenarios
✅ Multi-browser support - Chrome, Firefox, Edge, Safari
✅ Parallel execution - Thread-safe test execution
✅ Smart waits - Explicit waits with timeout handling
✅ Enhanced logging - Custom TestLogger with context
✅ Screenshot capture - Automatic screenshots on failures
✅ Configuration management - Centralized config handling
✅ CI/CD ready - Jenkins pipeline integration


## 📞 Contact

- **Author**: Yuliya Sumina<br>
- **Email**: yuliyasumina@gmail.com<br>
- **GitHub**: [@JuSumina](https://github.com/JuSumina)<br>
- **LinkedIn**: (https://www.linkedin.com/in/yuliyasumina/)<br>
