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

## 🚀 Publishing

To publish a new version of the project:

1. Create a branch for the release:

```
$ git checkout -b release_$(date -u +"%Y%m%d")
```

2. Generate the `CHANGELOG` file automatically based on the commits:

```
$ ./gradlew changelog
```

3. Open a Pull Request against `main` and have it merged.

4. Update your local `main` branch, generate the new tag and push it:

```
$ git checkout master
$ git pull
$ git tag v<new_version>
$ git push --tags
```
