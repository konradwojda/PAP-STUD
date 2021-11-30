# Podział modułów

- `edu.iipw.pap.db.model` - model zgodnie z wzorcem MVC
- `edu.iipw.pap.db` - połączenie z bazą danych
- `edu.iipw.pap.controller` - kontrolery zgodnie z wzorcem MVC
- `edu.iipw.pap` - punkt wejściowy do aplikacji

Widoki zdefiniowane są w plikach fxml w katalogu `resources.view`

```mermaid
classDiagram
    class App
    class Controller
    class AgencyController
    class LineController
    class StopController
    class Stop
    class Agency
    class Line
    class Database
    class LineType
    class WheelchairAccessibility

    Database o-- Agency
    Database o-- Line
    Database o-- Stop

    WheelchairAccessibility -- Stop
    LineType -- Line

    AgencyController -- Database
    LineController -- Database
    StopController -- Database
    Controller  -- Database

    App o-- Controller

    Controller o-- LineController
    Controller o-- StopController
    Controller o-- AgencyController
```