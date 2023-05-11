Lab3 (auction system)
---

### Software

Required:
- Jdk17.

Useful resources:
- Jdk17 installation [guide](docs/install-jdk17.md).
- Intellij IDEA project setup [guide](docs/setup-intellij-project.md).
- Docker (or alternatively follow this [guide](https://kafka.apache.org/quickstart) to start kafka broker + zookeeper)
- [sqlite client](https://sqlite.org/download.html).
- `curl` must be available in your _Git Bash_ terminal.
- [Postman](https://www.postman.com/downloads/) as `curl` alternative.

In order to add a new database migration create a new file with a version increased by 1.
Flyway will be able to detect new migration during application startup and will automatically apply it.
For example:
```
└───app-buyer/src/main/resources/db/migration
    └───V1__demo_migration.sql
    └───V2__your_database_change.sql <-- here you go (notice the prefix pattern 'V2__')
```
### Application

Start kafka:
```
docker-compose up
```

Start app-auction:

```sh
./gradlew app-auction:bootRun
```

or run _fotius.example.auction.Application.main_ from IDE.

Web interface - `http://localhost:8080/login.html`.

Stop:

Press <kbd>ctrl+c</kbd> or stop _fotius.example.auction.Application.main_ from IDE.

You can start app-buyer and app-seller the same way as app-auction. Web interface - `http://localhost:8000/login.html`.

---
In case you want to play around with kafka utilities, bash into the container:
```sh
docker exec -ti broker bash
```

You can use most of the commands available in the doc [here](https://kafka.apache.org/quickstart).
