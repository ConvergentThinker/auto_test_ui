
# `Write less code & Achieve more` In your Test Automation Framework.

The aim of this framework is to use Spring Boot to create a test automation framework for your functional tests with Selenium Java and Cucumber with TestNG.

- **Spring Boot** is a popular application development framework in the Java community. Spring Boot simplifies the developer life by handling most of the routine activities.
- **Dependency Injection** : Easy to create specific objects in our Page Objects, test classes automatically. Spring will just give it to you!!
- **WebDriver Manager** : Manage web driver life cycle automatically using Spring Boot.
- **Page Objects** : Create reusable page components and get them injected into Step def and Test
- **Parallel test execution** : Create multiple threads, manage web drivers and run tests in parallel etc with clear thread specific scope.
- **Executing tests in multiple environments**  (DEV / QA / STG / PRD): To manage environment specific properties like URLs, user credentials etc via property files.
- **Cucumber feature files** : Driving tests by writing Cucumber feature files.
-  **Local vs Remote Grid** : Execute your tests in local & in remote selenium grid by simply switching Spring profiles easily.
- One of the main advantages of using Springboot in automation framework is writing less code — a lot of configuration management, object management is done is Spring via simple annotations

## Building Framework
`mvn clean install -DskipTests -DskipCucumber`

## Execute Test
### Command Line Method
- `mvn clean test`
- `mvn clean test -Dcucumber.filter.tags="@Framework"` -> run with default profile
- `mvn clean test -P [remote-sit, remote-uat, uat, sit, local]` -> run with different profiles (default: local
- `mvn clean test -Dparallelcount=10 -Dbrowser=chrome` -> run with 10 threads and on default chrome
- `mvn clean test -Dparallelcount=10 -Dcucumber.filter.tags="@Framework" `  -> run with 10 threads and on default chrome
- `mvn clean test  -P uat -Dparallelcount=10 -Dcucumber.filter.tags="@Framework" `  -> run with 10 threads in UAT env and on default chrome
- `TestRunner.java` is meant to be run from CLI with `mvn clean test`
    - this will detect the `@serial` and run accordingly

### UI - Directly from feature file (scenario -> run) or Runner File

- `TestRunnerLocal.java` is meant to be run from UI
    - whereby, it disregards the rule of parallelism. All tests will be run in serial regardless of `@serial` or not
    - mainly for debugging purpose
    - by default, it'll pick up the maven profile that has default true activation (eq. `qa` or `local`)

- Please take note below points before execution:
    - Please select proper **maven profile** from **Maven** toolbar
    - Once selected, click on **Reload All Maven Projects** to ensure selected profile is loaded before execution
    - Every switching of profile, above 2 steps are required to avoid non-deterministic profile being loaded
        - If profile not loaded properly, wrong URL might be hit (eq. we wanted to test `UAT` but hitting `SIT` environment)
    - Optinally, set the runner **environment variable** to `spring.profiles.active=<profile_name>`

## Built With
- SpringBoot - Entire framework is built on top of it
- Cucumber - BDD style feature files
- Selenium Java
- TestNG - Execution 
- Poiji - Excel Reader
- Ashot - Screenshot Lib
- Hamcrest Assertion
- Cucumber-Reporting
- Cluecumber-reporting
- Log4j2 - logging


## Feature
1. Excel to POJO Mapper
2. Build in environment switcher for UAT/SIT/Remote-UAT/Remote-SIT/Local
3. Built-in custom tag to control browser, parallelism, api/ui test
4. terminateOnExit configuration to prevent browser from terminating
5. Code separation between framework and test


## Built-in custom cucumber tags
- `@nobrowser` scenario that doesn't require browser (eq. API-only)
- `@serial` scenario that require to be run in serial (default: parallel)
- `@chrome` force the scenario to only run in Chrome and implicitly `@serial`
- `@firefox` force the scenario to only run in firefox and implicitly `@serial`


## Technical Notes
- maven profile will set the `spring.prof` property
- application.properties will reference it and set active profile
    - `spring.profiles.active=@spring.prof@`
    - all property in this file will be served as default value
    - if other profile `qa` or `remote` does specify, it'll use it instead (overwrite the app.prop file)
- Steps for excel-POJO generator
    1. create the model class `Person implements ScenarioModel` which using lombok `@Data`
        - this model must at least has `String Scenario` attribute
    2. create the bean in `GeneralConfig` that return `List<Person>`
        - this is where the reading from Excel happens
    3. To use it in test, first `@Autowired List<Person> personList;` (this to get List of objects from step 2)
    4. get `@Autowired GeneralUtils utils;` object
        - use the object to retrieve 1 Person object based on `scenario` name
        - eq. `Person person = utils.getData(personList, strScenario, Person.class;`

## To-Do Features
- handling of multiple excel sheets to be combined in single POJO
- handling of different browsers needs
- pre-baked REST-API
- TestNG and Cucumber and API side-by-side
- Add ExtentTest Report

## To Contact

- Email: `innovativesolutionsapps@gmail.com`

- LinkedIn: `https://www.linkedin.com/in/sakthivel-iyappan/`
