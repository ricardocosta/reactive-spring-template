<h1>Reactive Spring Template</h1>

Template for a reactive Spring Boot application.

## 🔌 Running

### Setup

In order to run this application locally, you need to have an instance of PostgreSQL running on your machine. After
that, follow the [Setup Database](./docs/setup-database.md) guide to have everything ready to run.

### Terminal

You can run the application locally in your terminal application with:

```
$ ./gradlew bootRun
```

The application will be available at http://localhost:8080.

### IntelliJ

To run in IntelliJ, just run the `main` method in the `TemplateApplication` class.

Be reminded that you may have to modify the Run Configuration in order to add environment variables that are not defined
in the `application.yml` file.

The application will be available at http://localhost:8080.

## 🧰 Development

Please read the [contributing guide](./CONTRIBUTING.md) for further details on how to develop on this app.
